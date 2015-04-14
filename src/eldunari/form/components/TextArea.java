package eldunari.form.components;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JTextArea;

import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumeration.Orientation;
import eldunari.form.interfaces.IComponent;

public class TextArea extends JTextArea implements IComponent{
	private static final long serialVersionUID = 8341242762223677648L;
	
	private String tag;
	private Orientation orientation;
	private IComponent neighborComponent;
	private String neighborName;
	private int percentWidth;
	private int percentHeight;

	private boolean lockx;
	private boolean locky;

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
	public void setNeighborString(String value) {
		neighborName = value;
	}

	@Override
	public String getNeightborName() {
		return neighborName;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public void setSizePercent(Component parent, int percentWidth,
			int percentHeight) {
		this.percentHeight = percentHeight;
		this.percentWidth = percentWidth;
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
	public void setMax(int width, int height) {
		this.maxHeight = height;
		this.maxWidth = width;
	}

	@Override
	public void setMin(int width, int height) {
		this.minHeight = height;
		this.minWidth = width;
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
	public void setLockedX(boolean value) {
		this.lockx = value;
	}

	@Override
	public void setLockedY(boolean value) {
		this.locky = value;
	}

	@Override
	public boolean isLockedX() {
		return lockx;
	}

	@Override
	public boolean isLockedY() {
		return locky;
	}

	@Override
	public Object getValue() {
		return this.getText();
	}

	@Override
	public void setValue(Object obj) {
		if(obj != null){
			this.setText(String.valueOf(obj));
		}		
	}

	@Override
	public Point getLocationXY() {
		return super.getLocation();
	}

}
