package eldunari.form.components;

import java.awt.Component;

import javax.swing.JTextField;

import eldunari.form.classes.VisualHelper;
import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;

public class TextField extends JTextField implements IComponent{
	private static final long serialVersionUID = 5360775976912551358L;

	private String tag;
	
	public void setLocation(Component com, Orientation orientation){
		this.setLocation(VisualHelper.GetPosition(com,orientation));
	}
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
}
