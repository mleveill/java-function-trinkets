# dev.trinkets / java-function-trinkets
Utilities to fill gaps in the Java standard libraries

Checked exceptions are favored by many developers because of their type safety. One of the reasons many developers don't like checked exceptions is because of how painful they are to use in lambdas. By making some minor changes to common `@FunctionalInterface` classes, this pain point will be eliminated. If the language also adopts default values for generic parameters, then the classes will retain backwards-compatibility.

# Example
1. If a `java.util.function.Consumer` lambda throws a checked exceptions, the developer has to messily wrap it in a RuntimeException.
2. If `Consumer.accept` throws a `RuntimeException`, then the compiler will not warn the developer.

The proposed changes to `Consumer` include: adding a generic type parameter, and throwing an exception from `accept`:
```java
public interface Consumer<T, E extends Throwable> {
    void accept(T t) throws E;
}
```
Since existing method signatures in Java libraries may reference `Consumer<X>`, we can avoid breaking these libraries with a new syntactic sugar:
```java
public interface Consumer<T, E extends Throwable = RuntimeException> {
    void accept(T t) throws E;
}
```
This new syntax allows specifying a default value for the last generic type parameters. This means any reference to `Consumer<X>` would be interpreted by the Java compiler as `Consumer<X, RuntimeException>`. `RuntimeException` was chosen as the default value here because it doesn't change the semantics of the original implementation of `Consumer` (it was always possible for a `RuntimeException` to be thrown by `accept`).

https://function.trinkets.dev/javadoc/dev/trinkets/util/function/ConsumerExt.html
