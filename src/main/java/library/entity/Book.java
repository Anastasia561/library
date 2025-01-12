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
