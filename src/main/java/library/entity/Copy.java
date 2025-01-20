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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Entity class representing a copy of a book in the library system.
 * <p>
 * This class is annotated with JPA annotations to map it to the "copy" table in the database.
 * It represents an individual physical copy of a book, identified by a unique copy number and status.
 * <p>
 * Annotations:
 * - {@code @Entity}: Indicates that this class is a JPA entity.
 * - {@code @Table}: Maps this entity to the "copy" table in the database.
 * - Lombok annotations ({@code @Getter}, {@code @Setter}, {@code @ToString}, {@code @Builder}, etc.) are used to
 * automatically generate boilerplate code such as getters, setters, constructors, and toString methods.
 * <p>
 * Relationships:
 * - Many-to-One with {@code Book}: Each copy is associated with a single book.
 * - One-to-Many with {@code Borrowing}: Each copy can have multiple borrowings over time.
 * <p>
 * Lifecycle Methods:
 * - {@code checkForConnections()}: A pre-remove lifecycle method that prevents deletion of a copy
 * if it has associated borrowings.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "copy")
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;
    @Column(name = "copy_number", nullable = false)
    private Integer copyNumber;
    @Column(nullable = false)
    private String status;
    @ToString.Exclude
    @OneToMany(mappedBy = "copy", fetch = FetchType.EAGER)
    private List<Borrowing> borrowings;

    @PreRemove
    private void checkForConnections() {
        if (borrowings != null && !borrowings.isEmpty()) {
            throw new RuntimeException("Copy has connections with other entities, can not be deleted");
        }
    }
}
