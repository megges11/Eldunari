package eldunari.form.components;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import eldunari.form.enumeration.ViewType;
import eldunari.form.interfaces.IForm;
import eldunari.origin.interfaces.IObject;

public class SelectionForm extends Form {
	private static final long serialVersionUID = 3249240093718548213L;

	private String text = "";

	public SelectionForm(Class<? extends IObject> currentClass,boolean allowAdd) {
		super(currentClass);
		this.setTitle( "Selection of "+currentClass.getSimpleName());
		if(allowAdd){
			init_menubar();
		}
		this.setLocationRelativeTo(null);
		this.setSize(400,400);
		this.setVisible(false);

	}

	@Override
	public void init_menubar() {}

	public String showSelection() {		
		this.setContainer(currentClass,ViewType.GridViewSelect);		
		this.setVisible(true);	
		this.repaint();		
		
		Component component = this.getContentPane().getComponentAt(0,0);
		if(component instanceof Grid){
			Grid table = (Grid) component;
			table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					Point p = me.getPoint();
					int row = table.rowAtPoint(p);
					if (me.getClickCount() == 2 && row >= 0 ) {	  
						table.getModel().getValueAt(row, 0).toString();						
					}
				}
			});			
		}				
		return text;
	}

	@Override
	public IForm getParentForm() {
		return null;
	}

	@Override
	public void setParentForm(IForm parentForm) {
		
	}
}
