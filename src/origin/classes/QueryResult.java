package origin.classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryResult {

	private Connection connection;
	private ResultSet result;
	private Statement statement;

	public QueryResult(Connection con, Statement state,ResultSet result){
		this.connection = con;
		this.statement = state;
		this.result = result;
	}
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public void close(){
		try{
			if(connection!=null){
				this.connection.close();
			}
			if(statement!=null){
				this.statement.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public ResultSet getResult() {
		return result;
	}
	public void setResult(ResultSet result) {
		this.result = result;
	}
	public Statement getStatement() {
		return statement;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

}
