package eldunari.origin.classes.helper;

import eldunari.origin.enumeration.Direction;

public class OrderByDefinition {

	private String field;
	private Direction direction;
	
	public OrderByDefinition(){	}
	public OrderByDefinition(String field, Direction direction){	
		this.field = field;
		this.direction = direction;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public String getClause(){
		return field+" "+direction.getValue();
	}
	
}
