package library.controller;

import library.dto.UserForLibrarianDto;
import library.entity.Librarian;
import library.entity.User;
import library.mapper.UserMapper;
import library.service.LibrarianService;
import library.service.UserService;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private final UserService userService;
    private final LibrarianService librarianService;

    public Controller(UserService userService, LibrarianService librarianService) {
        this.userService = userService;
        this.librarianService = librarianService;

    }

    public void createUser(String name, String email, String phoneNumber,
                           String address, Boolean isLibrarian) {
        UserForLibrarianDto dto = UserForLibrarianDto.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .isLibrarian(isLibrarian)
                .build();
        userService.createUser(dto);
    }

    public void createLibrarian(String name, String email, String phoneNumber,
                                String address, LocalDate employmentDate, String position) {
        createUser(name, email, phoneNumber, address, true);

        UserForLibrarianDto dto = userService.getUserByEmailForLibrarian(email);
        User user = UserMapper.toUserFromUserForLibrarianDto(dto);

        Librarian librarian = Librarian.builder()
                .employmentDate(employmentDate)
                .position(position)
                .user(user)
                .build();
        librarianService.createLibrarian(librarian);
    }

    public List<UserForLibrarianDto> getAllUserDto() {
        return userService.getAllForLibrarian();
    }

    public void deleteUserByEmail(String email) {
        UserForLibrarianDto dto = userService.getUserByEmailForLibrarian(email);
        Integer id = dto.getId();
        Boolean isLibrarian = dto.getIsLibrarian();
        if (isLibrarian) {
            throw new RuntimeException("Not allowed to delete librarians");
        }
        userService.deleteUserById(id);
    }
}
