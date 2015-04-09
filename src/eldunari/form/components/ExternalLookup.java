package eldunari.form.components;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;
import eldunari.origin.classes.helper.WhereDefinition;
import eldunari.origin.interfaces.IObject;

public class ExternalLookup extends TextField implements ActionListener{
	private static final long serialVersionUID = 9183548889761593993L;

	private Class<? extends IObject> cls = null;
	boolean allowAdd = false;
	WhereDefinition filter;
	Button btn = new Button();
	
	public ExternalLookup(){
		btn.setSize(25,25);
		btn.addActionListener(this);
	}
	
	public ExternalLookup(Class<? extends IObject> cls,boolean allowAdd){
		btn.setSize(25,25);
		btn.addActionListener(this);
		try{
			this.setLookupClass(cls);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		this.allowAdd = allowAdd;
	}
	
	@Override
	public void setLocation(IComponent component, Orientation orientation){
		super.setLocation(component,orientation);
		btn.setLocation(this,Orientation.Right);
	}
	
	@Override
	public void setName(String value){
		super.setName(value);
		btn.setNeighborString(value);
	}
	
	@Override
	public void setLocation(Point point){
		super.setLocation(point);
		btn.setLocation(this,Orientation.Right);
	}
	@Override
	public int getWidth(){
		return super.getWidth()+btn.getWidth();
	}
	@Override
	public void setSizePercent(Component parent,int percentWidth, int percentHeight){
		super.setSizePercent(parent, percentWidth, percentHeight);
		btn.setSizePercent(parent,percentWidth,percentHeight);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SelectionForm form = new SelectionForm(cls,allowAdd);
		form.showSelection();
	}

	public Class<? extends IObject> getLookupClass() {
		return cls;
	}

	public void setLookupClass(Class<? extends IObject> cls) {
		this.cls = cls;
	}

	public Button getButton() {
		return btn;
	}
	
}
