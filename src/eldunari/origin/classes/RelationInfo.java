package eldunari.origin.classes;

import eldunari.origin.enumeration.ConstraintAction;
import eldunari.origin.enumeration.ConstraintRule;
import eldunari.origin.interfaces.IObject;

public class RelationInfo {

	private final Class<? extends IObject> table;
	private final String field;
	private final String name;
	private final ConstraintAction action;
	private final ConstraintRule rule;
	
	public RelationInfo(Class<? extends IObject> table,String field,String name,ConstraintAction action, ConstraintRule rule){
		this.table = table;
		this.field = field;
		this.name = name;
		this.action = action;
		this.rule = rule;
	}
	
	public Class<? extends IObject> getTable() {
		return table;
	}
	public String getField() {
		return field;
	}
	public String getName() {
		return name;
	}
	public ConstraintAction getAction() {
		return action;
	}
	public ConstraintRule getRule() {
		return rule;
	}	
	
}
