package eldunari.origin.enumeration;

public enum Direction {

	Descending("DESC"),
	Ascending("ASC");
	
	Direction(String val){
		this.value = val;
	}
	public String getValue() {
		return value;
	}
	private final String value;
	
}
