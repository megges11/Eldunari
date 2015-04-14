package eldunari.form.classes;

import java.awt.Component;
import java.awt.Container;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import eldunari.form.annotation.Caption;
import eldunari.form.annotation.Dimension;
import eldunari.form.annotation.InputField;
import eldunari.form.annotation.FormLabel;
import eldunari.form.annotation.ReadOnly;
import eldunari.form.components.Label;
import eldunari.form.interfaces.IComponent;
import eldunari.general.annotation.Definition;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;
import eldunari.origin.annotation.Required;

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

	public Container getContainer(Container container){
		if(components != null){	
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
			ReadOnly ro = currentClass.getAnnotation(ReadOnly.class);
			boolean isReadOnly = (ro != null) ? ro.value() : false; 
			
			for(Field field : currentClass.getDeclaredFields()){
				LayoutDefinition definition = getDefinition(currentClass,field);
				if(definition.hasErrors()){
					OutputHandler.Message(OutputType.Warning, definition.getError());
					return;
				}			
				
				Required req = field.getAnnotation(Required.class);
				boolean isrequired = (req == null) ? false : req.value();
				
				if(definition.getLabel() != null){
					FormLabel fl = definition.getLabel();
					Label lbl = new Label();
					lbl.setName(fl.name());
					if(definition.getCaption()!= null){						
						lbl.setText(definition.getCaption().value()+ ((isrequired)?"*":""));
					}else{
						lbl.setText(fl.text()+ ((isrequired)?"*":""));
					}
					lbl.setTag(fl.tag());
					lbl.setSize(fl.width(),fl.height());
					lbl.setMin(fl.width(), fl.height());
					lbl.setMax(fl.width(), fl.height());
					lbl.setNeighborString(fl.neighborName());
					lbl.setLocation(getComponentByName(fl.neighborName()), fl.orientation());
					components.add(lbl);
				}

				InputField ifield = definition.getInputField();
				if(ifield != null){
					IComponent com = ifield.cls().newInstance();
					com.setEditable((isReadOnly == true)? true : ifield.editable());
					com.setName(ifield.name());
					com.setSize(ifield.width(), ifield.height());
					com.setTag(ifield.tag());
					com.setNeighborString(ifield.neighborName());		
					com.setOrientation(ifield.orientation());
					com.setMin(ifield.width(), ifield.height());
					com.setMax(ifield.width(), ifield.height());
					com.setLocation(getComponentByName(ifield.neighborName()), ifield.orientation());
					components.add(com);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LayoutDefinition getDefinition(Class<?> cls, Field field){
		LayoutDefinition layoutdef = new LayoutDefinition();
		field.setAccessible(true);

		Definition definition = cls.getAnnotation(Definition.class);
		for(Class<?> defclass : definition.value()){
			for(Field f : defclass.getDeclaredFields()){
				if(f.getName().equals(field.getName())){
					Annotation[] annotations = f.getAnnotations();
					for(Annotation anno : annotations){
						if(anno.annotationType().equals(FormLabel.class)){
							layoutdef.setLabel((FormLabel)anno);
						}else if(anno.annotationType().equals(InputField.class)){
							layoutdef.setInputField((InputField)anno);
						}else if(anno.annotationType().equals(Dimension.class)){
							layoutdef.setDimension((Dimension)anno);
						}else if(anno.annotationType().equals(Caption.class)){
							layoutdef.setCaption((Caption)anno);
						}
					}
				}
			}
		}
		
		Annotation[] annotations = field.getAnnotations();
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(FormLabel.class)){
				layoutdef.setLabel((FormLabel)anno);
			}else if(anno.annotationType().equals(InputField.class)){
				layoutdef.setInputField((InputField)anno);
			}else if(anno.annotationType().equals(Dimension.class)){
				layoutdef.setDimension((Dimension)anno);
			}else if(anno.annotationType().equals(Caption.class)){
				layoutdef.setCaption((Caption)anno);
			}
		}
		return layoutdef;
	}

	private IComponent getComponentByName(String name){
		try{
			for(IComponent com : components){
				if(com != null){
					String comname = com.getName();
					if(comname != null && comname.equals(name)){
						return com;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;

	}

}
