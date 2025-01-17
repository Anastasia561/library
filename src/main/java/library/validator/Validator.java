package library.validator;

import library.annotation.Email;
import library.annotation.Isbn;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern ISBN_PATTERN = Pattern.compile
            ("^\\d{13}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile
            ("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

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
