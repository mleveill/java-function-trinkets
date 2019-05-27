package dev.trinkets.util.function;

import dev.trinkets.doc.PrimaryRequirement;
import dev.trinkets.doc.Update;
import java.util.function.Function;

/**
 * <p>
 * Why:<br>
 *	1. If a Function lambda throws a checked exceptions,
 *		then the developer has to messily wrap it in a RuntimeException.<br>
 *	2. If Function.apply throws a RuntimeException,
 *		then the compiler will not warn the developer.<br>
 *	3. This class provides type-safe exceptions,
 *		which is not currently possible with java.util.function classes.<br>
 * </p>
 * <p>
 * To eliminate the need for this class:<br>
 *	1. Java should support default values for generic parameters.<br>
 *	2. Modify {@code java.util.function.Function<T, R>} to {@code Function<T, R, E extends Throwable = Throwable>}<br>
 * </p>
 * <p>
 * Risk:<br>
 *	1. Maintain source compatibility because the E parameter can be omitted.<br>
 *	2. Maintain binary compatibility because generic types are erased.<br>
 *	3. Impact on code using Reflection APIs should be minimal.<br>
 * </p>
 */
@PrimaryRequirement(updates = Function.class)
@FunctionalInterface
public interface FunctionExt<T, R, E extends Throwable /* = Throwable */> {

	@Update
	R apply(T t) throws E;

	@Update
    default <V> FunctionExt<V, R, E> compose(FunctionExt<? super V, ? extends T, E> before) {
        return v -> apply(before.apply(v));
    }

	@Update
    default <V> FunctionExt<T, V, E> andThen(FunctionExt<? super R, ? extends V, E> after) {
        return t -> after.apply(apply(t));
    }

	@Update
    static <T> FunctionExt<T, T, Throwable> identity() {
        return t -> t;
    }
}
