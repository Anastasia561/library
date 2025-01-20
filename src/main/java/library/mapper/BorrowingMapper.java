package library.mapper;

import library.dto.BorrowingDto;
import library.dto.UserForLibrarianDto;
import library.entity.Borrowing;
import library.entity.Copy;
import library.entity.User;
import library.service.CopyService;
import library.service.UserService;

/**
 * The {@code BorrowingMapper} class provides utility methods to map between {@code Borrowing} entities
 * and their corresponding DTOs ({@code BorrowingDto}).
 */
public class BorrowingMapper {
    /**
     * Converts a {@code Borrowing} entity to a {@code BorrowingDto}.
     *
     * @param borrowing the {@code Borrowing} entity to be converted
     * @return a {@code BorrowingDto} containing the borrowing's details
     */
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

    /**
     * Converts a {@code BorrowingDto} to a {@code Borrowing} entity.
     *
     * @param dto the {@code BorrowingDto} to be converted
     * @return a {@code Borrowing} entity corresponding to the provided DTO
     */
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
