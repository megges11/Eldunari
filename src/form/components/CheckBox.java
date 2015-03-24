package form.components;

import javax.swing.JCheckBox;

import form.interfaces.IComponent;

public class CheckBox extends JCheckBox implements IComponent{
	private static final long serialVersionUID = -8054510212083812687L;

	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
	
}
