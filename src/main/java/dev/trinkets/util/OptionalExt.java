package dev.trinkets.util;

import dev.trinkets.doc.SecondaryRequirement;
import dev.trinkets.doc.Update;
import dev.trinkets.util.function.ConsumerExt;
import dev.trinkets.util.function.FunctionExt;
import dev.trinkets.util.function.PredicateExt;
import dev.trinkets.util.function.SupplierExt;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Supplemental to changes in {@link dev.trinkets.util.function}
 * </p>
 *
 * <p>
 * To eliminate the need for this class:<br>
 *	1. Change method(s) tagged {@link dev.trinkets.doc.Update}<br>
 * </p>
 *
 * @see dev.trinkets.util.function.ConsumerExt
 */
@SecondaryRequirement(causedBy = { ConsumerExt.class, FunctionExt.class, PredicateExt.class, SupplierExt.class })
public class OptionalExt<T> {

	public static <T> OptionalExt<T> of(Optional<T> opt) {
		return new OptionalExt<>(opt);
	}

	public static <T> OptionalExt<T> of(T value) {
		Objects.requireNonNull(value);
		return of(Optional.of(value));
	}

    public static<T> OptionalExt<T> empty() {
        return of(Optional.ofNullable(null));
    }

    public static <T> OptionalExt<T> ofNullable(T value) {
        return value != null ? of(value) : empty();
    }

	private final Optional<T> opt;

	private OptionalExt(Optional<T> opt) {
		this.opt = opt;
	}

	public Optional<T> unwrap() {
		return opt;
	}

	@Update
	public <E extends Throwable> void ifPresent(ConsumerExt<T, E> action) throws E {
        ifPresentOrElse(action, () -> {});
	}

	@Update
    public <E extends Throwable> void ifPresentOrElse(
			ConsumerExt<? super T, E> action, Runnable emptyAction) throws E {

        if (opt.isPresent()) {
            action.accept(opt.get());
        } else {
            emptyAction.run();
        }
    }

	@Update
    public <E extends Throwable> OptionalExt<T> filter(
			PredicateExt<? super T, E> predicate) throws E {

        if (!opt.isPresent()) {
            return this;
        } else {
            return predicate.test(opt.get()) ? this : empty();
        }
    }

	@Update
    public <U, E extends Throwable> OptionalExt<U> map(
			FunctionExt<? super T, ? extends U, E> mapper) throws E {

        if (!opt.isPresent()) {
            return empty();
        } else {
            return OptionalExt.ofNullable(mapper.apply(opt.get()));
        }
    }

	@Update
    public <U, E extends Throwable> OptionalExt<U> flatMap(
			FunctionExt<? super T, ? extends OptionalExt<U>, E> mapper) throws E {

        if (!opt.isPresent()) {
            return empty();
        } else {
            return mapper.apply(opt.get());
        }
    }

	@Update
    public <E extends Throwable> OptionalExt<T> or(
			SupplierExt<? extends OptionalExt<T>, E> supplier) throws E {

        if (opt.isPresent()) {
            return this;
        } else {
            return supplier.get();
        }
    }

	@Update
    public <E extends Throwable> T orElseGet(SupplierExt<? extends T, E> supplier) throws E {
        return opt.isPresent() ? opt.get() : supplier.get();
    }
}
