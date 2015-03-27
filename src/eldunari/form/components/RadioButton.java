package eldunari.form.components;

import java.awt.Component;

import javax.swing.JRadioButton;

import eldunari.form.classes.VisualHelper;
import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;

public class RadioButton extends JRadioButton implements IComponent{
	private static final long serialVersionUID = -4187208235979508932L;

	private String tag;
	private int percentWidth;
	private int percentHeight;
	
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
	
	@Override
	public void setLocation(Component com, Orientation orientation) {
		this.setLocation(VisualHelper.GetPosition(com,orientation));
	}
	@Override
	public Orientation getOrientation() {
		return null;
	}
	@Override
	public Component getNeighbor() {
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

	public void setValue(Object obj){
		this.setSelected((boolean) obj);
	}
	public Object getValue(){
		return this.isSelected();
	}
}
