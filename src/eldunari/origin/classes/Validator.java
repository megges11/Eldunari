package eldunari.origin.classes;

import java.util.ArrayList;

import eldunari.origin.interfaces.IObject;
import eldunari.origin.interfaces.IValidator;

public abstract class Validator implements IValidator{

	public ArrayList<String> error = new ArrayList<String>();

	public void isGreater(int proof, int min,String message){
		if(!(proof > min)){
			error.add(message);
		}
	}
	
	public void isNotNull(Object obj,String message){
		if(obj == null){
			error.add(message);
		}
	}		
	public void isNotNull(String obj,String message){
		if(obj == null || obj.isEmpty()){
			error.add(message);
		}
	}
	public void isNotNull(Object obj){
		if(obj == null){
			error.add("Objekt darf nicht null sein");
		}
	}		
	public void isNotNull(String obj){
		if(obj == null || obj.isEmpty()){
			error.add("Objekt darf nicht null sein");
		}
	}
	public ArrayList<String> Error(){
		return this.error;
	}
	
	public boolean hasError(){
		return (this.error.size()==0) ? false :true;
	}
	
	@Override
	public abstract void isValid(IObject item);

}
