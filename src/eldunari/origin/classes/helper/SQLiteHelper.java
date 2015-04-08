package eldunari.origin.classes.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import eldunari.general.annotation.Definition;
import eldunari.origin.annotation.Column;
import eldunari.origin.annotation.DefaultValue;
import eldunari.origin.annotation.Format;
import eldunari.origin.annotation.Precision;
import eldunari.origin.annotation.PrimaryKey;
import eldunari.origin.annotation.Relation;
import eldunari.origin.annotation.Required;
import eldunari.origin.annotation.Table;
import eldunari.origin.classes.Connector;
import eldunari.origin.enumeration.ConstraintAction;
import eldunari.origin.enumeration.ConstraintRule;
import eldunari.origin.enumeration.DataType;
import eldunari.origin.interfaces.IObject;
import eldunari.origin.interfaces.IView;

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
			return new SQLiteHelperResult(false,"","Class<? extends IObject> can not be null");
		}
		String sql = "CREATE TABLE IF NOT EXISTS "+getTableName(cls)+"(";
		Field[] fields = cls.getDeclaredFields();

		HashMap<String,PrimaryKey> primaryKeyCols = new HashMap<String,PrimaryKey>();
		HashMap<Column,ArrayList<Relation>> relationCols = new HashMap<Column,ArrayList<Relation>>();

		for(int i = 0; i<fields.length ; i++){			
			Field field = fields[i];
			field.setAccessible(true);
			ColumnDefinition definition = getDefinition(cls,field);
			if(definition.hasErrors()){
				return new SQLiteHelperResult(false,"",definition.getError());
			}
			if(definition.getColumn()!= null){
				String fieldsql = definition.getColumn().name();
				fieldsql += getColumnType(definition.getColumn(), field);
				fieldsql += getPrecision(definition.getPrecision());
				fieldsql += getNullable(definition.getRequired());
				//fieldsql += (definition.getColumn().nullable()) ? " NULL" : " NOT NULL" ;
				fieldsql += getDefaultValue(definition.getDefaultValue());			
				fieldsql += (i < fields.length-1) ? "," : "" ;
				sql += fieldsql;
				if(definition.getPrimaryKey()!= null){
					//System.err.println(cls.getSimpleName()+" "+definition.getColumn().name());
					primaryKeyCols.put(definition.getColumn().name(),definition.getPrimaryKey());
				}
				if(!definition.getRelations().isEmpty()){
					relationCols.put(definition.getColumn(), definition.getRelations());
				}
			}
		}
		String primaries = getPrimaryKeys(primaryKeyCols);

		if(!primaries.isEmpty()){
			sql+=","+primaries;
		}
		String foreign = getForeignKeys(relationCols);
		if(!foreign.isEmpty()){
			sql+=foreign;	
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
			ColumnDefinition definition = getDefinition(cls,field);						
			if(definition.getColumn() != null){
				Column column = definition.getColumn();
				if(column!= null){
					names+=column.name();
					values+=getFieldValue(column,field,null);
					if(i < fields.length-1){
						names+=",";
						values+=",";
					}
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

			ColumnDefinition definition = getDefinition(cls,field);
			if(definition.getColumn() != null){
				PrimaryKey primary = definition.getPrimaryKey();
				if(primary != null){
					if(i!= 0){
						value+=" AND ";
					}
					Column column = definition.getColumn();
					if(column!= null){
						value+=column.name()+"="+getFieldValue(column,field,null);
					}
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

			ColumnDefinition definition = getDefinition(cls,field);

			PrimaryKey primary = definition.getPrimaryKey();
			Column column = definition.getColumn();
			if(primary != null){
				if(column != null){
					if(wherecount != 0){
						where+=" AND ";
					}
					wherecount++;
					where += column.name()+"="+getFieldValue(column,field,toUpdate);
				}
				
			}else{
				if(column != null){
					String field_obj_value = getFieldValue(column,field,obj);
					String field_upd_value = getFieldValue(column,field,toUpdate);
					if(!field_obj_value.equals(field_upd_value)){
						if(fieldcount!= 0){
							value+=", ";
						}
						fieldcount++;
						value+=column.name()+"="+field_obj_value;
					}
				}
			}
		}
		if(fieldcount != 0){
			value += where;	
		}else{
			value="";
		}
		return new SQLiteHelperResult(true,value);
	}	

	public SQLiteHelperResult getSelectViewQuery(WhereDefinition[] where){
		try{
			if(Connector.implementsInterface(cls,IView.class)){			
				String sql="";
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
				IView view = (IView)cls.newInstance();
				sql = view.getSelection(sql);
				return new SQLiteHelperResult(true,sql,"");
			}		
			return new SQLiteHelperResult(false,"","Class<?> is not from Type IView");
		}catch(Exception ex){
			return new SQLiteHelperResult(false,"",ex.getMessage());
		}
	}

	public SQLiteHelperResult getSelectQuery(String[] fieldnames,WhereDefinition[] where,OrderByDefinition[] orderby,String groupby,int limit){
		if(cls == null){
			return new SQLiteHelperResult(false,"","Class<? extends Object> can not be null");
		}
		String sql = "SELECT ";
		if(fieldnames!=null && fieldnames.length!= 0){
			for(int i = 0 ; i<fieldnames.length;i++){
				sql+=fieldnames[i];
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


	private String getDefaultValue(DefaultValue defval){
		String val = "";
		if(defval != null){
			if(defval.value() != null && !defval.value().isEmpty()){
				val = " DEFAULT "+defval.value();

			}
		}
		return val;
	}
	private String getPrecision(Precision prec) {		
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
	private String getNullable(Required required) {		
		if(required != null){
			if(required.value()){
				return " NOT NULL ";
			}else{
				return " NULL ";
			}
		}
		return "";
	}
	private String getColumnType(Column column,Field field) { 				
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
	private String getPrimaryKeys(HashMap<String,PrimaryKey> primaryKeyCols){
		String sql = "";
		int primarycount = 0;
		for(String key : primaryKeyCols.keySet()){
			PrimaryKey primary = primaryKeyCols.get(key);
			if(key!= null && primary != null){
				if(primary.value()){
					if(primarycount!=0){
						sql+=",";
					}
					primarycount++;
					sql+=key;
				}
			}
		}
		if(sql!= ""){
			sql = "PRIMARY KEY("+sql+")";
		}
		return sql;
	}

	private String getForeignKeys(HashMap<Column,ArrayList<Relation>> relationCols){
		ArrayList<String> relations = new ArrayList<String>();

		for(Column column : relationCols.keySet()){
			for(Relation rl : relationCols.get(column)){
				if(rl != null){
					String colname = column.name();
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
	private String getFieldValue(Column column, Field field,IObject tempObj) {
		try {
			IObject obj = (tempObj == null) ? this.obj : tempObj;
			String value = "";
			//			Column column = field.getAnnotation(Column.class);
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
					Object val = field.get(obj);
					if(val != null){
						value = "'"+val+"'";
					}

				}
			}

			if((value.isEmpty() || value.equals("''"))){
				DefaultValue def = field.getAnnotation(DefaultValue.class);
				if(def != null){
					if(field.getType().equals(String.class) || field.getType().equals(String.class)){
						value = "'"+def.value()+"'";
					}else{
						value = def.value();
					}
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

	public static ColumnDefinition getDefinition(Class<? extends IObject> cls, Field field){
		ColumnDefinition definition = new ColumnDefinition();
		field.setAccessible(true);

		Definition define = cls.getAnnotation(Definition.class);
		if(define != null){
			for(Class<?> clazz : define.value()){

				Field[] fields = clazz.getDeclaredFields();
				for(Field f : fields){
					if(f.getName().equals(field.getName())){
						Annotation[] annotations = f.getDeclaredAnnotations();
						for(Annotation anno : annotations){
							if(anno.annotationType().equals(Column.class)){
								definition.setColumn((Column)anno);
							}else if(anno.annotationType().equals(PrimaryKey.class)){
								definition.setPrimaryKey((PrimaryKey)anno);
							}else if(anno.annotationType().equals(DefaultValue.class)){
								definition.setDefaultValue((DefaultValue)anno);
							}else if(anno.annotationType().equals(Format.class)){
								definition.setFormat((Format)anno);
							}else if(anno.annotationType().equals(Precision.class)){
								definition.setPrecision((Precision)anno);
							}else if(anno.annotationType().equals(Relation.class)){
								definition.addRelation((Relation)anno);
							}else if(anno.annotationType().equals(Required.class)){
								definition.setRequired((Required)anno);
							}
						}
					}
				}
			}
		}

		Annotation[] annotations = field.getAnnotations();
		for(Annotation anno : annotations){
			if(anno.annotationType().equals(Column.class)){
				definition.setColumn((Column)anno);
			}else if(anno.annotationType().equals(PrimaryKey.class)){
				definition.setPrimaryKey((PrimaryKey)anno);
			}else if(anno.annotationType().equals(DefaultValue.class)){
				definition.setDefaultValue((DefaultValue)anno);
			}else if(anno.annotationType().equals(Format.class)){
				definition.setFormat((Format)anno);
			}else if(anno.annotationType().equals(Precision.class)){
				definition.setPrecision((Precision)anno);
			}else if(anno.annotationType().equals(Relation.class)){
				definition.addRelation((Relation)anno);
			}else if(anno.annotationType().equals(Required.class)){
				definition.setRequired((Required)anno);
			}
		}

		return definition;
	}

}
