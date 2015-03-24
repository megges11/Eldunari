package eldunari.origin.interfaces;

import java.util.ArrayList;

public interface IValidator {
	void isValid(IObject item);
	public ArrayList<String> Error();
	public boolean hasError();
}
