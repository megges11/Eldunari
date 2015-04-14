package eldunari.form.classes.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import eldunari.general.classes.MethodFinder;

public class PopupHelper {

	private ArrayList<JMenuItem> menuitems = new ArrayList<JMenuItem>();
	
	/**
	 * Insert a JMenuItem to the ArrayList
	 * @param menuName --> name of the parent Menu Item
	 * @param thisname --> set the name of this MenuItem
	 * @param displayValue --> set the Text that displayed
	 */
	public void AddMenuItem(String thisname, String displayValue){
		JMenuItem mi = new JMenuItem();
		mi.setName(thisname);
		mi.setText(displayValue);
		addToMenuItem(mi);
	}
	
	private void addToMenuItem(JMenuItem mi) {
		menuitems.add(mi);
	}

	/**
	 * Add a JMenuItem to the ArrayList and set an ActionListener with methodname and a Class
	 * @param menuName --> the parent name
	 * @param thisname --> the name of this menuitem
	 * @param displayValue --> the text that displayed
	 * @param methodname --> the name of the Method that would be called from the ActionListener
	 * @param targetClass --> the Class that contains the method with the methodname
	 */
	public void AddMenuItem(String thisname, String displayValue,final String methodname,final Class<?> targetClass){
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
		addToMenuItem(mi);
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
	public void AddMenuItem(String thisname, String displayValue,final String methodname,final Class<?> targetClass, final Class<?>[]paramTypes, final Object[]params ){
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
		addToMenuItem(mi);
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
	public void AddMenuItem(String thisname, String displayValue,final String methodname,final Object targetObject, final Class<?>[]paramTypes, final Object[]params ){
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
		addToMenuItem(mi);
	}
	
	/**
	 * Add a JMenuItem to the ArrayList and set an ActionListener with methodname and a Class
	 * @param menuName --> the parent name
	 * @param thisname --> the name of this menuitem
	 * @param displayValue --> the text that displayed
	 * @param methodname --> the name of the Method that would be called from the ActionListener
	 * @param targetObject --> the object that call the method
	 */
	public void AddMenuItem(String thisname, String displayValue,final String methodname,final Object targetObject){
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
		addToMenuItem(mi);
	}
	
	public JPopupMenu getPopupMenu(){
		JPopupMenu popup = new JPopupMenu();
		for(JMenuItem jmen:menuitems){
			popup.add(jmen);
		}
		return popup;
	}
}
