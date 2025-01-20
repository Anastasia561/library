package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a user for the librarian's view.
 * <p>
 * This class encapsulates information about a user.
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
public class UserForLibrarianDto {

    /**
     * The unique identifier of the user.
     */
    private Integer id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The phone number of the user.
     */
    private String phoneNumber;

    /**
     * The address of the user.
     */
    private String address;

    /**
     * If the user is a librarian at the same time.
     */
    private Boolean isLibrarian;

    /**
     * List of borrowing done by the user.
     */
    private List<BorrowingDto> borrowings;
}
