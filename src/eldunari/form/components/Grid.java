package eldunari.form.components;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import eldunari.form.classes.GridModel;
import eldunari.form.classes.helper.GridHelper;
import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumeration.Orientation;
import eldunari.form.interfaces.IComponent;

public class Grid extends JTable implements IComponent{
	private static final long serialVersionUID = -5194270868779952867L;
	private String tag;
	private int percentHeight;
	private int percentWidth;
	private Orientation orientation;
	private IComponent neighborComponent;	

	private ArrayList<?> mItems;
	private final Class<?> cls;

	private Object[] columns;
	
	private boolean lockx;
	private boolean locky;	

	private int maxWidth;
	private int minWidth;
	private int maxHeight;
	private int minHeight;	

	public Grid(Class<?> cls){
		this.cls = cls;
		this.mItems = null;
		this.columns = null;
	}

	public Grid(Class<?> cls,ArrayList<?> items,Object[][] rows, Object[] columns){
		super(rows,columns);
		this.cls = cls;
		mItems = items;
		this.columns = columns;
	}
	public Grid(Class<?> cls, GridModel dm){
		super(dm);
		this.cls = cls;
		mItems = dm.getItems();
		this.columns = dm.getColumns();
	}	

	public ScrollPane getTableWithHead(){
		ScrollPane scp = new ScrollPane(this,this);
		scp.setSize(this.getSize());
		scp.setLocation(this.getLocation());
		return scp;
	}
	
	public void reload(Object[][] data){
		((DefaultTableModel) this.getModel()).setDataVector(data, columns);
	}
	
	public void reload(ArrayList<?> data,String[] fieldnames){
		mItems = data;
		GridHelper helper = new GridHelper(cls,mItems,fieldnames);	
		((DefaultTableModel) this.getModel()).setDataVector(helper.getRows(), helper.getColumns());
	}
	
	public void reload(Object[][] data,Object[] columns){
		this.columns = columns;
		((DefaultTableModel) this.getModel()).setDataVector(data, columns);
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
	public void setLocation(IComponent com, Orientation orientation){
		this.orientation = orientation;
		this.neighborComponent = com;
		this.setLocation(VisualHelper.GetPosition(com,orientation));
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
	public ArrayList<?> getItems() {
		return mItems;
	}
	public Class<?> getCls() {
		return cls;
	}
	@Override
	public void setEditable(boolean value) {
		this.setEnabled(value);
	}
	@Override
	public boolean isEditable() {
		return this.isEnabled();
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
	public Object[] getColumns() {
		return columns;
	}

}

