package contracts.winforms.controlling;

import javax.swing.table.DefaultTableModel;

public class GridModel extends DefaultTableModel{
	private static final long serialVersionUID = -5864299213839988818L;
	private boolean isEditable = false;
	
	public GridModel(Object[][] rows, Object[] columns){
		super(rows,columns);
	}
	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return isEditable;
	}
}
