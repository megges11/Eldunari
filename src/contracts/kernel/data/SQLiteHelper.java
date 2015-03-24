package contracts.kernel.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import contracts.annotation.Column;
import contracts.annotation.DefaultValue;
import contracts.annotation.Precision;
import contracts.annotation.PrimaryKey;
import contracts.annotation.Relation;
import contracts.annotation.Table;
import contracts.kernel.enumeration.ConstraintAction;
import contracts.kernel.enumeration.ConstraintRule;
import contracts.kernel.enumeration.DataType;
import contracts.kernel.helper.OrderByDefinition;
import contracts.kernel.helper.WhereDefinition;
import contracts.kernel.interfaces.IObject;

public class SQLiteHelper {

	private Class<? extends IObject> cls;
	private IObject obj;

	public SQLiteHelper(Class<? extends IObject> cls){
		this.cls = cls;
	}
	public SQLiteHelper(IObject obj){
		this.obj = obj;
		this.cls = obj.getClass();
	}

	public Class<? extends IObject> getCls() {
		return cls;
	}
	public IObject getObj() {
		return obj;
	}

	public SQLiteHelperResult getTableQuery(){
		if(cls == null){
			return new SQLiteHelperResult(false,"","Class<? extends Object> can not be null");
		}
		String sql = "CREATE TABLE IF NOT EXISTS "+getTableName(cls)+"(";
		Field[] fields = cls.getDeclaredFields();
		for(int i = 0; i<fields.length ; i++){
			Field field = fields[i];
			field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if(column!= null){
				String fieldsql = column.name();
				fieldsql += getColumnType(field);
				fieldsql += getPrecision(field);	
				fieldsql += (column.nullable()) ? " NULL" : " NOT NULL" ;
				fieldsql += getDefaultValue(field);
				fieldsql += (i < fields.length-1) ? "," : "" ;
				sql += fieldsql;
			}
		}
		String primaries = getPrimaryKeys();

		if(!primaries.isEmpty()){
			sql+=","+primaries;
		}
		String foreign = getForeignKeys(fields);
		if(!foreign.isEmpty()){
			sql+=getForeignKeys(fields);	
		}
		sql+=");";
		return new SQLiteHelperResult(true,sql);
	}

	public SQLiteHelperResult getInsertQuery(){
		if(obj == null){
			return new SQLiteHelperResult(false,"","Object can not be null");
		}		

		String sql = "INSERT INTO "+getTableName(cls)+"(";
		Field[] fields = cls.getDeclaredFields();
		String values = "";
		String names = "";
		for(int i = 0; i<fields.length; i++){
			Field field = fields[i];
			field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			if(column!= null){
				names+=column.name();
				values+=getFieldValue(field,null);
				if(i < fields.length-1){
					names+=",";
					values+=",";
				}
			}
		}
		sql += names+")VALUES("+values+");";
		return new SQLiteHelperResult(true,sql);
	}

	public SQLiteHelperResult getDeleteQuery(){
		if(obj == null){
			return new SQLiteHelperResult(false,"","Object can not be null");
		}
		String value = "DELETE FROM "+getTableName(cls)+" WHERE ";
		Field[] fields = cls.getDeclaredFields();
		for(int i = 0; i<fields.length; i++){

			Field field = fields[i];
			field.setAccessible(true);
			PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
			if(primary != null){
				if(i!= 0){
					value+=" AND ";
				}
				Column column = field.getAnnotation(Column.class);
				if(column!= null){
					value+=column.name()+"="+getFieldValue(field,null);
				}
			}
		}
		return new SQLiteHelperResult(true,value);
	}

	public SQLiteHelperResult getUpdateQuery(IObject toUpdate){
		if(obj == null){
			return new SQLiteHelperResult(false,"","Object can not be null");
		}
		if(toUpdate == null){
			return new SQLiteHelperResult(false,"","To Updated Object can not be null");
		}		
		if(obj.getClass() != toUpdate.getClass()){
			return new SQLiteHelperResult(false,"","Object class and toUpdated Object class are not the same");
		}

		String value = "UPDATE "+getTableName(cls)+" SET ";
		String where = " WHERE ";
		Field[] fields = cls.getDeclaredFields();
		int wherecount = 0;
		int fieldcount = 0;
		for(int i = 0; i<fields.length;i++){
			Field field = fields[i];
			field.setAccessible(true);

			PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
			Column column = field.getAnnotation(Column.class);
			if(primary == null){
				if(column != null){
					String field_obj_value = getFieldValue(field,obj);
					String field_upd_value = getFieldValue(field,toUpdate);
					if(!field_obj_value.equals(field_upd_value)){
						if(fieldcount!= 0){
							value+=", ";
						}
						fieldcount++;
						value+=column.name()+"="+field_obj_value;
					}
				}
			}else{
				if(wherecount != 0){
					where+=" AND ";
				}
				wherecount++;
				where += column.name()+"="+getFieldValue(field,toUpdate);
			}
		}
		value += where;
		System.out.println(value);
		return new SQLiteHelperResult(true,value);
	}	

	public SQLiteHelperResult getSelectQuery(String[] fieldnames,WhereDefinition[] where,OrderByDefinition[] orderby,String groupby,int limit){
		if(cls == null){
			return new SQLiteHelperResult(false,"","Class<? extends Object> can not be null");
		}
		String sql = "SELECT ";
		if(fieldnames!=null && where.length!= 0){
			for(int i = 0 ; i<fieldnames.length;i++){
				sql+=fieldnames;
				if(i<fieldnames.length-1){
					sql+=",";
				}
			}
		}else{
			sql+="*";
		}
		sql += " FROM "+getTableName(cls);
		if(where!=null && where.length!= 0){
			sql+=" WHERE ";
			for(int i = 0; i<where.length;i++){
				WhereDefinition wheredef = where[i];
				sql+= wheredef.getClause();
				if(i<where.length-1){
					sql+= " AND ";
				}
			}
		}
		if(orderby!=null && orderby.length!=0){
			sql+=" ORDER BY ";
			for(int i = 0; i<orderby.length;i++){
				OrderByDefinition orderdef = orderby[i];
				sql+= orderdef.getClause();
				if(i<orderby.length-1){
					sql+=", ";
				}
			}
		}
		if(groupby!= null && !groupby.isEmpty()){
			sql+=" GROUP BY "+groupby;
		}

		if(limit > 0){
			sql+= " LIMIT "+limit;
		}

		return new SQLiteHelperResult(true,sql);
	}


	private String getDefaultValue(Field field){
		field.setAccessible(true);
		DefaultValue defval = field.getAnnotation(DefaultValue.class);
		String val = "";
		if(defval != null){
			if(defval.value() != null && !defval.value().isEmpty()){
				val = " DEFAULT "+defval.value();

			}
		}
		return val;
	}
	private String getPrecision(Field field) {
		field.setAccessible(true);
		Precision prec = field.getAnnotation(Precision.class); 				
		if(prec != null){
			int[] val = prec.value();
			if(val.length >= 2){
				return "("+val[0]+","+val[1]+")";
			}else if(val.length== 1){
				return "("+val[0]+")";
			}
		}
		return "";
	}
	private String getColumnType(Field field) {
		Column column = (field.getAnnotation(Column.class)!= null) ? field.getAnnotation(Column.class) : null ; 				
		DataType datatype = DataType.AUTO;
		if(column != null){
			datatype = column.type();
		}
		switch(datatype){
		case AUTO:
			return " "+getAutoType(field);
		default:
			return " "+datatype.getValue();
		}
	}
	private String getAutoType(Field field) {
		for(Class<?> cls : DataType.INTEGER.getBestFitClasses()){
			if(field.getClass().equals(cls)){
				return DataType.INTEGER.getValue();
			}
		}
		for(Class<?> cls : DataType.TEXT.getBestFitClasses()){
			if(field.getClass().equals(cls)){
				return DataType.TEXT.getValue();
			}
		}
		for(Class<?> cls : DataType.NUMERIC.getBestFitClasses()){
			if(field.getClass().equals(cls)){
				return DataType.NUMERIC.getValue();
			}
		}
		for(Class<?> cls : DataType.REAL.getBestFitClasses()){
			if(field.getClass().equals(cls)){
				return DataType.REAL.getValue();
			}
		}
		return DataType.AUTO.getValue();
	}
	private String getPrimaryKeys(){
		Field[] fields = cls.getDeclaredFields();
		String sql = "";
		int primarycount = 0;
		for(int i = 0; i< fields.length;i++){
			Field field = fields[i];
			field.setAccessible(true);
			PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
			Column column= field.getAnnotation(Column.class);
			if(column!= null && primary != null){
				if(primary.value()){
					if(primarycount!=0){
						sql+=",";
					}
					primarycount++;
					sql+=column.name();
				}
			}
		}
		if(sql!= ""){
			sql = "PRIMARY KEY("+sql+")";
		}
		return sql;
	}

	private String getForeignKeys(Field[] fields){
		ArrayList<String> relations = new ArrayList<String>();
		for(Field field : fields){
			field.setAccessible(true);
			Relation rl = field.getAnnotation(Relation.class);
			if(rl != null){
				String colname = (field.getAnnotation(Column.class) != null) ? field.getAnnotation(Column.class).name() : null;
				if(colname != null){
					String constraint = "";
					String rule = "";
					if(!rl.name().equals("")){
						constraint = "CONSTRAINT "+rl.name()+" ";
					}
					if(rl.action() != ConstraintAction.NONE && rl.rule() != ConstraintRule.NONE){
						rule = rl.action().getValue()+" "+rl.rule().getValue();
					}
					relations.add(constraint+"FOREIGN KEY("+colname+") REFERENCES "+getTableName(rl.table())+"("+rl.field()+") "+rule);
				}
			}
		}
		String sql = "";
		if(relations.size() != 0 ){
			sql+=",";
			for(int i = 0; i<relations.size(); i++){
				sql+=relations.get(i);
				if(i<relations.size()-1){
					sql+=",";
				}
			}
		}
		return sql;
	}
	private String getTableName(Class<? extends IObject> cls){
		Table tab = cls.getAnnotation(Table.class);
		if(tab != null){
			return tab.value();
		}else{
			return cls.getSimpleName();
		}
	}
	private String getFieldValue(Field field,IObject tempObj) {
		try {
			IObject obj = (tempObj == null) ? this.obj : tempObj;
			String value = "";
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
					value = field.getInt(obj)+"";
				}else if(type.equals(boolean.class) || type.equals(Boolean.class)){
					value = (field.getBoolean(obj)) ? "1":"0";
				}else if(type.equals(byte.class) || type.equals(Byte.class)){
					value = field.getByte(obj)+"";					
				}else if(type.equals(char.class)){
					value = field.getChar(obj)+"";
				}else if(type.equals(double.class) || type.equals(Double.class)){
					value = field.getDouble(obj)+"";
				}else if(type.equals(float.class) || type.equals(Float.class)){
					value = field.getFloat(obj)+"";
				}else if(type.equals(long.class) || type.equals(Long.class)){
					value = field.getLong(obj)+"";
				}else if(type.equals(short.class) || type.equals(Short.class)){
					value = field.getShort(obj)+"";
				}else if(type.equals(Date.class)){
					Date cal = (Date)field.get(obj);
					if(cal != null){
						value = cal.getTime()+"";
					}
				}else if(type.equals(Calendar.class)){
					Calendar cal = (Calendar)field.get(obj);
					if(cal != null){
						value = cal.getTime()+"";
					}
				}else{
					value = "'"+field.get(obj)+"'";
				}
			}

			if((value.isEmpty() || value.equals("''"))){
				DefaultValue def = field.getAnnotation(DefaultValue.class);
				if(field.getType().equals(String.class) || field.getType().equals(String.class)){
					value = "'"+def.value()+"'";
				}else{
					value = def.value();
				}
				if(value.isEmpty() || value.equals("''")){
					value="NULL";
				}
			}

			return " "+value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "No type found change database type or Attribute type to match field and column";
	}
}
