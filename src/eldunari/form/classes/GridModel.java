package eldunari.form.classes;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class GridModel<T> extends DefaultTableModel{
	private static final long serialVersionUID = -5864299213839988818L;
	private boolean isEditable = false;
	private final Class<T> cls;
	private final ArrayList<T> mItems;
	private final Object[] columns;
	
	public GridModel(Class<T> cls,ArrayList<T> items,Object[][] rows, Object[] columns){
		super(rows,columns);
		this.cls = cls;
		this.mItems = items;
		this.columns = columns;
	}
	
	public ArrayList<T> getItems(){		
		return mItems;		
	}
	
	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return isEditable;
	}

	public Class<T> getCls() {
		return cls;
	}

	public Object[] getColumns() {
		return columns;
	}
}
