package eldunari.origin.classes.helper;

import java.util.ArrayList;

import eldunari.origin.annotation.Column;
import eldunari.origin.annotation.DefaultValue;
import eldunari.origin.annotation.Format;
import eldunari.origin.annotation.Precision;
import eldunari.origin.annotation.PrimaryKey;
import eldunari.origin.annotation.Relation;
import eldunari.origin.annotation.Required;

public class ColumnDefinition {

	private Column column = null;
	private DefaultValue defaultValue = null;
	private Format format = null;
	private Precision precision = null;
	private PrimaryKey primaryKey = null;
	private Required required = null;
	private ArrayList<Relation> relations = new ArrayList<Relation>();	
	
	private ArrayList<String> errors = new ArrayList<String>();
	
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		if(this.column != null && !this.column.equals(column)){
			addError("Column already set");
			return;
		}
		this.column = column;
	}
	public DefaultValue getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(DefaultValue defaultValue) {
		if(this.defaultValue != null && !this.defaultValue.equals(defaultValue)){
			addError("Default Value already set");
			return;
		}
		this.defaultValue = defaultValue;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		if(this.format != null && !this.format.equals(format)){
			addError("Format already set");
			return;
		}
		this.format = format;
	}
	public Precision getPrecision() {
		return precision;
	}
	public void setPrecision(Precision precision) {
		if(this.precision != null && !this.precision.equals(precision)){
			addError("Precision already set");
			return;
		}
		this.precision = precision;
	}
	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(PrimaryKey primaryKey) {
		if(this.primaryKey != null && !this.primaryKey.equals(primaryKey)){
			addError("Primary Key already set");
			return;
		}
		this.primaryKey = primaryKey;
	}
	public ArrayList<Relation> getRelations() {
		return relations;
	}
	public void setRelations(ArrayList<Relation> relations) {
		this.relations = relations;
	}
	public void addRelation(Relation relation){
		this.relations.add(relation);
	}
	public String getError() {
		String error = "";
		for(String err : errors){
			error += err+"\n";
		}
		return error;
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
	public Required getRequired() {
		return required;
	}
	public void setRequired(Required required) {
		if(this.required != null && !this.required.equals(required)){
			addError("Required already set");
			return;
		}
		this.required = required;
	}
	
	
	
	
}
