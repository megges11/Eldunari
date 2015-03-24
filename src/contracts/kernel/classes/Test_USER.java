package contracts.kernel.classes;
import contracts.annotation.PrimaryKey;
import contracts.annotation.Table;
import contracts.annotation.Precision;
import contracts.annotation.Column;
import contracts.kernel.interfaces.IObject;
import contracts.kernel.enumeration.DataType;

@Table("USER")
public class Test_USER implements IObject{

	@PrimaryKey
	@Precision(10)
	@Column(name="ID",nullable=false,type=DataType.INTEGER)
	private int id;
	
	@Precision(30)
	@Column(name="USERNAME",nullable=false,type=DataType.TEXT)
	private String username;
	
	@Precision(30)
	@Column(name="PASSWORD",nullable=false,type=DataType.TEXT)
	private String password;
	
	@Precision(255)
	@Column(name="EMAIL",nullable=false,type=DataType.TEXT)
	private String email;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String DisplayValue() {
		return id+": "+username;
	}
	
}
