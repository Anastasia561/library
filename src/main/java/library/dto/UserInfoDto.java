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
public class UserInfoDto {
    private Integer id;
    private String name;
    private String email;
    private List<BorrowingDto> borrowings;
}
