package library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Entity class representing a borrowing record in the library system.
 * <p>
 * This class is annotated with JPA annotations to map it to the "borrowing" table in the database.
 * It represents the relationship between a user and a borrowed copy of a book, along with the associated
 * borrowing and return dates.
 * <p>
 * Annotations:
 * - {@code @Entity}: Indicates that this class is a JPA entity.
 * - {@code @Table}: Maps this entity to the "borrowing" table in the database.
 * - Lombok annotations ({@code @Getter}, {@code @Setter}, {@code @ToString}, {@code @Builder}, etc.) are used to
 * automatically generate boilerplate code such as getters, setters, constructors, and toString methods.
 * <p>
 * Relationships:
 * - Many-to-One with {@code User}: Each borrowing is associated with a single user.
 * - Many-to-One with {@code Copy}: Each borrowing is associated with a single copy of a book.
 * </p>
 * Fields:
 * - {@code id}: The unique identifier for the borrowing record.
 * - {@code user}: The user who borrowed the book.
 * - {@code copy}: The specific copy of the book that was borrowed.
 * - {@code borrowDate}: The date the book was borrowed.
 * - {@code returnDate}: The date the book was returned (nullable).
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "copy_id", referencedColumnName = "id", nullable = false)
    private Copy copy;
    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
}
