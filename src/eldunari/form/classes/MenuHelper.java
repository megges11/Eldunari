package eldunari.form.classes;
import eldunari.general.classes.MethodFinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The MenuHelper class is a simple class, that contains one Arraylist of JMenu and one ArrayList of MenuItem<br>
 * to manage the JMenuBar
 * 
 * @author Marcus Priwall
 * @version 1.0
 */
public class MenuHelper {

	private JMenuBar menuBar;
	private ArrayList<JMenu> menu;
	private ArrayList<MenuItem> menuitems;

	public MenuHelper() {
		menuBar = new JMenuBar(); //JMenuBar get initialized
		menu = new ArrayList<JMenu>(); //the ArrayLists get initialized
		menuitems = new ArrayList<MenuItem>();
	}
	
	/**
	 * Create a new JMenu and save it into an ArrayList
	 * @param name -->the name of the JMenu
	 * @param displayValue -->the text of the JMenu that will be displayed
	 */
	public void AddMenu(String name,String displayValue){
		//Create a new JMenu
		JMenu m = new JMenu();
		m.setName(name);
		m.setText(displayValue);
		//Add the JMenu to the ArrayList
		addToMenu(m);
	}
	
	/**
	 * Create a new SubMenu and save it into an ArrayList
	 * @param menuName -->the name of the Parent JMenu
	 * @param name -->the name of this JMenu
	 * @param displayValue -->the text of the JMenu that will be displayed
	 */
	public void AddSubMenu(String menuName, String name, String displayValue) {
		JMenu m = new JMenu();
		m.setName(name);
		m.setText(displayValue);
		addToSubMenu(m, menuName);
	}

	/**
	 * Insert a JMenu to the ArrayList
	 * @param m -->the JMenu that would be inserted
	 */
	private void addToMenu(JMenu m){
		menu.add(m);
	}

	/**
	 * Insert a JMenuItem to the ArrayList
	 * @param m -->the JMenuItem
	 * @param jmenuName -->parent name
	 */
	private void addToMenuItem(JMenuItem m,String jmenuName){
		menuitems.add(new MenuItem(m,jmenuName));
	}
	
	/**
	 * Insert a JMenuItem to the ArrayList
	 * @param m -->the JMenuItem
	 * @param jmenuName -->parent name
	 */
	private void addToSubMenu(JMenu m, String jmenuName) {
		menuitems.add(new MenuItem(m, jmenuName));
	}

	/**
	 * Insert a JMenuItem to the ArrayList
	 * @param menuName --> name of the parent Menu Item
	 * @param thisname --> set the name of this MenuItem
	 * @param displayValue --> set the Text that displayed
	 */
	public void AddMenuItem(String menuName, String thisname, String displayValue){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		addToMenuItem(mi,menuName);
	}
	
	/**
	 * Add a JMenuItem to the ArrayList and set an ActionListener with methodname and a Class
	 * @param menuName --> the parent name
	 * @param thisname --> the name of this menuitem
	 * @param displayValue --> the text that displayed
	 * @param methodname --> the name of the Method that would be called from the ActionListener
	 * @param targetClass --> the Class that contains the method with the methodname
	 */
	public void AddMenuItem(String menuName, String thisname, String displayValue,final String methodname,final Class<?> targetClass){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					MethodFinder.invokeMethod(methodname, targetClass);
				}catch(Exception ex){
					System.err.println(ex.getMessage());
				}
			}	
		});
		addToMenuItem(mi,menuName);
	}
	
	/**
	 * Add a JMenuItem to the ArrayList and set an ActionListener with methodname Class and Parameters 
	 * @param menuName --> the parent name
	 * @param thisname --> the name of this menuitem
	 * @param displayValue --> the text that displayed
	 * @param methodname --> the name of the Method that would be called from the ActionListener
	 * @param targetClass --> the Class that contains the method with the methodname
	 * @param paramTypes --> the Class of the Parameter values as an Array in the same order like the params in the method and the params
	 * @param params --> the values that will be overgived to the method
	 */
	public void AddMenuItem(String menuName, String thisname, String displayValue,final String methodname,final Class<?> targetClass, final Class<?>[]paramTypes, final Object[]params ){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					MethodFinder.invokeMethod(methodname, targetClass, paramTypes, params);
				}catch(Exception ex){
					System.err.println(ex.getMessage());
				}
			}	
		});
		addToMenuItem(mi,menuName);
	}
	
	/**
	 * Add a JMenuItem to the ArrayList and set an ActionListener with methodname Class and Parameters 
	 * @param menuName --> the parent name
	 * @param thisname --> the name of this menuitem
	 * @param displayValue --> the text that displayed
	 * @param methodname --> the name of the Method that would be called from the ActionListener
	 * @param targetObject --> the object that call the method
	 * @param paramTypes --> the Class of the Parameter values as an Array in the same order like the params in the method and the params
	 * @param params --> the values that will be overgived to the method
	 */
	public void AddMenuItem(String menuName, String thisname, String displayValue,final String methodname,final Object targetObject, final Class<?>[]paramTypes, final Object[]params ){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					MethodFinder.invokeMethod(methodname, targetObject, paramTypes, params);
				}catch(Exception ex){
					System.err.println(ex.getMessage());
				}
			}	
		});
		addToMenuItem(mi,menuName);
	}
	
	/**
	 * Add a JMenuItem to the ArrayList and set an ActionListener with methodname and a Class
	 * @param menuName --> the parent name
	 * @param thisname --> the name of this menuitem
	 * @param displayValue --> the text that displayed
	 * @param methodname --> the name of the Method that would be called from the ActionListener
	 * @param targetObject --> the object that call the method
	 */
	public void AddMenuItem(String menuName, String thisname, String displayValue,final String methodname,final Object targetObject){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		mi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					MethodFinder.invokeMethod(methodname, targetObject);
				}catch(Exception ex){
					System.err.println(ex.getMessage());
				}
			}	
		});
		addToMenuItem(mi,menuName);
	}

	/**
	 * Initialize the JMenuBar and set the name
	 * @param name --> Name of the JMenuBar
	 */
	public void createMenu(String name){
		menuBar = new JMenuBar();
		menuBar.setName(name);
	}
	
	/**
	 * Returns the generated JMenuBar from the ArrayLists
	 * @return JMenuBar
	 */
	public JMenuBar getMenuBar() {
		for(JMenu jmen:menu){
			populateMenu(jmen);
			menuBar.add(jmen);
		}
		return menuBar;
	}
	
	/**
	 * Add the SubMenuItems to the JMenuItem
	 * @param jmen 
	 */
	public void populateMenu(JMenuItem jmen) {
		for (MenuItem menuitem : menuitems) {
			// Only add menuitems where the parent is the actually JMenuItem
			if (menuitem.getParent().equals(jmen.getName())) {
				// insert the SubMenus
				if (menuitem.getItem() instanceof JMenu) {
					populateMenu(menuitem.getItem());
				}
				jmen.add(menuitem.getItem());
			}
		}
	}
}
