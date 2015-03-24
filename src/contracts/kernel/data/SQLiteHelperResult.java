package contracts.kernel.data;

public class SQLiteHelperResult {

	private String value;
	private boolean success;
	private String message;

	public SQLiteHelperResult(boolean success, String value, String message){
		this.success = success;
		this.value = value;
		this.message = message;
	}
	public SQLiteHelperResult(boolean success, String value){
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
