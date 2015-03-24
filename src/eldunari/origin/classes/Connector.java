package eldunari.origin.classes;
import eldunari.general.classes.ClassFinder;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;
import eldunari.origin.annotation.Column;
import eldunari.origin.annotation.DataModel;
import eldunari.origin.enumeration.DataType;
import eldunari.origin.interfaces.IObject;
import eldunari.origin.interfaces.ITrigger;
import eldunari.origin.interfaces.IValidator;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Connector {

	private ArrayList<String> error = new ArrayList<String>();
	public String VALIDATOR_PACKAGE = "mpad.contracts.validator";
	public String TRIGGER_PACKAGE = "mpad.contracts.trigger";

	private Connection getConnection(String name){
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
		} catch ( Exception e ) {
			addError( e.getClass().getName() + ": " + e.getMessage() );
		}
		return con;
	}	
	public boolean Initialize(String dbname,Class<? extends IObject> cls) throws Exception{	
		SQLiteHelper helper = new SQLiteHelper(cls);
		SQLiteHelperResult result = helper.getTableQuery();
		if(result.isSuccess()){	
			executeUpdate(dbname, result.getValue());
		}else{
			throw new Exception(result.getMessage());
		}
		return result.isSuccess();
	}

	public boolean Insert(String dbname,IObject obj){
		ITrigger trigger = getTrigger(obj);
		if(trigger != null){
			obj = trigger.PreItem(obj);
		}

		IValidator validator = getValidator(obj);
		if(validator != null){
			validator.isValid(obj);
			if(validator.hasError()){
				String error = "";
				for(String err:validator.Error()){
					error += err+"\n";
					this.error.add(err);
				}
				OutputHandler.Message(OutputType.Error,error);
				return false;
			}
		}

		SQLiteHelper helper = new SQLiteHelper(obj);
		SQLiteHelperResult result = helper.getInsertQuery();
		if(result.isSuccess()){	
			if (executeUpdate(dbname,result.getValue()) != 0){
				if(trigger != null)
					obj = trigger.PostItem(obj);
				return true;
			}
		}return false;
	}

	public boolean Delete(String dbname,IObject obj){
		SQLiteHelper helper = new SQLiteHelper(obj);
		SQLiteHelperResult result = helper.getDeleteQuery();
		if(result.isSuccess()){
			return executeUpdate(dbname,result.getValue())!=0;
		}
		return false;		
	}

	public boolean Update(String dbname, IObject obj, IObject toUpdate){
		SQLiteHelper helper = new SQLiteHelper(obj);
		SQLiteHelperResult result = helper.getUpdateQuery(toUpdate);
		if(result.isSuccess()){
			return executeUpdate(dbname,result.getValue())!=0;
		}
		return false;
	}
	
	public <T extends IObject> ArrayList<T> Select(String dbname, Class<T> cls, String[] fieldnames, WhereDefinition[] where, OrderByDefinition[] orderby,String groupby,int limit){

		SQLiteHelper helper = new SQLiteHelper(cls);
		SQLiteHelperResult result = helper.getSelectQuery(fieldnames, where, orderby,groupby,limit);
		if(result.isSuccess()){
			QueryResult queryresult = executeQuery(dbname,result.getValue());
			if(queryresult == null || queryresult.getResult() == null){
				return null;
			}
			try {
				ArrayList<T> items = new ArrayList<T>();
				ResultSet res = queryresult.getResult();
				while(res.next()){
					T obj = ResultToObject(res,cls);
					items.add(obj);
				}
				queryresult.getStatement().close();
				queryresult.getConnection().close();
				return items;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private <T extends IObject> T ResultToObject(ResultSet result,Class<T> cls){
		try{
			T obj = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				Column column = fields[i].getAnnotation(Column.class);
				if(column != null){
					fields[i] = setFieldValue(fields[i],result.getString(column.name()),obj);
				}
			}
			return obj;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	private Field setFieldValue(Field field,String value,IObject obj) {
		try {
			Column column = field.getAnnotation(Column.class);
			Class<?>[] bestfits = column.type().getBestFitClasses();
			Class<?> type = null;
			for(Class<?> available : bestfits){
				if(available == field.getType()){
					type = available;
				}
			}
			if(column.type() == DataType.AUTO ){
				type = field.getType();
			}
			if(type != null ){
				if(type.equals(int.class) || type.equals(Integer.class)){
					field.setInt(obj,Integer.parseInt(value));
				}else if(type.equals(boolean.class) || type.equals(Boolean.class)){
					field.setBoolean(obj,Boolean.parseBoolean(value));
				}else if(type.equals(byte.class) || type.equals(Byte.class)){
					field.setByte(obj, Byte.parseByte(value));					
				}else if(type.equals(char.class)){
					field.setChar(obj, value.charAt(0));
				}else if(type.equals(double.class) || type.equals(Double.class)){
					field.setDouble(obj, Double.parseDouble(value));
				}else if(type.equals(float.class) || type.equals(Float.class)){
					field.setFloat(obj, Float.parseFloat(value));
				}else if(type.equals(long.class) || type.equals(Long.class)){
					field.setLong(obj,Long.parseLong(value));
				}else if(type.equals(short.class) || type.equals(Short.class)){
					field.setShort(obj, Short.parseShort(value));
				}else if(type.equals(Date.class)){
					long val = Long.parseLong(value);
					field.set(obj, new Date(val));
				}else if(type.equals(Calendar.class)){
					long val = Long.parseLong(value);
					field.set(obj, new Date(val));
				}else{
					field.set(obj, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field;
	}

	private QueryResult executeQuery(String dbname,String sql){
		try{
			Connection con = getConnection(dbname);
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			return new QueryResult(con,stmt,result);			
		}catch(Exception ex){
			return null;
		}
	}
	private int executeUpdate(String dbname,String sql){
		try{
			Connection con = getConnection(dbname);
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);
			stmt.close();
			con.close();
			return result;	
		}catch(Exception ex){
			addError(ex.getMessage());
			return 0;
		}
	}	

	private ITrigger getTrigger(Object obj){
		try{
			Class<?> cls = obj.getClass();
			Class<?> trigger = ClassFinder.find(TRIGGER_PACKAGE+"."+cls.getSimpleName()+"_Trigger");
			if(trigger != null){
				DataModel model = trigger.getAnnotation(DataModel.class);
				if( model != null ){
					if(model.value().equals(cls)){
						return (ITrigger)trigger.newInstance();
					}
				}
			}
		}catch(Exception ex){
			OutputHandler.Message(OutputType.Error, ex.getMessage());
		}
		return null;		
	}

	private IValidator getValidator(IObject obj){
		try{
			Class<? extends IObject> cls = obj.getClass();
			Class<?> validator = ClassFinder.find(VALIDATOR_PACKAGE+"."+cls.getSimpleName()+"_Validator");
			if(validator != null){
				DataModel model = validator.getAnnotation(DataModel.class);
				if( model != null ){
					if(model.value().equals(cls)){
						return (IValidator)validator.newInstance();
					}
				}
			}
		}catch(Exception ex){
			OutputHandler.Message(OutputType.Error, ex.getMessage());
		}
		return null;		
	}

	public static Field setFieldValue(Field field, Object obj, Object value){
		try{
			Class<?> typecls = field.getType();
			if(value.getClass() == String.class){
				String val = (String) value;
				if(val.isEmpty()){ return field; }
			}
			if(typecls.equals(int.class)){
				field.set(obj,Integer.parseInt((String) value));
			}else if(typecls.equals(String.class)){
				field.set(obj, value.toString());
			}else if(typecls.equals(Calendar.class)){
				//				String calstr = value.toString();
				//				field.set(obj, VisualHelper.ToCalendar(calstr));
			}else if(typecls.equals(double.class)){
				field.set(obj, Double.parseDouble(value.toString()));
			}else{
				field.set(obj, value);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return field;
	}

	//	public boolean UpdateOrInsert(String dbname,IObject obj,IObject currentObject){
	//		if(currentObject != null){
	//			Update(dbname,obj.getClass(),obj,currentObject);
	//			return true;
	//		}else{
	//			return Insert(dbname,obj);
	//		}
	//	}

	private void addError(String message){
		error.add(message);
	}
	public boolean hasError(){
		return error.size()!=0;
	}
	public ArrayList<String> getError(){
		return error;
	}
	public String getError(String seperator){
		String err = "";
		for(String e : error){
			err +=e+seperator;
		}
		return err;
	}
	public void clearError(){
		error.clear();
	}
}
