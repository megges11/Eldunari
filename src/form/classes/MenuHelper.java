package form.classes;
import general.classes.MethodFinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuHelper {

	private JMenuBar menuBar;
	private ArrayList<JMenu> menu;
	private ArrayList<MenuItem> menuitems;

	public MenuHelper() {
		menuBar = new JMenuBar();
		menu = new ArrayList<JMenu>();
		menuitems = new ArrayList<MenuItem>();
	}
	
	public void AddMenu(String name,String displayValue){
		JMenu m = new JMenu();
		m.setName(name);
		m.setText(displayValue);
		addToMenu(m);
	}
	
	public void AddSubMenu(String menuName, String name, String displayValue) {
		JMenu m = new JMenu();
		m.setName(name);
		m.setText(displayValue);
		addToSubMenu(m, menuName);
	}

	private void addToMenu(JMenu m){
		menu.add(m);
	}
	
	private void addToMenuItem(JMenuItem m,String jmenuName){
		menuitems.add(new MenuItem(m,jmenuName));
	}
	
	private void addToSubMenu(JMenu m, String jmenuName) {
		menuitems.add(new MenuItem(m, jmenuName));
	}

	public void AddMenuItem(String menuName, String thisname, String displayValue){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		addToMenuItem(mi,menuName);
	}
	
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


	public void createMenu(String name){
		menuBar = new JMenuBar();
	}
	
	public JMenuBar getMenuBar() {
		for(JMenu jmen:menu){
			populateMenu(jmen);
			menuBar.add(jmen);
		}
		return menuBar;
	}
	
	public void populateMenu(JMenuItem jmen) {
		for (MenuItem menuitem : menuitems) {
			// Nur Items hinzufügen, die zum Parent passen.
			if (menuitem.getParent().equals(jmen.getName())) {
				// JMenus können als Submenu dienen. Deshalb werden Menus rekursiv befüllt.
				if (menuitem.getItem() instanceof JMenu) {
					populateMenu(menuitem.getItem());
				}
				jmen.add(menuitem.getItem());
			}
		}
	}
}
