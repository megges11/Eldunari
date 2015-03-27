package eldunari.form.classes;

import eldunari.general.classes.MethodFinder;

public class CallMethod {

	private Class<?> cls;
	private String methodname;
	private Class<?>[] types;
	private Object[] params;

	public CallMethod(Class<?> cls, String methodname){
		this.cls = cls;
		this.methodname = methodname;
	}
	
	public CallMethod(Class<?> cls, String methodname,Class<?>[] types, Object[] params){
		this.cls = cls;
		this.methodname = methodname;
		this.types = types;
		this.params = params;
	}
	
	public Class<?> getCalledClass() {
		return cls;
	}
	public void setCalledClass(Class<?> cls) {
		this.cls = cls;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public Class<?>[] getTypes() {
		return types;
	}
	public void setTypes(Class<?>[] types) {
		this.types = types;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}

	public void Call(){
		if(cls!= null && !methodname.isEmpty() && types == null && params == null){
			MethodFinder.invokeMethod(methodname, cls);
		}else if(cls!= null && !methodname.isEmpty() && types != null && params != null){
			MethodFinder.invokeMethod(methodname, cls,types,params);
		}else{
			System.err.println("Class and Methodname must be set");
		}
	}

}
