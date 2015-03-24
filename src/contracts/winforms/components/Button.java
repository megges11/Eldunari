package contracts.winforms.components;

import javax.swing.JButton;

import contracts.kernel.interfaces.IComponent;

public class Button extends JButton implements IComponent{
	private static final long serialVersionUID = 3430232108646304221L;
	
	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
}
