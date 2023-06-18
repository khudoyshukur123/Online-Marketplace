package uz.pdp.services;

import uz.pdp.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    List<User> users = new ArrayList<>();
    String password = "password";
    String senderEmail = "khudoshukur7@gmail.com";
    String emailPath = "C:\\Users\\user\\OneDrive\\Рабочий стол\\G25\\JAVA Development\\4 module\\Online marketplace\\src\\main\\html\\email.html";

    String usersPath = "C:\\Users\\user\\OneDrive\\Рабочий стол\\G25\\JAVA Development\\4 module\\Online marketplace\\src\\main\\resources\\usersDB.txt";

    boolean addUser(User user);

    boolean removeUser(User user);

    User getUser(String phone, String password);

    User getUser(int idOfUser);

    void sendEmail(User user, String passcode);
}
