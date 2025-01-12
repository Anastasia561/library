package library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserForLibrarianDto {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean isLibrarian;
    private List<BorrowingDto> borrowings;
}
