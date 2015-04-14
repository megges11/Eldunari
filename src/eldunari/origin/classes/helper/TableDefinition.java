package eldunari.origin.classes.helper;

import java.util.ArrayList;

import eldunari.general.annotation.Definition;
import eldunari.origin.annotation.Filter;

public class TableDefinition {

	private Filter filter;
	private Definition definition;
	private ArrayList<String> errors = new ArrayList<String>();
	
	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		if(this.filter != null && !this.filter.equals(filter)){
			addError("Filter already set");
			return;
		}
		this.filter = filter;
	}
	
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
	private void addError(String err){
		this.errors.add(err);
	}
	public boolean hasErrors(){
		return !this.errors.isEmpty();
	}

	public Definition getDefintion() {
		return definition;
	}

	public void setDefinition(Definition definition) {
		if(this.definition != null && !this.definition.equals(definition)){
			addError("Definition already set");
			return;
		}
		this.definition = definition;
	}
	
	
	
}
