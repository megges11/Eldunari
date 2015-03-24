package contracts.kernel.enumeration;

import java.util.Calendar;
import java.util.Date;

public enum DataType {
	
	TEXT("TEXT",String.class,char.class), 
	INTEGER("INTEGER",int.class,Integer.class,boolean.class,byte.class,long.class,Calendar.class,Date.class),
	NUMERIC("NUMERIC",double.class,float.class),
	REAL("REAL",double.class,float.class),
	AUTO("AUTO",new Class<?>[]{});
	
	DataType(String value,Class<?>... cls){
		this.value = value;
		this.cls = cls;
	}	

	private final String value;
	private final Class<?>[] cls;
	
	public String getValue(){
		return value;
	}
	public Class<?>[] getBestFitClasses(){
		return cls;
	}
	
}
