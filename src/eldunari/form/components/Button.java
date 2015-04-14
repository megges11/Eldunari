package eldunari.form.components;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import eldunari.form.classes.CallMethod;
import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumeration.Orientation;
import eldunari.form.interfaces.IComponent;

public class Button extends JButton implements IComponent{
	private static final long serialVersionUID = 3430232108646304221L;
	
	private CallMethod callingMethod;	
	private String tag;
	private int percentWidth;
	private int percentHeight;
	
	private boolean lockx;
	private boolean locky;
	private String neighborName;
	
	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;
	
	
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
	
	public void setSize(int width,int height){
		minHeight = height;
		maxHeight = height;
		minWidth = width;
		maxWidth = width;
		super.setSize(width, height);
	}
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}
	
	public Point getLocationXY(){
		return super.getLocation();
	}	
	
	public void setLocation(int x, int y){
		super.setLocation(x, y);
	}
	
	public void setLocation(IComponent com, Orientation orientation){
		this.setLocation(VisualHelper.GetPosition(com, orientation));
	}
	
	public CallMethod getCallingMethod() {
		return callingMethod;
	}
	@Override
	public Orientation getOrientation() {
		return null;
	}
	@Override
	public IComponent getNeighbor() {
		return null;
	}
	@Override
	public void setSizePercent(Component parent,int percentWidth, int percentHeight) {
		this.percentHeight = percentHeight;
		this.percentWidth = percentWidth;
		this.setSize(parent.getWidth()*(percentWidth/100),parent.getHeight()*(percentHeight/100));
	}
	public int getPercentHeight(){
		return percentHeight;
	}
	public int getPercentWidth(){
		return percentWidth;
	}
	@Override
	public int getMinWidth() {
		return minWidth;
	}
	@Override
	public int getMaxWidth() {
		return maxWidth;
	}
	@Override
	public int getMinHeight() {
		return minHeight;
	}
	@Override
	public int getMaxHeight() {
		return maxHeight;
	}
	@Override
	public void setMax(int width, int height) {
		this.maxHeight = height;
		this.maxWidth = width;
	}
	@Override
	public void setMin(int width, int height) {
		this.minHeight = height;
		this.minWidth = width;	
	}	
	
	public void setLockedX(boolean value){
		this.lockx = value;
	}
	public void setLockedY(boolean value){
		this.locky = value;
	}
	public boolean isLockedX(){
		return lockx;
	}
	public boolean isLockedY(){
		return locky;
	}
	
	public void setValue(Object obj){}
	public Object getValue(){ return null;	}

	@Override
	public void setEditable(boolean value) {
		this.setEnabled(value);
	}
	@Override
	public boolean isEditable() {
		return this.isEnabled();
	}


	public void setNeighborString(String value){
		neighborName = value;
	}
	public String getNeightborName(){
		return neighborName;
	}
	public void setOrientation(Orientation orientation){
//		this.orientation = orientation;
	}
}
