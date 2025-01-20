package library.validator;

import library.annotation.Email;
import library.annotation.Isbn;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * The {@code Validator} class provides utility methods to validate objects based on custom annotations.
 * It supports validation of fields annotated with {@code @Email} and {@code @Isbn}.
 */
public class Validator {
    /**
     * A {@code Pattern} for validating ISBN.
     * Matches strings containing exactly 13 digits.
     */
    private static final Pattern ISBN_PATTERN = Pattern.compile("^\\d{13}$");

    /**
     * A {@code Pattern} for validating email addresses.
     * Matches strings that follow the standard email format.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile
            ("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * Validates the fields of an object based on the annotations present on its fields.
     * <p>
     * - Fields annotated with {@code @Email} are validated against the {@code EMAIL_PATTERN}.
     * - Fields annotated with {@code @Isbn} are validated against the {@code ISBN_PATTERN}.
     * </p>
     *
     * @param obj the object to validate
     * @throws IllegalArgumentException if a field's value does not satisfy the validation criteria
     * @throws RuntimeException         if there is an error accessing the object's fields
     */
    public static void validate(Object obj) {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Email.class)) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if (value != null && !EMAIL_PATTERN.matcher(value.toString()).matches()) {
                    Email annotation = field.getAnnotation(Email.class);
                    throw new IllegalArgumentException(annotation.message());
                }
            } else if (field.isAnnotationPresent(Isbn.class)) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if (value != null && !ISBN_PATTERN.matcher(value.toString()).matches()) {
                    Isbn annotation = field.getAnnotation(Isbn.class);
                    throw new IllegalArgumentException(annotation.message());
                }
            }
        }
    }
}
