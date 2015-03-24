package contracts.kernel.data;

import java.awt.Container;
import java.lang.reflect.Field;

import javax.swing.JOptionPane;

import contracts.annotation.Column;
import contracts.kernel.enumeration.OutputType;
import contracts.kernel.helper.OutputHandler;
import contracts.kernel.interfaces.IObject;
import contracts.winforms.MainFrame;
import contracts.winforms.controlling.VisualHelper;

public class InfoHelper {

	public static boolean save(String dbname,Class<? extends IObject> cls,IObject currentObject, Container container){
		IObject obj = getObject(cls,container);
		if(obj != null){
//			Connector con = new Connector();
//			con.UpdateOrInsert(dbname,obj,currentObject);
//			if(con.hasError()){
//				OutputHandler.Message(OutputType.Error, "Fehler beim schreiben in die Datenbank "+con.getError(";"));
//				return false;
//			}else{
//				OutputHandler.Message(OutputType.Info, "Speichern erfolgreich");
//				return true;
//			}
		}return false;
	}

	public static boolean delete(String dbname,Class<? extends IObject> currentClass, Container contentPane,MainFrame frame) {

		int result = JOptionPane.showConfirmDialog(frame, "Wirklich Löschen?", 
			       "Löschen", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION){
			IObject obj = getObject(currentClass,contentPane);

			Connector con = new Connector();
			con.Delete(dbname,obj);
			if(con.hasError()){
				OutputHandler.Message(OutputType.Error, "Fehler beim Löschen "+con.getError(";"));
				return false;
			}else{
				OutputHandler.Message(OutputType.Info, "Löschen erfolgreich");
				return true;
			}
		}
		return false;
	}

	public static IObject getObject(Class<? extends IObject> cls, Container container){
		try{
			Field[] fields = cls.getDeclaredFields();
			IObject obj = cls.newInstance();
			for(Field field : fields){
				field.setAccessible(true);
				Column col = field.getAnnotation(Column.class);
				if(col!= null){
					Object value = VisualHelper.getElementValue("["+col.name()+"]", container);
					if(value != null){
						
						field = Connector.setFieldValue(field, obj, value);
					}
				}
			}
			return obj;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
}
