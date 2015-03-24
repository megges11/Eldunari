package contracts.kernel.interfaces;

import java.awt.Container;
import java.awt.Dimension;

import contracts.winforms.MainFrame;

public interface IGridLayer {

	Container getContainer(Dimension dimension,MainFrame frame,boolean onClickEnabled);
}
