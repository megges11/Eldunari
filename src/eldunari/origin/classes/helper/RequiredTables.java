package eldunari.origin.classes.helper;
import java.util.ArrayList;

import eldunari.origin.classes.Connector;
import eldunari.origin.classes.SqlConnector;
import eldunari.origin.classes.SqliteConnector;
import eldunari.origin.interfaces.IConnectable;
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

	public void Initialize(boolean sqlite,String constring){
		try{
			if(required == null){
				required = new ArrayList<Class<? extends IObject>>();
			}
			IConnectable con = null;
			Connector connect = new Connector();
			if(sqlite){
				con = new SqliteConnector();			
			}else{
				con = new SqlConnector();
			}
			con.setConnectionString(constring);
			for(Class<? extends IObject> cls : required){
				connect.Initialize(con,cls);
				if(con.hasError()){
					System.err.println(con.getError());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}	
}
