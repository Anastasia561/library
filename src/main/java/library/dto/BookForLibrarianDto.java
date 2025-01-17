package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookForLibrarianDto {
    private Integer id;
    private String title;
    private String author;
    private String publisherName;
    private Integer publicationYear;
    private String isbn;
    private Long allCopiesCount;
    private Long availableCopiesCount;
}
