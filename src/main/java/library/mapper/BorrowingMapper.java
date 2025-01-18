package library.mapper;

import library.dto.BorrowingDto;
import library.dto.UserForLibrarianDto;
import library.entity.Borrowing;
import library.entity.Copy;
import library.entity.User;
import library.service.CopyService;
import library.service.UserService;

public class BorrowingMapper {
    public static BorrowingDto toBorrowingDto(Borrowing borrowing) {
        return BorrowingDto.builder()
                .id(borrowing.getId())
                .userName(borrowing.getUser().getName())
                .userEmail(borrowing.getUser().getEmail())
                .copyNumber(borrowing.getCopy().getCopyNumber())
                .bookTitle(borrowing.getCopy().getBook().getTitle())
                .author(borrowing.getCopy().getBook().getAuthor())
                .isbn(borrowing.getCopy().getBook().getIsbn())
                .borrowDate(borrowing.getBorrowDate())
                .returnDate(borrowing.getReturnDate())
                .build();
    }

    public static Borrowing toBorrowing(BorrowingDto dto) {
        UserService userService = UserService.getInstance("hibernate.cfg.xml");
        UserForLibrarianDto userDto = userService.getUserByEmailForLibrarian(dto.getUserEmail());
        User user = UserMapper.toUserFromUserForLibrarianDto(userDto, false); // Skip borrowings

        CopyService copyService = CopyService.getInstance("hibernate.cfg.xml");
        Copy copy = copyService.getCopyByCopyNumber(dto.getCopyNumber());

        return Borrowing.builder()
                .id(dto.getId())
                .user(user)
                .copy(copy)
                .borrowDate(dto.getBorrowDate())
                .returnDate(dto.getReturnDate())
                .build();
    }
}
