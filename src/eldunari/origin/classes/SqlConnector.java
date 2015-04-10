package eldunari.origin.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import eldunari.origin.classes.helper.OrderByDefinition;
import eldunari.origin.classes.helper.QueryResult;
import eldunari.origin.classes.helper.WhereDefinition;
import eldunari.origin.interfaces.IConnectable;
import eldunari.origin.interfaces.IObject;

public class SqlConnector extends Connector implements IConnectable{

	private ArrayList<String> errors = new ArrayList<String>();	
	private String connectionString = "jdbc:mysql://localhost:3306/patient_management?user=root&password=";
	
	@Override
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(connectionString);
			if(errors.size()!=0){
				errors.clear();
			}
		} catch ( Exception e ) {
			this.errors.add(e.getMessage());
		}
		return con;
	}

	public QueryResult executeQuery(String sql){
		try{
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			return new QueryResult(con,stmt,result);			
		}catch(Exception ex){
			this.errors.add(ex.getMessage());
			return null;
		}
	}
	public int executeUpdate(String sql){
		try{
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);
			stmt.close();
			con.close();
			return result;	
		}catch(Exception ex){
			this.errors.add(ex.getMessage());
			return 0;
		}
	}

	@Override
	public <T extends IObject> ArrayList<T> Select(Class<T> cls, String[] fieldnames, WhereDefinition[] where,
			OrderByDefinition[] orderby, String groupby, int limit) {
		return this.Select(this, cls, fieldnames, where, orderby, groupby, limit);
	}

	@Override
	public boolean Insert(IObject obj) {
		return this.Insert(this, obj);
	}

	@Override
	public boolean Delete(IObject obj) {
		return this.Delete(this, obj);
	}

	@Override
	public boolean Update(IObject obj, IObject toUpdate) {
		return this.Update(this, obj,toUpdate);
	}

	@Override
	public String getError() {
		String error = "";
		for(String err : errors){
			error+=err+"\n";
		}
		return error;
	}	
	public void addError(String value){
		errors.add(value);
	}

	@Override
	public <T extends IObject> ArrayList<T> Select(Class<T> cls) {
		return this.Select(this, cls, null, null, null, null, -1);
	}

	@Override
	public <T extends IObject> ArrayList<T> Select(Class<T> cls,
			String[] fieldnames) {
		return this.Select(this, cls, fieldnames, null, null, null, -1);
	}

	@Override
	public <T extends IObject> ArrayList<T> Select(Class<T> cls,
			String[] fieldnames, WhereDefinition[] where) {
		return this.Select(this, cls, fieldnames, where, null, null, -1);
	}

	@Override
	public <T extends IObject> ArrayList<T> Select(Class<T> cls,
			String[] fieldnames, WhereDefinition[] where,
			OrderByDefinition[] orderby) {
		return this.Select(this, cls, fieldnames, where, orderby, null, -1);
	}

	@Override
	public <T extends IObject> ArrayList<T> Select(Class<T> cls,
			String[] fieldnames, WhereDefinition[] where,
			OrderByDefinition[] orderby, String groupby) {
		return this.Select(this, cls, fieldnames, where, orderby, groupby, -1);
	}

	@Override
	public boolean hasError() {
		return (errors.size() != 0);
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	@Override
	public void setValidatorPackage(String value) {
		this.VALIDATOR_PACKAGE = value;
	}

	@Override
	public void setTriggerPackage(String value) {
		this.TRIGGER_PACKAGE = value;
	}

	@Override
	public String getValidatorPackage() {
		return VALIDATOR_PACKAGE;
	}

	@Override
	public String getTriggerPackage() {
		return TRIGGER_PACKAGE;
	}
}
