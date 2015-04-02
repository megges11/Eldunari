package eldunari.form.classes;

import java.awt.Component;
import java.awt.Container;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import eldunari.form.annotation.Dimension;
import eldunari.form.annotation.InputField;
import eldunari.form.annotation.FormLabel;
import eldunari.form.components.Label;
import eldunari.form.interfaces.IComponent;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;

public class LayoutCreator {

	private final Class<?> currentClass;
	private ArrayList<IComponent> components;	

	public LayoutCreator(Class<?> cls){
		currentClass = cls;
		load_annotations();
	}
	public Class<?> getCurrentClass() {
		return currentClass;
	}

	public Container getContainer(){
		if(components != null){
			Container container = new Container();			
			for(IComponent component : components){
				String neighbor = component.getNeightborName();
				component.setLocation(null,component.getOrientation());
				if(neighbor != null){
					IComponent n = getComponentByName(neighbor);
					component.setLocation(n, component.getOrientation());
				}				
				container.add((Component)component);
			}			
			return container;
		}return null;
	}

	private void load_annotations(){
		try {
			components = new ArrayList<IComponent>();
			for(Field field : currentClass.getDeclaredFields()){
				LayoutDefinition definition = getDefinition(currentClass,field);
				if(definition.hasErrors()){
					OutputHandler.Message(OutputType.Warning, definition.getError());
					return;
				}			

				if(definition.getLabel() != null){
					FormLabel fl = definition.getLabel();
					Label lbl = new Label();
					lbl.setName(fl.name());
					lbl.setText(fl.text());
					lbl.setTag(fl.tag());
					lbl.setSize(fl.width(),fl.height());
					lbl.setMin(fl.width(), fl.height());
					lbl.setMax(fl.width(), fl.height());
					components.add(lbl);
				}

				InputField ifield = definition.getInputField();
				if(ifield != null){
					IComponent com = ifield.cls().newInstance();
					com.setEditable(ifield.editable());
					com.setName(ifield.name());
					com.setSize(ifield.width(), ifield.height());
					com.setTag(ifield.tag());
					com.setNeighborString(ifield.neighborName());		
					com.setOrientation(ifield.orientation());
					com.setMin(ifield.width(), ifield.height());
					com.setMax(ifield.width(), ifield.height());
					components.add(com);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LayoutDefinition getDefinition(Class<?> cls, Field field){
		LayoutDefinition definition = new LayoutDefinition();
		field.setAccessible(true);

		Annotation[] annotations = field.getAnnotations();
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(FormLabel.class)){
				definition.setLabel((FormLabel)anno);
			}else if(anno.annotationType().equals(InputField.class)){
				definition.setInputField((InputField)anno);
			}else if(anno.annotationType().equals(Dimension.class)){
				definition.setDimension((Dimension)anno);
			}
		}
		return definition;
	}

	private IComponent getComponentByName(String name){
		for(IComponent com : components){
			if(com.getName().equals(name)){
				return com;
			}
		}return null;
	}
	
}
