package eldunari.origin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eldunari.origin.enumeration.ConstraintAction;
import eldunari.origin.enumeration.ConstraintRule;
import eldunari.origin.interfaces.IObject;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Relation {
	public ConstraintAction action() default ConstraintAction.NONE;
	public String name() default "";
	public Class<? extends IObject> table();
	public String field();	
	public ConstraintRule rule() default ConstraintRule.NO_ACTION;
}
