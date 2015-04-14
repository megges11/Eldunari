package eldunari.form.interfaces;

import java.awt.Container;

import eldunari.origin.interfaces.IObject;
import eldunari.form.enumation.ViewType;

public interface IForm {

	Container getContentPane();
	void setContainer(Class<? extends IObject> cls, ViewType type, IObject obj);
	
}
