package eldunari.form.interfaces;

import java.awt.Container;
import java.awt.Dimension;

import eldunari.form.components.Form;

public interface IGridLayer {

	Container getContainer(Dimension dimension,Form frame,boolean onClickEnabled);
}
