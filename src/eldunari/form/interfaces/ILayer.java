package eldunari.form.interfaces;

import java.awt.Container;

import eldunari.form.enumeration.ViewType;

public interface ILayer {

	Container getContainer(Container container,ViewType type);
	
	boolean ContainsGrid();
	Container loadGrid(ViewType type, Container container);

}
