package library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Entity class representing a librarian in the library system.
 * <p>
 * This class is annotated with JPA annotations to map it to the "librarian" table in the database.
 * It represents a librarian, which is associated with a {@code User} entity and includes additional details
 * such as employment date and position.
 * <p>
 * Annotations:
 * - {@code @Entity}: Indicates that this class is a JPA entity.
 * - {@code @Table}: Maps this entity to the "librarian" table in the database.
 * - Lombok annotations ({@code @Getter}, {@code @Setter}, {@code @ToString}, {@code @Builder}, etc.) are used to
 * automatically generate boilerplate code such as getters, setters, constructors, and toString methods.
 * <p>
 * Relationships:
 * - One-to-One with {@code User}: Each librarian is associated with a single user.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "librarian")
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @Column(name = "employment_date", nullable = false)
    private LocalDate employmentDate;
    @Column(nullable = false)
    private String position;
}
