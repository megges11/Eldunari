package eldunari.form.classes.helper;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.Field;

import javax.swing.JTable;

import eldunari.form.enumation.Orientation;
import eldunari.form.enumation.ViewType;
import eldunari.form.interfaces.IComponent;
import eldunari.form.interfaces.IGridLayer;
import eldunari.form.interfaces.ILayer;
import eldunari.general.classes.ClassFinder;
import eldunari.general.classes.OutputHandler;
import eldunari.general.enumeration.OutputType;
import eldunari.origin.annotation.Column;
import eldunari.origin.interfaces.IObject;
import eldunari.form.annotation.DataModel;
import eldunari.form.components.Form;

public class VisualHelper {

	public static String layer_package = "de.itx3.winforms.layer";
	public static String grid_package = "de.itx3.winforms.grid";


	public static Container getLayout(Form frame,Class<?> currentClass,ViewType type,Dimension dimension) {
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
								return layer.getContainer(frame,false);
							}else{
								return layer.getContainer(frame, true);
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
								return layer.getContainer(frame.getContentPane(),type);
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

						IComponent component = getElementByTag(col_name,container);
						if(component != null){
							component.setValue(field.get(obj));							
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

	public static IComponent getElementByTag(String name, Container container){
		Component[] components = container.getComponents();
		for(Component com : components){
			if(com instanceof IComponent){
				IComponent compo = (IComponent) com;
				if(compo!= null){
					String com_name = compo.getTag();
					if(com_name != null){
						if(com_name.equalsIgnoreCase(name)){
							return compo;
						}
					}
				}
			}
		}
		return null;
	}

	public static Object getElementValue(String name, Container container){
		IComponent component = getElementByTag(name,container);
		if(component != null){
			return component.getValue();
		}
		return null;
	}

	public static Point GetPosition(Point relation, Orientation orientation){
		if(relation != null){
			if(orientation == Orientation.Right){
				return new Point(relation.x+Form.DEFAULT_ITEM_WIDTH+Form.DEFAULT_PADDING_LEFT, relation.y);
			}else if(orientation == Orientation.Left){
				return new Point(relation.x-Form.DEFAULT_ITEM_WIDTH-Form.DEFAULT_PADDING_RIGHT, relation.y);
			}else if(orientation == Orientation.Top){
				return new Point(relation.x,relation.y-Form.DEFAULT_PADDING_TOP-Form.DEFAULT_ITEM_HEIGHT);
			}else if(orientation == Orientation.Bottom){
				return new Point(relation.x,relation.y+Form.DEFAULT_PADDING_TOP+Form.DEFAULT_ITEM_HEIGHT);
			}
		}
		return Form.DEFAULT_COMPONENT_START_LOCATION;
	}

	public static Point GetPosition(IComponent com, Orientation orientation){
		if(com != null){
			Point relation = com.getLocation();
			if(relation != null){
				Dimension size = Form.DEFAULT_ITEM_SIZE();
				if(com.getSize() != Form.DEFAULT_ITEM_SIZE()){
					size = com.getSize();
				}
				if(orientation == Orientation.Right){
					return new Point(relation.x+size.width+Form.DEFAULT_PADDING_LEFT, relation.y);
				}else if(orientation == Orientation.Left){
					return new Point(relation.x-size.width-Form.DEFAULT_PADDING_RIGHT, relation.y);
				}else if(orientation == Orientation.Top){
					return new Point(relation.x,relation.y-Form.DEFAULT_PADDING_TOP-size.height);
				}else if(orientation == Orientation.Bottom){
					return new Point(relation.x,relation.y+Form.DEFAULT_PADDING_TOP+size.height);
				}
			}
		}
		return Form.DEFAULT_COMPONENT_START_LOCATION;
	}
	public static Point GetPosition(){
		return Form.DEFAULT_COMPONENT_START_LOCATION;
	}
}
