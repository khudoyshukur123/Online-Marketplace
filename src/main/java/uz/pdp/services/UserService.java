package uz.pdp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.entities.User;

import java.nio.file.Path;
import java.util.List;

public interface UserService {
    String password = "gfafyjwtgqnidfrz";
    String senderEmail = "khudoshukur7@gmail.com";
    String emailPath = "src\\main\\html\\email.html";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    Path usersPath = Path.of("src\\main\\resources\\usersDB.json");

    List<User> getUsers();

    boolean addUser(User user);

    boolean removeUser(User user);

    User getUser(String email, String password);

    User getUser(int idOfUser);

    void sendEmail(User user, String passcode);
}
