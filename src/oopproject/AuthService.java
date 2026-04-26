package oopproject;

import oopproject.storage.DataStorage;
import oopproject.users.User;

public class AuthService {
    public static User authenticate(String login, String password) {
        User user = DataStorage.getInstance().findUserByLogin(login);
        
        if (user != null && user.getPassword().equals(password)) {
            DataStorage.getInstance().addLog("User '" + login + "' successfully authenticated.");
            return user;
        }
        
        DataStorage.getInstance().addLog("Failed login attempt for user: " + login);
        return null;
    }
}