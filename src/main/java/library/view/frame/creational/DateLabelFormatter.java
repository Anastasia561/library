package library.view.frame.creational;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A formatter for date values used with {@code JFormattedTextField}.
 * This class extends {@code JFormattedTextField.AbstractFormatter} to provide
 * a way to format and parse date values in the "yyyy-MM-dd" format.
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Parses a date {@code String} into a {@code Date} object.
     *
     * @param text the date string to be parsed.
     * @return the parsed {@code Date} object.
     * @throws ParseException if the string cannot be parsed into a valid date.
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Converts a {@code Calendar} object into a formatted date {@code String}.
     *
     * @param value the {@code Calendar} object to be formatted.
     * @return a formatted date string in the "yyyy-MM-dd" format, or an empty string if the value is {@code null}.
     * @throws ParseException if an error occurs during formatting.
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
