package contracts.kernel.enumeration;

public enum ConstraintRule {

	CASCADE("CASCADE"),
	RESTRICT("RESTRICT"), 
	NO_ACTION("NO ACTION"), 
	SET_DEFAULT("SET DEFAULT"),
	SET_NULL("SET NULL"),
	NONE("NONE");
	
	ConstraintRule(String value){
		this.value = value;
	}
	private final String value;
	public String getValue(){
		return value;
	}
}
