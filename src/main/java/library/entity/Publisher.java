package library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
 * Entity class representing a publisher in the library system.
 * <p>
 * This class is annotated with JPA annotations to map it to the "publisher" table in the database.
 * It represents a publisher, including its name, address, and contact information, as well as
 * the list of books it has published.
 * <p>
 * Annotations:
 * - {@code @Entity}: Indicates that this class is a JPA entity.
 * - {@code @Table}: Maps this entity to the "publisher" table in the database.
 * - Lombok annotations ({@code @Getter}, {@code @Setter}, {@code @ToString}, {@code @Builder}, etc.) are used to
 * automatically generate boilerplate code such as getters, setters, constructors, and toString methods.
 * <p>
 * Relationships:
 * - One-to-Many with {@code Book}: Each publisher can be associated with multiple books.
 * <p>
 * Lifecycle Methods:
 * - {@code checkForConnections()}: A pre-remove lifecycle method that prevents deletion of a publisher
 * if it has associated books.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @ToString.Exclude
    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    private List<Book> books;

    @PreRemove
    private void checkForConnections() {
        if (books != null && !books.isEmpty()) {
            throw new RuntimeException("Publisher has connections with other entities, can not be deleted");
        }
    }
}
