package eldunari.general.classes;

public class ClassFinder {
	
	public static Class<?> find(String name){
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {}
		return null;
	}
}