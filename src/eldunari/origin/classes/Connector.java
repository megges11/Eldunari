package eldunari.origin.classes;
import eldunari.form.annotation.DataModel;
import eldunari.general.classes.ClassFinder;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;
import eldunari.origin.annotation.Column;
import eldunari.origin.annotation.Relation;
import eldunari.origin.classes.helper.ColumnDefinition;
import eldunari.origin.classes.helper.OrderByDefinition;
import eldunari.origin.classes.helper.QueryResult;
import eldunari.origin.classes.helper.SQLiteHelper;
import eldunari.origin.classes.helper.SQLiteHelperResult;
import eldunari.origin.classes.helper.WhereDefinition;
import eldunari.origin.enumeration.DataType;
import eldunari.origin.interfaces.IConnectable;
import eldunari.origin.interfaces.IObject;
import eldunari.origin.interfaces.ITrigger;
import eldunari.origin.interfaces.IValidator;
import eldunari.origin.interfaces.IView;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Connector{

	public String VALIDATOR_PACKAGE = "mpad.contracts.validator";
	public String TRIGGER_PACKAGE = "mpad.contracts.trigger";

	public boolean Initialize(IConnectable connector,Class<? extends IObject> cls) throws Exception{	
		SQLiteHelper helper = new SQLiteHelper(cls);
		SQLiteHelperResult result = helper.getTableQuery();
		if(result.isSuccess()){	
			connector.executeUpdate(result.getValue());
		}else{
			throw new Exception(result.getMessage());
		}
		return result.isSuccess();
	}

	public boolean Insert(IConnectable connector,IObject obj){
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
				}
				OutputHandler.Message(OutputType.Error,error);
				return false;
			}
		}

		SQLiteHelper helper = new SQLiteHelper(obj);
		SQLiteHelperResult result = helper.getInsertQuery();
		System.out.println(result.getValue());
		if(result.isSuccess()){	
			if (connector.executeUpdate(result.getValue()) != 0){
				if(trigger != null)
					obj = trigger.PostItem(obj);
				return true;
			}
		}return false;
	}

	public boolean Delete(IConnectable connector,IObject obj){
		SQLiteHelper helper = new SQLiteHelper(obj);
		SQLiteHelperResult result = helper.getDeleteQuery();
		if(result.isSuccess()){
			return connector.executeUpdate(result.getValue())!=0;
		}
		return false;		
	}

	public boolean Update(IConnectable connector, IObject obj, IObject toUpdate){
		SQLiteHelper helper = new SQLiteHelper(obj);
		SQLiteHelperResult result = helper.getUpdateQuery(toUpdate);
		if(result.isSuccess() && !result.getValue().isEmpty()){
			return connector.executeUpdate(result.getValue())!=0;
		}
		return false;
	}

	public static boolean implementsInterface(Class<?> cls, Class<?> implementedClass){
		for(Class<?> iface : cls.getInterfaces()){
			if(iface.equals(implementedClass)){
				return true;
			}
		}
		return false;
	}

	public <T extends IObject> ArrayList<T> Select(IConnectable connector, Class<T> cls, String[] fieldnames, WhereDefinition[] where, OrderByDefinition[] orderby,String groupby,int limit){
		SQLiteHelper helper = new SQLiteHelper(cls);		
		SQLiteHelperResult result = null;
		if(implementsInterface(cls,IView.class)){
			result = helper.getSelectViewQuery(where);
		}else{
			result = helper.getSelectQuery(fieldnames, where, orderby,groupby,limit);
		}
		if(result.isSuccess()){
			QueryResult queryresult = connector.executeQuery(result.getValue());
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

	public <T extends IObject> T ResultToObject(ResultSet result,Class<T> cls){
		try{
			T obj = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);

				ColumnDefinition definition = SQLiteHelper.getDefinition(cls, fields[i]);

				Column column = definition.getColumn();
				if(column != null){
					if(ResultSetContains(result,column.name())){
						fields[i] = setFieldValue(column,fields[i],result.getString(column.name()),obj);
					}
				}
			}
			return obj;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public boolean ResultSetContains(ResultSet result, String columnname){
		try{
			return result.findColumn(columnname) != -1;
		}catch(Exception ex){
			return false;
		}

	}

	public Field setFieldValue(Column column,Field field,String value,IObject obj) {
		try {
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
			if(type != null && !value.isEmpty()){				
				if(type.equals(int.class) || type.equals(Integer.class)){
					field.setInt(obj,Integer.parseInt(value+""));
				}else if(type.equals(boolean.class) || type.equals(Boolean.class)){
					field.setBoolean(obj,Boolean.parseBoolean(value+""));
				}else if(type.equals(byte.class) || type.equals(Byte.class)){
					field.setByte(obj, Byte.parseByte(value+""));					
				}else if(type.equals(char.class)){
					field.setChar(obj, (value+"").charAt(0));
				}else if(type.equals(double.class) || type.equals(Double.class)){
					field.setDouble(obj, Double.parseDouble(value+""));
				}else if(type.equals(float.class) || type.equals(Float.class)){
					field.setFloat(obj, Float.parseFloat(value+""));
				}else if(type.equals(long.class) || type.equals(Long.class)){
					field.setLong(obj,Long.parseLong(value+""));
				}else if(type.equals(short.class) || type.equals(Short.class)){
					field.setShort(obj, Short.parseShort(value+""));
				}else if(type.equals(Date.class)){
					if(value != null){
						long val = Long.parseLong(value+"");
						field.set(obj, new Date(val));
					}
				}else if(type.equals(Calendar.class)){
					if(value != null){
						long val = Long.parseLong(value+"");
						field.set(obj, new Date(val));
					}
				}else{
					field.set(obj, value);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return field;
	}

	public ITrigger getTrigger(Object obj){
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

	public IValidator getValidator(IObject obj){
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

	public static ArrayList<RelationInfo> getRelations(Class<? extends IObject> cls){
		ArrayList<RelationInfo> relations = new ArrayList<RelationInfo>();
		Field[] fields = cls.getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			Relation rel = field.getAnnotation(Relation.class);
			if(rel != null){
				relations.add(new RelationInfo(rel.table(),rel.field(),rel.name(),rel.action(),rel.rule()));
			}
		}
		return relations;
	}
}
