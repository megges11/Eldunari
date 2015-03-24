package eldunari.form.components;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import eldunari.form.classes.GridModel;
import eldunari.form.interfaces.IComponent;

public class Grid extends JTable implements IComponent{
	private static final long serialVersionUID = -5194270868779952867L;
	private String tag;
	
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

	@Override
	public void setTag(String value) {
		this.tag = value;
	}
	@Override
	public String getTag() {
		return tag;
	}
	
}

