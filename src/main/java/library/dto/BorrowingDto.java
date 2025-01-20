package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a borrowing entity for view.
 * <p>
 * This class encapsulates information about a borrowing.
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
public class BorrowingDto {

    /**
     * The unique identifier of the borrowing.
     */
    private Integer id;

    /**
     * Name of user how done this borrowing.
     */
    private String userName;

    /**
     * Email of user how done this borrowing.
     */
    private String userEmail;

    /**
     * Copy number of copy that was borrowed.
     */
    private Integer copyNumber;

    /**
     * Title of book that was borrowed.
     */
    private String bookTitle;

    /**
     * Author of the book that was borrowed.
     */
    private String author;

    /**
     * ISBN of the book that was borrowed.
     */
    private String isbn;

    /**
     * Date when borrowing starts.
     */
    private LocalDate borrowDate;

    /**
     * Date when borrowing ends.
     */
    private LocalDate returnDate;
}
