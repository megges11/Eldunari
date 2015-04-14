package eldunari.form.components;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JScrollPane;

import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumeration.Orientation;
import eldunari.form.interfaces.IComponent;

public class ScrollPane extends JScrollPane implements IComponent{
	private static final long serialVersionUID = 4169579731842793022L;
	private String tag;
	private Orientation orientation;
	private IComponent neighborComponent;
	private int percentWidth;
	private int percentHeight;
	private String neighborName;
	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;
	
	private boolean lockx;
	private boolean locky;	
	
	public ScrollPane(Component component,IComponent com){
		super(component);
		this.setSize(component.getSize());
		this.setLocation(component.getLocation());
		this.maxHeight = com.getMaxHeight();
		this.minHeight = com.getMinHeight();
		this.maxWidth = com.getMaxWidth();
		this.minWidth = com.getMinWidth();
		this.percentHeight = com.getPercentHeight();
		this.percentWidth = com.getPercentWidth();
		this.orientation = com.getOrientation();
		this.neighborComponent = com.getNeighbor();
		this.lockx = com.isLockedX();
		this.locky = com.isLockedY();
		
	}

	@Override
	public void setTag(String value) {
		this.tag = value;
	}

	@Override
	public String getTag() {
		return tag;
	}

	public Point getLocationXY(){
		return super.getLocation();
	}	
	
	public void setLocation(int x, int y){
		super.setLocation(x, y);
	}
	@Override
	public void setLocation(IComponent com, Orientation orientation) {
		this.orientation = orientation;
		this.neighborComponent = com;
		this.setLocation(VisualHelper.GetPosition(com, orientation));
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public IComponent getNeighbor() {
		return neighborComponent;
	}

	@Override
	public void setSizePercent(Component parent,int percentWidth, int percentHeight) {
		this.percentWidth = percentWidth;
		this.percentHeight = percentHeight;
		this.setSize(parent.getWidth()*(percentWidth/100),parent.getHeight()*(percentHeight/100));
	}

	@Override
	public int getPercentWidth() {
		return percentWidth;
	}

	@Override
	public int getPercentHeight() {
		return percentHeight;
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
	public Object getValue(){
		return null;
	}
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
		this.orientation = orientation;
	}
	
}
