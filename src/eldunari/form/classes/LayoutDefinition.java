package eldunari.form.classes;

import java.util.ArrayList;

import eldunari.form.annotation.Caption;
import eldunari.form.annotation.Dimension;
import eldunari.form.annotation.InputField;
import eldunari.form.annotation.FormLabel;

public class LayoutDefinition {

	private FormLabel label;
	private Dimension dimension;
	private InputField inputField;
	private Caption caption;
	
	private ArrayList<String> errors = new ArrayList<String>();
	
	public FormLabel getLabel() {
		return label;
	}
	public void setLabel(FormLabel label) {
		if(this.label != null && !this.label.equals(label)){
			addError("Label already set");
			return;
		}
		this.label = label;
	}
	public Dimension getDimension() {
		return dimension;
	}
	public void setDimension(Dimension dimension) {
		if(this.dimension != null && !this.dimension.equals(dimension)){
			addError("Dimension already set");
			return;
		}
		this.dimension = dimension;
	}
	public InputField getInputField() {
		return inputField;
	}
	public void setInputField(InputField field) {
		if(this.inputField != null && !this.inputField.equals(field)){
			addError("Input Field already set");
			return;
		}
		this.inputField = field;
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
	public Caption getCaption() {
		return caption;
	}
	public void setCaption(Caption caption) {
		if(this.caption != null && !this.caption.equals(caption)){
			addError("Caption already set");
			return;
		}
		this.caption = caption;
	}
	
}
