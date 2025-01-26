package library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code Isbn} annotation is used to validate that a field contains a valid ISBN format.
 * It can be applied to fields in a class and is retained at runtime for validation purposes.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Isbn {
    /**
     * Specifies the error message to be used when the field does not contain a valid ISBN.
     *
     * @return the error message
     */
    String message() default "Invalid isbn format";
}
