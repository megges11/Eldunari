package contracts.kernel.enumeration;

public enum Operator {
	Equal("="),
	NotEqual("!="),
	Is("IS"),
	Not("NOT"),
	Less("<"),
	LessEqual("<="),
	Greater(">"),
	GreaterEqual(">="),
	IsNot("IS NOT"),
	Like("LIKE");
	
	Operator(String val){
		value = val;
	}
	public String getValue() {
		return value;
	}
	private final String value;
}
