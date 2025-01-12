package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookForLibrarianDto {
    private Integer id;
    private String title;
    private String author;
    private String publisherName;
    private Integer publicationYear;
    private String isbn;
    private Long allCopies;
    private Long availableCopies;
}
