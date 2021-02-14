package negrosa.common.util;

@FunctionalInterface
public interface UnsafeSupplier<T> {

    T get() throws Throwable;
    
}