package eldunari.form.interfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import eldunari.form.enumeration.Orientation;

public interface IComponent {
	void setTag(String value);
	String getTag();
	void setLocation(IComponent com, Orientation orientation);
	Orientation getOrientation();
	IComponent getNeighbor();
	
	void setNeighborString(String value);
	String getNeightborName();
	void setOrientation(Orientation orientation);
	
	void setSizePercent(Component parent,int percentWidth,int percentHeight);
	int getPercentWidth();
	int getPercentHeight();
	
	void setMax(int width, int height);
	void setMin(int width, int height);
	int getMinWidth();
	int getMaxWidth();
	int getMinHeight();
	int getMaxHeight();
	
	void setSize(int width, int height);
	Dimension getSize();
	
	void setLockedX(boolean value);
	void setLockedY(boolean value);
	boolean isLockedX();
	boolean isLockedY();
	
	Object getValue();
	void setValue(Object obj);

	Point getLocation();
	Point getLocationXY();		
	void setLocation(int x, int y);
	
	void setName(String value);
	String getName();
	
	void setEditable(boolean value);
	boolean isEditable();
	
}
