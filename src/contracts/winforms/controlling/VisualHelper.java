package contracts.winforms.controlling;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import contracts.annotation.Column;
import contracts.annotation.DataModel;
import contracts.kernel.enumeration.Orientation;
import contracts.kernel.enumeration.OutputType;
import contracts.kernel.enumeration.ViewType;
import contracts.kernel.helper.ClassFinder;
import contracts.kernel.helper.OutputHandler;
import contracts.kernel.interfaces.IGridLayer;
import contracts.kernel.interfaces.ILayer;
import contracts.kernel.interfaces.IObject;
import contracts.winforms.MainFrame;


public class VisualHelper {

	public static final String layer_package = "de.itx3.winforms.layer";
	public static final String grid_package = "de.itx3.winforms.grid";


	public static Container getLayout(MainFrame frame,Class<?> currentClass,ViewType type,Dimension dimension) {
		try{
			String searchPackage = getPackage(type);			
			if(type == ViewType.GridView || type == ViewType.GridViewSelect){
				Class<?> cls = ClassFinder.find(grid_package+"."+currentClass.getSimpleName()+"_GridView");

				if(cls != null){
					DataModel aLayer = cls.getAnnotation(DataModel.class);
					if(aLayer != null && aLayer.value().equals(currentClass)){
						IGridLayer layer = null;
						if(isInstanceOf(cls,IGridLayer.class)){
							Object obj = cls.newInstance();
							layer = (IGridLayer) obj;
						}
						if(layer != null){
							if(type == ViewType.GridViewSelect){
								return layer.getContainer(dimension,frame,false);
							}else{
								return layer.getContainer(dimension, frame, true);
							}				
						}
					}
				}
			}else{
				if(searchPackage != ""){			
					Class<?> cls = ClassFinder.find(layer_package+"."+currentClass.getSimpleName()+"_Layer");
					if(cls != null){

						DataModel aLayer = cls.getAnnotation(DataModel.class);
						if(aLayer != null && aLayer.value().equals(currentClass)){
							ILayer layer = null;
							if(isInstanceOf(cls,ILayer.class)){
								Object obj = cls.newInstance();
								layer = (ILayer) obj;
							}
							if(layer != null){
								return layer.getContainer(dimension,frame,type);
							}
						}
					}
				}
			}
		}catch(Exception ex){
			OutputHandler.Message(OutputType.Error, ex.getMessage());
		}
		return new Container();
	}

	private static boolean isInstanceOf(Class<?> proofClass, Class<?> parent){
		Class<?>[] classes = proofClass.getInterfaces();
		for(Class<?> cls : classes){
			if (cls.equals(parent)){
				return true;
			}
		}
		return false;
	}

	private static String getPackage(ViewType type){
		if(type == ViewType.GridView || type == ViewType.GridViewSelect){
			return grid_package;
		}
		return layer_package;
	}

	public static Container loadContent(IObject obj,Container container) {
		try{
			if(obj != null){
				Class<?> cls = obj.getClass();
				Field[] fields = cls.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					Column col = field.getAnnotation(Column.class);
					if(col != null){
						String col_name = col.name();
						Component component = getElementByName("["+col_name+"]",container);
						if(component != null){
							Class<?> comClass = component.getClass();

							if(comClass.equals(JTextField.class) && field.getType().equals(String.class)){
								JTextField tfCom = (JTextField)component;
								tfCom.setText(field.get(obj).toString());

							}else if(comClass.equals(JTextField.class) && field.getType().equals(int.class)){
								JTextField tfCom = (JTextField)component;
								tfCom.setText(field.getInt(obj)+"");

							}else if(comClass.equals(JTextField.class) && field.getType().equals(Calendar.class)){
								JTextField tfCom = (JTextField)component;
								Calendar cal = (Calendar)field.get(obj);					
								tfCom.setText(cal.toString());

							}else if(comClass.equals(JTextArea.class) && field.getType().equals(String.class)){
								JTextArea tfCom = (JTextArea)component;
								tfCom.setText(field.get(obj).toString());

							}
						}
					}
				}
				for(Component com : container.getComponents()){
					if(com.getClass() == JTable.class){

					}
				}
			}
		}catch(Exception ex){
			OutputHandler.Message(OutputType.Error, ex.getMessage());
		}
		return container;
	}

	public static ILayer getLayer(Class<?> currentClass,ViewType type){
		try{
			String searchPackage = getPackage(type);			
			if(searchPackage != ""){			
				Class<?> cls = ClassFinder.find(layer_package+"."+currentClass.getSimpleName()+"_Layer");
				if(cls != null){
					DataModel aLayer = cls.getAnnotation(DataModel.class);
					if(aLayer != null && aLayer.value().equals(currentClass)){
						if(isInstanceOf(cls,ILayer.class)){
							Object obj = cls.newInstance();
							return (ILayer) obj;
						}			
					}
				}
			}
		}catch(Exception ex){
			OutputHandler.Message(OutputType.Error,ex.getMessage());
		}
		return null;
	}

	public static Component getElementByName(String name, Container container){
		Component[] components = container.getComponents();
		for(Component com : components){
			if(com!= null){
				String com_name = com.getName();
				if(com_name != null){
					if(com_name.equalsIgnoreCase(name)){
						return com;
					}
				}
			}
		}
		return null;
	}

	public static Object getElementValue(String name, Container container){
		Component component = getElementByName(name,container);
		if(component != null){
			if(component.getClass().equals(JTextField.class)){
				JTextField tfCom = (JTextField)component;
				return tfCom.getText();	
			}else if(component.getClass().equals(JTextArea.class)){
				JTextArea tfCom = (JTextArea)component;
				return tfCom.getText();			
			}
		}
		return null;
	}

	public static Point GetPosition(Point relation, Orientation orientation){
		if(relation != null){
			if(orientation == Orientation.Right){
				return new Point(relation.x+MainFrame.DEFAULT_ITEM_WIDTH+MainFrame.DEFAULT_PADDING_LEFT, relation.y);
			}else if(orientation == Orientation.Left){
				return new Point(relation.x-MainFrame.DEFAULT_ITEM_WIDTH-MainFrame.DEFAULT_PADDING_RIGHT, relation.y);
			}else if(orientation == Orientation.Top){
				return new Point(relation.x,relation.y-MainFrame.DEFAULT_PADDING_TOP-MainFrame.DEFAULT_ITEM_HEIGHT);
			}else if(orientation == Orientation.Bottom){
				return new Point(relation.x,relation.y+MainFrame.DEFAULT_PADDING_TOP+MainFrame.DEFAULT_ITEM_HEIGHT);
			}
		}
		return MainFrame.DEFAULT_COMPONENT_START_LOCATION;
	}

	public static Point GetPosition(Component com, Orientation orientation){
		if(com != null){
			Point relation = com.getLocation();
			if(relation != null){
				Dimension size = MainFrame.DEFAULT_ITEM_SIZE();
				if(com.getSize() != MainFrame.DEFAULT_ITEM_SIZE()){
					size = com.getSize();
				}
				if(orientation == Orientation.Right){
					return new Point(relation.x+size.width+MainFrame.DEFAULT_PADDING_LEFT, relation.y);
				}else if(orientation == Orientation.Left){
					return new Point(relation.x-size.width-MainFrame.DEFAULT_PADDING_RIGHT, relation.y);
				}else if(orientation == Orientation.Top){
					return new Point(relation.x,relation.y-MainFrame.DEFAULT_PADDING_TOP-size.height);
				}else if(orientation == Orientation.Bottom){
					return new Point(relation.x,relation.y+MainFrame.DEFAULT_PADDING_TOP+size.height);
				}
			}
		}
		return MainFrame.DEFAULT_COMPONENT_START_LOCATION;
	}
	public static Point GetPosition(){
		return MainFrame.DEFAULT_COMPONENT_START_LOCATION;
	}
}
