package form.interfaces;

import java.awt.Container;
import java.awt.Dimension;
import form.classes.Form;

public interface IGridLayer {

	Container getContainer(Dimension dimension,Form frame,boolean onClickEnabled);
}
