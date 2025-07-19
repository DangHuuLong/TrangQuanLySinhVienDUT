package controller;

import dao.UserDAO;
import model.User;

public class AuthController {
    public static User authenticate(String username, String password) {
        User user = UserDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
