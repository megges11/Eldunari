package contracts.winforms.components;

import javax.swing.JTextField;

import contracts.kernel.interfaces.IComponent;

public class TextField extends JTextField implements IComponent{
	private static final long serialVersionUID = 5360775976912551358L;

	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
}
