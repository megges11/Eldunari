package eldunari.origin.enumeration;

public enum ConstraintAction {

	ON_UPDATE("ON UPDATE"),
	ON_DELETE("ON DELETE"),
	NONE("NONE");
	
	
	ConstraintAction(String value){
		this.value = value;
	}
	private final String value;
	public String getValue(){
		return this.value;
	}
	
}
