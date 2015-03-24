package eldunari.origin.classes;
import java.util.ArrayList;

import eldunari.origin.interfaces.IObject;

public class RequiredTables {

	private ArrayList<Class<? extends IObject>> required;
	
	public void AddRequired(Class<? extends IObject> cls){
		if(required == null){
			required = new ArrayList<Class<? extends IObject>>();
		}
		required.add(cls);
	}
	public void ClearRequired(){
		if(required == null){
			required = new ArrayList<Class<? extends IObject>>();
		}
		required.clear();
	}
	public boolean RemoveRequired(Class<? extends IObject> cls){
		return required.remove(cls);
	}
	
	public void Initialize(String dbname) throws Exception{
		if(required == null){
			required = new ArrayList<Class<? extends IObject>>();
		}
		Connector con = new Connector();
		for(Class<? extends IObject> cls : required){
			con.Initialize(dbname,cls);
			if(con.hasError()){
				System.err.println(con.getError(";"));
			}
		}
	}	
}
