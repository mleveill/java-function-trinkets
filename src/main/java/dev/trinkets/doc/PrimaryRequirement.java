package dev.trinkets.doc;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Tags classes that exist due to deficiencies in the standard library.
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface PrimaryRequirement {

	/**
	 * @return The Java standard library class with deficiencies.
	 */
	Class<?> updates();
}
