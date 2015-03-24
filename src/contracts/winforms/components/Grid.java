package contracts.winforms.components;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import contracts.winforms.controlling.GridModel;

public class Grid extends JTable{
	private static final long serialVersionUID = -5194270868779952867L;
	
	public Grid(){
		
	}
	public Grid(int numRows, int numColumns){
		super(numRows,numColumns);
	}
	public Grid(Object[][] rowData, Object[] columnNames){
		super(rowData,columnNames);
	}
	public Grid(GridModel dm){
		super(dm);
	}

	public JScrollPane getTableWithHead(){
		JScrollPane scp = new JScrollPane(this);
		scp.setSize(this.getSize());
		scp.setLocation(this.getLocation());
		return scp;
	}
	
}

