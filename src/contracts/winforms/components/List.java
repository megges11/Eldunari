package contracts.winforms.components;

import javax.swing.JList;

import contracts.kernel.interfaces.IComponent;

public class List<T> extends JList<T> implements IComponent{
	private static final long serialVersionUID = 953889140162924389L;

	private String tag;
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
}
