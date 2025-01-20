package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) representing a book for the user's view.
 * <p>
 * This class encapsulates information about a book, including its title and author.
 * </p>
 *
 * <p>
 * The class is annotated with:
 * <ul>
 *   <li>{@link Builder}: Enables the builder pattern for creating instances.</li>
 *   <li>{@link Getter}: Automatically generates getter methods for all fields.</li>
 *   <li>{@link ToString}: Generates a string representation of the object.</li>
 *   <li>{@link AllArgsConstructor}: Generates a constructor with all fields as parameters.</li>
 *   <li>{@link NoArgsConstructor}: Generates a no-argument constructor.</li>
 * </ul>
 * </p>
 */
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookForUserDto {

    /**
     * The unique identifier of the book.
     */
    private Integer id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;
}
