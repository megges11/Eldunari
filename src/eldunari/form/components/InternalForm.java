package eldunari.form.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import eldunari.form.interfaces.IComponent;
import eldunari.form.interfaces.IForm;
import eldunari.form.interfaces.IResizeListener;

import javax.swing.JInternalFrame;

import eldunari.form.classes.helper.VisualHelper;
import eldunari.form.enumation.ViewType;
import eldunari.origin.interfaces.IObject;

public abstract class InternalForm extends JInternalFrame implements IResizeListener,IForm{
	private static final long serialVersionUID = 8173638485155786959L;


	public static final Dimension DEFAULT_SIZE = new Dimension(700,500);
	public String TITLE = "Eldunari Form";
	public static Color DEFAULT_BACKGROUND = Color.LIGHT_GRAY;
	public static int TOOLBAR_HEIGHT = 25;
	public static int DEFAULT_PADDING_LEFT = 5;
	public static int DEFAULT_PADDING_TOP = 5;
	public static int DEFAULT_PADDING_BOTTOM = 5;
	public static int DEFAULT_PADDING_RIGHT=5;

	public static Point DEFAULT_COMPONENT_START_LOCATION = new Point(10,30);
	public static int DEFAULT_ITEM_HEIGHT = 25;
	public static int DEFAULT_ITEM_WIDTH = 150;

	public Class<? extends IObject> currentClass;
	public IObject currentObject;
	public ViewType currentViewType;
	public ToolBar toolbar;
	
	public static Dimension DEFAULT_ITEM_SIZE(){
		return new Dimension(DEFAULT_ITEM_WIDTH,DEFAULT_ITEM_HEIGHT);
	}
	
	public InternalForm(Class<? extends IObject> currentClass){
		this.setTitle(TITLE);
		this.currentClass = currentClass;			
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				onResize();
			}
		});
	}

	public abstract void init_menubar();
	public void setContainer(Class<? extends IObject> cls, ViewType type){
		currentClass = cls;
		currentViewType =type;
		currentObject = null;
	}
	public void setContainer(Class<? extends IObject> cls, ViewType type,IObject obj){
		currentClass = cls;
		currentViewType =type;
		currentObject = obj;
	}

	public void onResize(){
		Component[] components = this.getContentPane().getComponents();
		for(Component component : components){
			if(component instanceof IComponent){
				IComponent com = (IComponent)component;
				int width = (this.getContentPane().getWidth()*(com.getPercentWidth()))/100;
				int height = (this.getContentPane().getHeight()*(com.getPercentHeight()))/100;

				if(com.getMinWidth() != 0){
					if(width < com.getMinWidth()){
						width = com.getMinWidth();
					}
				}
				if(com.getMaxWidth() != 0){
					if(width > com.getMaxWidth()){
						width = com.getMaxWidth();
					}
				}
				if(com.getMinHeight() != 0){
					if(height < com.getMinHeight()){
						height = com.getMinHeight();
					}
				}
				if(com.getMaxHeight() != 0){
					if(height > com.getMaxHeight()){
						height = com.getMaxHeight();
					}	
				}
				component.setSize(width,height);
				if(com.getNeighbor() != null){
					if(!com.isLockedX() && !com.isLockedY()){
						com.setLocation(com.getNeighbor(), com.getOrientation());
					}else if(com.isLockedX()){
						Point location = VisualHelper.GetPosition(com.getNeighbor(), com.getOrientation());
						component.setLocation(component.getLocation().x,location.y);
					}else if(com.isLockedY()){
						Point location = VisualHelper.GetPosition(com.getNeighbor(), com.getOrientation());
						component.setLocation(location.x,component.getLocation().y);
					}
				}				
			}
		}
	}
	
	public void clear(){
		Container container = this.getContentPane();
		for(Component com : container.getComponents()){
			container.remove(com);
		}
		this.setContentPane(container);
	}
	
	//	public void init_toolbar(){}
	//	public abstract void init_toolbar(ViewType type);
	//	public void init_toolbar(ToolBar toolbar){
	//		this.add(toolbar);
	//	}
	public void reload(){
		this.revalidate();
		this.repaint();
	}
}
