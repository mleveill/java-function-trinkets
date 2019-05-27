package dev.trinkets.doc;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Tags classes that exist because of another class tagged {@link dev.trinkets.doc.PrimaryRequirement}
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface SecondaryRequirement {

	/**
	 * Specifies classes that are driving the secondary changes in classes tagged {@link SecondaryRequirement}
	 *
	 * @return Classes tagged {@link PrimaryRequirement}
	 */
	Class<?>[] causedBy();
}
