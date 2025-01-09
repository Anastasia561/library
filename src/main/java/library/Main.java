package library;

import library.entity.User;
import library.service.UserService;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserService();
        User user = userService.getUserById(1);
        System.out.println(user);
    }
}
