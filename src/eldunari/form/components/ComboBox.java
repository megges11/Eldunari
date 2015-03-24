package eldunari.form.components;

import javax.swing.JComboBox;

import eldunari.form.interfaces.IComponent;

public class ComboBox<T> extends JComboBox<T> implements IComponent{
	private static final long serialVersionUID = 5384877250228602940L;

	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
	
	
}
