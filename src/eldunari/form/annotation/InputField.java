package eldunari.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eldunari.form.enumation.Orientation;
import eldunari.form.interfaces.IComponent;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InputField {
	Class<? extends IComponent> cls(); 
	String name();
	String neighborName() default "";
	boolean editable() default true;
	Orientation orientation() default Orientation.Right;
	String tag() default "";	
	int width();
	int height();	
}
