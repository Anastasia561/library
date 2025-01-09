package library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany
    @JoinColumn(name = "user_id", nullable = false)
    private List<User> users;
    @OneToMany
    @JoinColumn(name = "copy_id", nullable = false)
    private List<Copy> copies;
    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;
    @Column(name = "return_date")
    private LocalDate returnDate;
}
