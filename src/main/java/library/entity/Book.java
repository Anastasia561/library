package library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import library.annotation.Isbn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Represents a book entity in the library system.
 * This class is mapped to the "book" table in the database.
 * It contains information about the book such as its title, author, publisher, publication year, ISBN,
 * and its associated copies. It also provides constraints and behaviors specific to the Book entity.
 * <p>
 * Annotations:
 * - {@code @Getter}, {@code @Setter}: Auto-generates getter and setter methods for the fields.
 * - {@code @ToString}: Generates a string representation of the object, excluding the "copies" field to avoid potential recursive loops.
 * - {@code @Builder}: Enables the builder pattern for constructing Book objects.
 * - {@code @NoArgsConstructor}, {@code @AllArgsConstructor}: Provides constructors with no arguments and all arguments, respectively.
 * - {@code @Entity}, {@code @Table(name = "book")}: Maps this class to a database table named "book".
 * <p>
 * Relationships:
 * - {@code @ManyToOne}: A Book is associated with a Publisher.
 * - {@code @OneToMany}: A Book can have multiple associated copies.
 * <p>
 * Constraints:
 * - The "title" and "author" fields are mandatory (not nullable).
 * - The "isbn" field is mandatory, must be unique, and follows a valid ISBN format.
 * - The "publisher_id" foreign key references the Publisher table and is mandatory.
 * - The "publication_year" field is mandatory.
 * <p>
 * Lifecycle Callbacks:
 * - {@code @PreRemove}: Ensures that a Book cannot be deleted if it has associated copies.
 * <p>
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    private Publisher publisher;
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;
    @Isbn
    @Column(unique = true, nullable = false)
    private String isbn;
    @ToString.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<Copy> copies;

    @PreRemove
    private void checkForConnections() {
        if (copies != null && !copies.isEmpty()) {
            throw new RuntimeException("Book has connections with other entities, can not be deleted");
        }
    }
}
