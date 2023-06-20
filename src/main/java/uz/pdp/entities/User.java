package uz.pdp.entities;

import lombok.Data;
import uz.pdp.services.UserService;

import java.io.*;
import java.util.List;

@Data
public class User implements Serializable {

    static int temp;
    private String firstName;
    private String nickName;
    private String email;
    private String password;
    private int id;

    static {
        try (
                InputStream in = new FileInputStream(UserService.usersPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            List<User> users = (List<User>) input.readObject();
            temp = users.get(users.size() - 1).getId();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in User static method");
        }

    }

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
