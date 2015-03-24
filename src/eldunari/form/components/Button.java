package eldunari.form.components;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import eldunari.form.classes.CallMethod;
import eldunari.form.classes.VisualHelper;
import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;

public class Button extends JButton implements IComponent{
	private static final long serialVersionUID = 3430232108646304221L;
	
	private CallMethod callingMethod;	
	private String tag;
	
	public Button(){}
	public Button(String text){
		this.setText(text);
	}
	public Button(String methodname, Class<?> cls){
		this.callingMethod = new CallMethod(cls,methodname);
		this.addActionListener();
	}
	
	public Button(String methodname, Class<?> cls, Class<?>[] types, Object[] params){
		this.callingMethod = new CallMethod(cls,methodname,types,params);
		this.addActionListener();
	}
	
	public void addActionListener(){
		this.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				callingMethod.Call();
			}
		});
	}
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
	
	public void setLocation(Component com, Orientation orientation){
		this.setLocation(VisualHelper.GetPosition(com, orientation));
	}
	
	public CallMethod getCallingMethod() {
		return callingMethod;
	}
}
