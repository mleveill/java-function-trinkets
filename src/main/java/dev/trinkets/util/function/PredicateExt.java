package dev.trinkets.util.function;

import dev.trinkets.doc.PrimaryRequirement;
import dev.trinkets.doc.Update;
import java.util.function.Predicate;

/**
 * <p>
 * Changes for {@link java.util.function.Predicate}
 * </p>
 * <p>
 * Why:<br>
 *	1. If a Predicate lambda throws a checked exceptions, the developer has to
 *		messily wrap it in a RuntimeException.<br>
 *	2. If Predicate.test throws a RuntimeException,
 *		the compiler will not warn the developer.<br>
 *	3. This class provides type-safe exceptions which is not currently possible
 *		with java.util.function classes.<br>
 * </p>
 * <p>
 * To eliminate the need for this class:<br>
 *	1. Java should support default values for generic parameters.<br>
 *	2. Modify {@code java.util.function.Predicate<T>} to {@code Predicate<T, E extends Throwable = Throwable>}<br>
 * </p>
 * <p>
 * Risk:<br>
 *	1. Maintains source compatibility because the E parameter can be omitted.<br>
 *	2. Maintains binary compatibility because generic types are erased.<br>
 *	3. Impact on code using Reflection APIs should be minimal.<br>
 * </p>
 */
@PrimaryRequirement(updates = Predicate.class)
@FunctionalInterface
public interface PredicateExt<T, E extends Throwable /* = Throwable */> {

	@Update
    boolean test(T t) throws E;

	@Update
    default PredicateExt<T, E> and(PredicateExt<? super T, E> other) {
        return t -> test(t) && other.test(t);
    }

	@Update
    default PredicateExt<T, E> negate() {
        return t -> !test(t);
    }

	@Update
    default PredicateExt<T, E> or(PredicateExt<? super T, E> other) {
        return t -> test(t) || other.test(t);
    }

	@Update
    static <T, E extends Throwable> PredicateExt<T, E> isEqual(Object targetRef) {
		return other -> (targetRef == null && other == null)
				|| (targetRef != null && targetRef.equals(other));
    }

	@Update
    static <T, E extends Throwable> PredicateExt<T, E> not(PredicateExt<? super T, E> target) {
        return t -> !target.test(t);
    }
}
