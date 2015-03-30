package eldunari.form.components;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JTable;

import eldunari.form.classes.GridModel;
import eldunari.form.classes.VisualHelper;
import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;

public class Grid<T> extends JTable implements IComponent{
	private static final long serialVersionUID = -5194270868779952867L;
	private String tag;
	private int percentHeight;
	private int percentWidth;
	private Orientation orientation;
	private Component neighborComponent;	
	
	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;	
	
	public Grid(){

	}
	public Grid(int numRows, int numColumns){
		super(numRows,numColumns);
	}
	public Grid(Object[][] rows, Object[] columns){
		super(rows,columns);
	}
	public Grid(GridModel dm){
		super(dm);
	}	

	public ScrollPane getTableWithHead(){
		ScrollPane scp = new ScrollPane(this,this);
		scp.setSize(this.getSize());
		scp.setLocation(this.getLocation());
		return scp;
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
	public void setLocation(Component com, Orientation orientation){
		this.orientation = orientation;
		this.neighborComponent = com;
		this.setLocation(VisualHelper.GetPosition(com,orientation));
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
		this.percentHeight = percentHeight;
		this.percentWidth = percentWidth;
		this.setSize((parent.getWidth()*(percentWidth))/100,(parent.getHeight()*(percentHeight))/100);
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
	
	public void setValue(Object obj){}
	public Object getValue(){
		return null;
	}

}

