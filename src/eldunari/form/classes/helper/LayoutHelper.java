package eldunari.form.classes.helper;

import java.awt.Component;
import java.awt.Container;

import eldunari.form.interfaces.IComponent;

public class LayoutHelper {

	public static IComponent getComponentByName(String name, Container container){
		for(Component component : container.getComponents()){
			if(component instanceof IComponent){
				IComponent com = (IComponent) component;
				if(com.getName().equals(name)){
					return com;
				}
			}
		}
		return null;
	}
	
}
