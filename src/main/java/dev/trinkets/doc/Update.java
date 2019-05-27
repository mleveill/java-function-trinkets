package dev.trinkets.doc;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Tags a method that deviates from the Java standard library. The standard
 * library should incorporate those changes.
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Update {
}
