package eldunari.general.enumeration;

public enum Errors {

	ConnectionFail("Failed to open Connection %s");
	
	Errors(String value){
		this.value = value;
	}
	private final String value;
	
	public String getValue(){
		return value;
	}
}
