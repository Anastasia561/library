package library.mapper;

import library.dto.UserForLibrarianDto;
import library.dto.UserInfoDto;
import library.entity.User;

/**
 * The {@code UserMapper} class provides utility methods to map between {@code User} entities
 * and their corresponding DTOs ({@code UserInfoDto} and {@code UserForLibrarianDto}).
 */
public class UserMapper {
    /**
     * Converts a {@code User} entity to a {@code UserInfoDto}.
     *
     * @param user the {@code User} entity to be converted
     * @return a {@code UserInfoDto} containing the user's basic details
     */
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

    /**
     * Converts a {@code User} entity to a {@code UserForLibrarianDto}.
     *
     * @param user the {@code User} entity to be converted
     * @return a {@code UserForLibrarianDto} containing the user's details for librarian views
     */
    public static UserForLibrarianDto toUserForLibrarianDto(User user) {
        Boolean isLibrarian = (user.getLibrarian() != null);
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
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
        return dto;
    }

    /**
     * Converts a {@code UserForLibrarianDto} to a {@code User} entity.
     *
     * @param dto               the {@code UserForLibrarianDto} to be converted
     * @param includeBorrowings a flag indicating whether borrowings should be included
     * @return a {@code User} entity corresponding to the provided DTO
     */
    public static User toUserFromUserForLibrarianDto(UserForLibrarianDto dto, boolean includeBorrowings) {
        User user = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .build();

        if (includeBorrowings && dto.getBorrowings() != null) {
            user.setBorrowings(dto.getBorrowings()
                    .stream()
                    .map(BorrowingMapper::toBorrowing)
                    .toList());
        }
        return user;
    }
}
