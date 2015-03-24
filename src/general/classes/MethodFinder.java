package general.classes;

import java.lang.reflect.Method;

public class MethodFinder {

	public static void invokeMethod(String methodname, Class<?> targetClass){
		try{
			if(methodname!=null && !methodname.isEmpty()){
				Object[] params = null;
				Object obj = targetClass.newInstance();
				Method method = targetClass.getDeclaredMethod(methodname, new Class<?>[]{});
				method.invoke(obj, params);
			}else{
				System.err.println("Method Not Found");
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
	}
	
	public static void invokeMethod(String methodname, Class<?> targetClass,Class<?>[] paramTypes,Object[]params){
		try{
			if(methodname!=null && !methodname.isEmpty()){
				Object obj = targetClass.newInstance();
				Method method = targetClass.getDeclaredMethod(methodname, paramTypes);
				method.invoke(obj, params);
			}else{
				System.err.println("Method Not Found");
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
	}
	
	public static void invokeMethod(String methodname, Object targetObject){
		try{
			if(methodname!=null && !methodname.isEmpty()){
				Object[] params = null;
				Method method = targetObject.getClass().getDeclaredMethod(methodname, new Class<?>[]{});
				method.invoke(targetObject, params);
			}else{
				System.err.println("Method Not Found");
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
	}
	
	public static void invokeMethod(String methodname, Object targetObject,Class<?>[] paramTypes,Object[]params){
		try{
			if(methodname!=null && !methodname.isEmpty()){
				Method method = targetObject.getClass().getDeclaredMethod(methodname, paramTypes);
				method.invoke(targetObject, params);
			}else{
				System.err.println("Method Not Found");
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}
	}
}
