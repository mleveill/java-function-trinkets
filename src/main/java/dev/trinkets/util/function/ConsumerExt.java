package dev.trinkets.util.function;

import dev.trinkets.doc.PrimaryRequirement;
import dev.trinkets.doc.Update;
import java.util.function.Consumer;

/**
 * Why:
 *	1. If a Consumer lambda throws a checked exceptions, the developer has to
 *		messily wrap it in a RuntimeException.
 *	2. If Consumer.accept throws a RuntimeException,
 *		the compiler will not warn the developer.
 *	3. This class provides type-safe exceptions which is not currently possible
 *		with java.util.function classes.
 *
 * To eliminate the need for this class:
 *	1. Java should support default values for generic parameters.
 *	2. Modify {@code java.util.function.Consumer<T>} to  {@code Consumer<T, E extends Throwable = Throwable>}
 *
 * Risk:
 *	1. Maintain source compatibility because the E parameter can be omitted.
 *	2. Maintain binary compatibility because generic types are erased.
 *	3. Impact on code using Reflection APIs should be minimal.
 */
@PrimaryRequirement(updates = Consumer.class)
@FunctionalInterface
public interface ConsumerExt<T, E extends Throwable /* = Throwable */> {

	@Update
	void accept(T t) throws E;

	@Update
    default ConsumerExt<T, E> andThen(ConsumerExt<? super T, E> after) {
        return t -> {
			accept(t);
			after.accept(t);
		};
    }
}
