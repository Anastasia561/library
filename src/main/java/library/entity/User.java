package library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import library.annotation.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Entity class representing a user in the library system.
 * <p>
 * This class is annotated with JPA annotations to map it to the "user" table in the database.
 * It represents a user with attributes such as name, email, phone number, and address. A user may have
 * multiple borrowings and optionally be associated with a librarian record.
 * <p>
 * Annotations:
 * - {@code @Entity}: Indicates that this class is a JPA entity.
 * - {@code @Table}: Maps this entity to the "user" table in the database. The table name is escaped
 * with double quotes to avoid conflicts with reserved keywords in SQL.
 * - Lombok annotations ({@code @Getter}, {@code @Setter}, {@code @ToString}, {@code @Builder}, etc.) are used to
 * automatically generate boilerplate code such as getters, setters, constructors, and toString methods.
 * <p>
 * Relationships:
 * - One-to-Many with {@code Borrowing}: Each user can have multiple borrowings.
 * - One-to-One with {@code Librarian}: A user can optionally have a librarian record.
 * <p>
 * Lifecycle Methods:
 * - {@code checkForConnections()}: A pre-remove lifecycle method that prevents deletion of a user
 * if they have associated borrowings.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Borrowing> borrowings;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "librarian_id", nullable = false)
    private Librarian librarian;

    @PreRemove
    private void checkForConnections() {
        if (borrowings != null && !borrowings.isEmpty()) {
            throw new RuntimeException("User has connections with other entities, can not be deleted");
        }
    }
}
