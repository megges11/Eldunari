package eldunari.form.components;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JSpinner;

import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;


public class Spinner extends JSpinner implements IComponent{
	private static final long serialVersionUID = -3120915713633156230L;
	
	private String tag;
	private int percentWidth;
	private int percentHeight;
	
	private String neighborName;
	
	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;
	
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
	@Override
	public void setLocation(IComponent com, Orientation orientation) {
		this.setLocation(VisualHelper.GetPosition(com,orientation));
		neighborName = com.getName();
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
	
	private boolean lockx;
	private boolean locky;	
	
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
