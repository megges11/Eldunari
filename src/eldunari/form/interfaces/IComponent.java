package eldunari.form.interfaces;

import java.awt.Component;
import java.awt.Point;

import eldunari.form.enumation.Orientation;

public interface IComponent {
	void setTag(String value);
	String getTag();
	void setLocation(Component com, Orientation orientation);
	Orientation getOrientation();
	Component getNeighbor();
	
	void setSizePercent(Component parent,int percentWidth,int percentHeight);
	int getPercentWidth();
	int getPercentHeight();
	
	void setMax(int width, int height);
	void setMin(int width, int height);
	int getMinWidth();
	int getMaxWidth();
	int getMinHeight();
	int getMaxHeight();
	
	public void setLockedX(boolean value);
	public void setLockedY(boolean value);
	public boolean isLockedX();
	public boolean isLockedY();
	
	public Object getValue();
	public void setValue(Object obj);

	public Point getLocation();
	public Point getLocationXY();		
	public void setLocation(int x, int y);
	
	
}
