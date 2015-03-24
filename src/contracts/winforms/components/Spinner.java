package contracts.winforms.components;

import javax.swing.JSpinner;

import contracts.kernel.interfaces.IComponent;


public class Spinner extends JSpinner implements IComponent{
	private static final long serialVersionUID = -3120915713633156230L;
	
	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
	
}
