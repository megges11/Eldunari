package origin.classes;

import origin.enumeration.Operator;

public class WhereDefinition {

	private String field;
	private String value;
	private Operator operator;
	
	public WhereDefinition(){}
	public WhereDefinition(String field, String value){
		this.field = field;
		this.value = value;
		this.operator = Operator.Equal;
	}
	public WhereDefinition(String field, String value,Operator operator){
		this.field = field;
		this.value = value;
		this.operator = operator;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public String getClause(){
		return field+" "+operator.getValue()+" "+value;
	}
}
