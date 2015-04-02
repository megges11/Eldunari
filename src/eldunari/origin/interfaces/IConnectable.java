package eldunari.origin.interfaces;

import java.sql.Connection;
import java.util.ArrayList;

import eldunari.origin.classes.helper.OrderByDefinition;
import eldunari.origin.classes.helper.QueryResult;
import eldunari.origin.classes.helper.WhereDefinition;

public interface IConnectable {

	String getError();
	boolean hasError();
	
	Connection getConnection();
	QueryResult executeQuery(String sql);
	int executeUpdate(String sql);
	
	<T extends IObject> ArrayList<T> Select(Class<T> cls);	
	<T extends IObject> ArrayList<T> Select(Class<T> cls, String[] fieldnames);	
	<T extends IObject> ArrayList<T> Select(Class<T> cls, String[] fieldnames, WhereDefinition[] where);	
	<T extends IObject> ArrayList<T> Select(Class<T> cls, String[] fieldnames, WhereDefinition[] where, OrderByDefinition[] orderby);
	<T extends IObject> ArrayList<T> Select(Class<T> cls, String[] fieldnames, WhereDefinition[] where, OrderByDefinition[] orderby,String groupby);	
	<T extends IObject> ArrayList<T> Select(Class<T> cls, String[] fieldnames, WhereDefinition[] where, OrderByDefinition[] orderby,String groupby,int limit);	
	boolean Insert(IObject obj);
	boolean Delete(IObject obj);
	boolean Update(IObject obj, IObject toUpdate);
	void setConnectionString(String constring);
	String getConnectionString();
}
