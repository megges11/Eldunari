package eldunari.form.components;

import java.awt.Component;

import javax.swing.JLabel;

import eldunari.form.classes.VisualHelper;
import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;

public class Label extends JLabel implements IComponent{
	private static final long serialVersionUID = -2998000665145523581L;

	private String tag;
	private Orientation orientation;
	private Component neighborComponent;
	private int percentWidth;
	private int percentHeight;
	
	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;
	
	@Override
	public void setTag(String value) {
		this.tag = value;
	}

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public void setLocation(Component com, Orientation orientation) {
		this.orientation = orientation;
		this.neighborComponent = com;
		this.setLocation(VisualHelper.GetPosition(com, orientation));
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public Component getNeighbor() {
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
		this.setText((String)obj);
	}
	public Object getValue(){
		return this.getText();
	}
	
}
