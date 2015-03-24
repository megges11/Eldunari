package eldunari.form.classes;

import javax.swing.JMenuItem;

public class MenuItem {

	private JMenuItem item;
	private String parent;
	
	public MenuItem(JMenuItem item,String parentName){
		this.setItem(item);
		this.setParent(parentName);
	}

	public JMenuItem getItem() {
		return item;
	}

	public void setItem(JMenuItem item) {
		this.item = item;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}	
}
