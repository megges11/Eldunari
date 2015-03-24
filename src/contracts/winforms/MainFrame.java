package contracts.winforms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import contracts.kernel.enumeration.ViewType;
import contracts.kernel.interfaces.IObject;

/**
 * Main Frame that is a Container of the Layer items
 * @author Marcus Priwall
 * @version 1.1
 */
public abstract class MainFrame extends JFrame {
	private static final long serialVersionUID = -2003026299016840081L;
	public static final Dimension DEFAULT_SIZE = new Dimension(700,500);
	public static String TITLE = "Reiseverwaltung v1.1";
	public static Color DEFAULT_BACKGROUND = Color.LIGHT_GRAY;
	public static int TOOLBAR_HEIGHT = 25;
	public static int DEFAULT_PADDING_LEFT = 5;
	public static int DEFAULT_PADDING_TOP = 5;
	public static int DEFAULT_PADDING_BOTTOM = 5;
	public static int DEFAULT_PADDING_RIGHT=5;
	
	public static Point DEFAULT_COMPONENT_START_LOCATION = new Point(10,30);
	public static int DEFAULT_ITEM_HEIGHT = 25;
	public static int DEFAULT_ITEM_WIDTH = 150;
	
	public Class<?> currentClass;
	public IObject currentObject;
	public ViewType currentViewType;
	
	public static Dimension DEFAULT_ITEM_SIZE(){
		return new Dimension(DEFAULT_ITEM_WIDTH,DEFAULT_ITEM_HEIGHT);
	}
	
	public MainFrame(Class<?> currentClass){
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_SIZE);
		this.setLocationRelativeTo(null);
		this.setContainer(currentClass,ViewType.GridView);
		this.setBackground(DEFAULT_BACKGROUND);
		this.setResizable(false);
		this.init_menubar();
		this.reload();
		this.setVisible(true);
	}

	public abstract void init_menubar();
	public void setContainer(Class<?> cls, ViewType type){
		currentClass = cls;
		currentViewType =type;
	}
	public void setContainer(Class<?> cls, ViewType type,IObject obj){
		currentClass = cls;
		currentViewType =type;
		currentObject = obj;
	}
	
	public abstract void init_toolbar(ViewType type);

	private void reload(){
		this.revalidate();
		this.repaint();
	}

}
