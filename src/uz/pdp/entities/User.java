package uz.pdp.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    static int temp;
    private String firstName;
    private String email;
    private String password;
    private int id;

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
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
