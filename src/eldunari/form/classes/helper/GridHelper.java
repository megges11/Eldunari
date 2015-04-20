package eldunari.form.classes.helper;

import eldunari.form.annotation.Caption;
import eldunari.general.annotation.Definition;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class GridHelper {

	private Class<?> currentClass;
	private String[] columns;
	private String[] fieldNames;

	private ArrayList<?> mItems;

	public GridHelper(Class<?> cls, ArrayList<?> mItems,String[] fieldNames){
		this.currentClass = cls;
		this.mItems = mItems;		
		this.fieldNames = fieldNames;
		setCaptions();
	}

	public GridHelper(Class<?> cls, String[] fieldNames){
		currentClass = cls;		
		this.fieldNames = fieldNames;
		setCaptions();
	}

	public GridHelper(Class<?> cls, ArrayList<?> mItems,String[] columns,String[] fieldNames){
		this.currentClass = cls;
		this.mItems = mItems;
		this.columns = columns;
		this.fieldNames = fieldNames;
	}

	public GridHelper(Class<?> cls, String[] columns, String[] fieldNames){
		currentClass = cls;
		this.columns = columns;
		this.fieldNames = fieldNames;
	}
	public Object[] getColumns(){
		return columns;		
	}
	public Object[][] getRows(){
		if(mItems == null){
			OutputHandler.Message(OutputType.Error, "Fehler beim laden der Datensätze");
			return null;
		}
		try{
			if(mItems.size()!=0){
				Object[][] rows = new Object[mItems.size()][columns.length];
				for(int i = 0;i<mItems.size();i++){
					for(int j = 0; j<fieldNames.length;j++){					
						Field field = currentClass.getDeclaredField(fieldNames[j]);
						if(field!= null){
							field.setAccessible(true);
							Class<?> fieldType = field.getType();
							if(fieldType.equals(Boolean.class)){
								rows[i][j] = field.getBoolean(mItems.get(i));
							}else if(fieldType.equals(int.class)){
								rows[i][j] = field.getInt(mItems.get(i));
							}else if(fieldType.equals(Date.class)){
								Date val = (Date) field.get(mItems.get(i));							
								rows[i][j] = VisualHelper.formatDate(val, currentClass, field);								
							}else{
								rows[i][j] = field.get(mItems.get(i));
							}
						}
					}
				}
				return rows;
			}
		}catch(Exception ex){
			OutputHandler.Message(OutputType.Error, ex.getMessage());
		}
		return null;
	}

	private void setCaptions(){
		columns = new String[fieldNames.length];
		for(int i = 0 ; i < fieldNames.length ; i++){		
			for(Field field : currentClass.getDeclaredFields()){
				if(field.getName().equals(fieldNames[i])){
					Caption cap = field.getAnnotation(Caption.class);
					if(cap != null){
						columns[i] = cap.value();
					}else{
						Definition definition = currentClass.getAnnotation(Definition.class);
						for(Class<?> cls : definition.value()){
							for(Field f : cls.getDeclaredFields()){
								if(f.getName().equals(field.getName()) && columns[i] == null){
									Caption caption = f.getAnnotation(Caption.class);
									if(caption != null){
										columns[i] = caption.value();
									}else{
										columns[i] = field.getName();
									}
								}
							}
						}
					}
				}
			}
		}
	}	
}
