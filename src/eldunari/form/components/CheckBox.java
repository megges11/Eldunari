package eldunari.form.components;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JCheckBox;

import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumeration.Orientation;
import eldunari.form.interfaces.IComponent;

public class CheckBox extends JCheckBox implements IComponent{
	private static final long serialVersionUID = -8054510212083812687L;

	private String tag;
	private Orientation orientation;
	private IComponent neighborComponent;
	private int percentWidth;
	private int percentHeight;
	
	private boolean lockx;
	private boolean locky;
	
	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;
	
	public Point getLocationXY(){
		return super.getLocation();
	}	
		
	public void setLocation(IComponent com, Orientation orientation){
		this.orientation = orientation;
		this.neighborComponent = com;
		this.setLocation(VisualHelper.GetPosition(com,orientation));
	}
	
	public void setTag(String value){
		this.tag = value;
	}
	public String getTag(){
		return this.tag;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}
	
	public void setSize(int width,int height){
		minHeight = height;
		maxHeight = height;
		minWidth = width;
		maxWidth = width;
		super.setSize(width, height);
	}
	
	@Override
	public IComponent getNeighbor(){
		return neighborComponent;
	}
	@Override
	public void setSizePercent(Component parent,int percentWidth, int percentHeight) {
		this.percentWidth = percentWidth;
		this.percentHeight = percentHeight;
		super.setSize(parent.getWidth()*(percentWidth/100),parent.getHeight()*(percentHeight/100));
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

	public void setValue(Object obj){
		if(obj != null){
			this.setText(String.valueOf(obj));
		}		
	}
	public Object getValue(){
		return this.getText();
	}
	private String neighborName;
	public void setNeighborString(String value){
		neighborName = value;
	}
	public String getNeightborName(){
		return neighborName;
	}
	public void setOrientation(Orientation orientation){
		this.orientation = orientation;
	}

	@Override
	public void setEditable(boolean value) {
		this.setEnabled(value);
	}

	@Override
	public boolean isEditable() {
		return this.isEnabled();
	}
}
