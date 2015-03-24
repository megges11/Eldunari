package contracts.winforms.controlling;

import java.lang.reflect.Field;
import java.util.ArrayList;

import contracts.kernel.enumeration.OutputType;
import contracts.kernel.helper.OutputHandler;

public class GridHelper {

	private Class<?> currentClass;
	private String[] columns;
	private String[] fieldNames;

	private ArrayList<?> mItems;

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
}
