package eldunari.form.components;
import eldunari.origin.interfaces.IObject;

public class SelectionForm extends Form {
	private static final long serialVersionUID = 3249240093718548213L;

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

	public void showSelection() {
		this.setVisible(true);		
	}

	
	
}
