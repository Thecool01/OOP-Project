package oopproject.factory;

import oopproject.enums.ManagerType;
import oopproject.enums.TeacherTitle;
import oopproject.enums.UserRole;
import oopproject.users.Admin;
import oopproject.users.Manager;
import oopproject.users.Student;
import oopproject.users.Teacher;
import oopproject.users.User;

import java.util.Date;

public class UserFactory {
    public User createUser(UserRole role, String id, String username, String password, String fullName) {
        String[] names = splitName(fullName);
        return switch (role) {
            case STUDENT -> new Student(id, username, password, names[0], names[1], 1, 0, 0, "");
            case TEACHER -> new Teacher(id, username, password, names[0], names[1],
                    0, new Date(), TeacherTitle.TUTOR);
            case MANAGER -> new Manager(id, username, password, names[0], names[1],
                    0, new Date(), ManagerType.OR);
            case ADMIN -> new Admin(id, username, password, names[0], names[1], 0, new Date());
        };
    }

    private String[] splitName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return new String[]{"", ""};
        }
        String[] parts = fullName.split(" ", 2);
        return parts.length == 1 ? new String[]{parts[0], ""} : parts;
    }
}
