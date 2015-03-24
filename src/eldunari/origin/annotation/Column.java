package eldunari.origin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eldunari.origin.enumeration.DataType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	public String name();
	public DataType type() default DataType.AUTO;
	public boolean nullable() default true;
}
