package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingDto {
    private Integer id;
    private String userName;
    private String userEmail;
    private Integer copyNumber;
    private String bookTitle;
    private String author;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
