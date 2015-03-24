package eldunari.form.components;

import javax.swing.JButton;

import eldunari.form.interfaces.IComponent;

public class Button extends JButton implements IComponent{
	private static final long serialVersionUID = 3430232108646304221L;
	
	public Button(){}
	public Button(String text){
		this.setText(text);
	}
	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
}
