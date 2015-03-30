package eldunari.form.interfaces;

import java.awt.Container;
import java.awt.Dimension;

import eldunari.form.components.Form;
import eldunari.form.enumation.ViewType;

public interface ILayer {

	Container getContainer(Dimension dimension,Form frame,ViewType type);
	
	boolean ContainsGrid();
	Container loadGrid(ViewType type, Container container);

}
