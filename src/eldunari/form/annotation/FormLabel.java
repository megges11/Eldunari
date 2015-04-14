package eldunari.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eldunari.form.enumeration.Orientation;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormLabel{
	String name();
	String text() default "";
	String neighborName() default "";
	Orientation orientation() default Orientation.Bottom;
	String tag() default "";
	
	int width();
	int height();
	
}
