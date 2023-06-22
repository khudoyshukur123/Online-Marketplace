package uz.pdp.services;

import uz.pdp.entities.User;

import java.util.List;

public interface UserService {
    String password = "password";
    String senderEmail = "khudoshukur7@gmail.com";
    String emailPath = "src\\main\\html\\email.html";

    String usersPath = "src\\main\\resources\\usersDB.txt";

    List<User> getUsers();

    boolean addUser(User user);

    boolean removeUser(User user);

    User getUser(String email, String password);

    User getUser(int idOfUser);

    void sendEmail(User user, String passcode);
}
