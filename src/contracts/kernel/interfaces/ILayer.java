package contracts.kernel.interfaces;

import java.awt.Container;
import java.awt.Dimension;

import contracts.kernel.enumeration.ViewType;
import contracts.winforms.MainFrame;

public interface ILayer {

	Container getContainer(Dimension dimension,MainFrame frame,ViewType type);
	
	boolean ContainsGrid();
	Container loadGrid(ViewType type, Container container);
	
	void setId(int id);
	int getId();
}
