package uz.pdp.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import uz.pdp.services.UserService;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
public class User implements Serializable {

    private static int temp;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        try {
            String json = Files.readString(UserService.usersPath);
            if (!json.isBlank()) {
                Type type = new TypeToken<ArrayList<User>>() {
                }.getType();
                List<User> users = User.gson.fromJson(json, type);
                temp = users.get(users.size() - 1).getId();
            }
        } catch (IOException e) {
            System.out.println("Exception in Comment static method");
        }
    }

    private String firstName;
    private String nickName;
    private String email;
    private String password;
    private int id;

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.nickName = firstName;
        temp++;
        id = temp;
    }

    public User() {
        temp++;
        id = temp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            return user.email.equals(this.email) && user.password.equals(this.password);
        }
        return false;
    }
}
