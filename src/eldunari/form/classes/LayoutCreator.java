package eldunari.form.classes;

import java.awt.Component;
import java.awt.Container;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import eldunari.form.annotation.Dimension;
import eldunari.form.annotation.InputField;
import eldunari.form.annotation.FormLabel;
import eldunari.form.components.ExternalLookup;
import eldunari.form.components.Label;
import eldunari.form.interfaces.IComponent;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;
import eldunari.origin.annotation.Relation;
import eldunari.origin.classes.helper.ColumnDefinition;
import eldunari.origin.classes.helper.SQLiteHelper;
import eldunari.origin.interfaces.IObject;

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
					lbl.setNeighborString(fl.neighborName());
					lbl.setLocation(getComponentByName(fl.neighborName()), fl.orientation());
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
					com.setLocation(getComponentByName(ifield.neighborName()), ifield.orientation());

					if(ifield.cls().equals(ExternalLookup.class)){
						@SuppressWarnings("unchecked")
						ColumnDefinition column = SQLiteHelper.getDefinition((Class<? extends IObject>)currentClass, field);
						ArrayList<Relation> relations = column.getRelations();
						if(relations != null && relations.size() > 0){
							ExternalLookup lookup = (ExternalLookup) com;
							lookup.setLookupClass(relations.get(0).table());
							components.add(lookup.getButton());
						}
					}

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
