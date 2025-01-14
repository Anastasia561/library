package library.mapper;

import library.dto.UserForLibrarianDto;
import library.dto.UserInfoDto;
import library.entity.User;
import library.service.LibrarianService;

public class UserMapper {
    public static UserInfoDto toUserInfoDto(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .borrowings(user.getBorrowings()
                        .stream()
                        .map(BorrowingMapper::toBorrowingDto).toList())
                .build();
    }

    public static UserForLibrarianDto toUserForLibrarianDto(User user) {
        Boolean isLibrarian = (user.getLibrarian() != null);
        return UserForLibrarianDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .borrowings(user.getBorrowings()
                        .stream()
                        .map(BorrowingMapper::toBorrowingDto).toList())
                .isLibrarian(isLibrarian)
                .build();
    }

    public static User toUserFromUserForLibrarianDto(UserForLibrarianDto dto) {
        LibrarianService librarianService = new LibrarianService();

        User user = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .build();

        if (dto.getBorrowings() != null) {
            user.setBorrowings((dto.getBorrowings()
                    .stream()
                    .map(BorrowingMapper::toBorrowing).toList()));
        }
        return user;
    }
}
