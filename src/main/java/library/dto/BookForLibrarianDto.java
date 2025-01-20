package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) representing a book for the librarian's view.
 * <p>
 * This class encapsulates information about a book, including its title, author,
 * publication details, and availability statistics.
 * </p>
 *
 * <p>
 * The class is annotated with:
 * <ul>
 *   <li>{@link Builder}: Enables the builder pattern for creating instances.</li>
 *   <li>{@link Getter}: Automatically generates getter methods for all fields.</li>
 *   <li>{@link Setter}: Automatically generates setter methods for all fields.</li>
 *   <li>{@link ToString}: Generates a string representation of the object.</li>
 *   <li>{@link AllArgsConstructor}: Generates a constructor with all fields as parameters.</li>
 *   <li>{@link NoArgsConstructor}: Generates a no-argument constructor.</li>
 * </ul>
 * </p>
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookForLibrarianDto {

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

    /**
     * The name of the book's publisher.
     */
    private String publisherName;

    /**
     * The year the book was published.
     */
    private Integer publicationYear;

    /**
     * The International Standard Book Number (ISBN) of the book.
     */
    private String isbn;

    /**
     * The total number of copies of the book available in the library.
     */
    private Long allCopiesCount;

    /**
     * The number of copies of the book currently available for borrowing.
     */
    private Long availableCopiesCount;
}
