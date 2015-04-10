package eldunari.origin.classes.helper;

public class QueryHelperResult {

	private String value;
	private boolean success;
	private String message;

	public QueryHelperResult(boolean success, String value, String message){
		this.success = success;
		this.value = value;
		this.message = message;
	}
	public QueryHelperResult(boolean success, String value){
		this.success = success;
		this.value = value;
		this.message = (success) ? "Success" : "Failed" ;
	}
	
	public String getValue(){
		return value;
	}
	public boolean isSuccess(){
		return success;
	}	
	public String getMessage(){
		return message;
	}

}
