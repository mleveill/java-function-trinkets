package dev.trinkets.util.function;

import dev.trinkets.doc.PrimaryRequirement;
import dev.trinkets.doc.Update;
import java.util.function.Supplier;

/**
 * <p>
 * Changes for {@link java.util.function.Supplier}
 * </p>
 * <p>
 * Why:<br>
 *	1. If a Supplier lambda throws a checked exceptions, the developer has to
 *		messily wrap it in a RuntimeException.<br>
 *	2. If Supplier.get throws a RuntimeException,
 *		the compiler will not warn the developer.<br>
 *	3. This class provides type-safe exceptions which is not currently possible
 *		with java.util.function classes.<br>
 * </p>
 * <p>
 * To eliminate the need for this class:<br>
 *	1. Java should support default values for generic parameters.<br>
 *	2. Modify {@code java.util.function.Supplier<T>} to {@code Supplier<T, E extends Throwable = Throwable>}<br>
 * </p>
 * <p>
 * Risk:<br>
 *	1. Maintains source compatibility because the E parameter can be omitted.<br>
 *	2. Maintains binary compatibility because generic types are erased.<br>
 *	3. Impact on code using Reflection APIs should be minimal.<br>
 * </p>
 */
@PrimaryRequirement(updates = Supplier.class)
@FunctionalInterface
public interface SupplierExt<T, E extends Throwable /* = Throwable */> {

	@Update
	T get() throws E;
}
