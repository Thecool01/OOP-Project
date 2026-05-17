package oopproject.services;

import oopproject.exceptions.DuplicateUserException;
import oopproject.exceptions.UserNotFoundException;
import oopproject.system.UniversitySystem;
import oopproject.users.User;

public class UserService {
    private final UniversitySystem system;

    public UserService(UniversitySystem system) {
        this.system = system;
    }

    public void addUser(User user) {
        if (user != null && system.findUserById(user.getId()) != null) {
            throw new DuplicateUserException(user.getId());
        }
        system.addUser(user);
    }

    public void removeUser(User user) {
        system.removeUser(user);
    }

    public void updateUser(User user) {
        if (user == null || system.findUserById(user.getId()) == null) {
            throw new UserNotFoundException(user == null ? null : user.getId());
        }
        system.updateUser(user);
    }

    public User findUserById(String id) {
        User user = system.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }
}
