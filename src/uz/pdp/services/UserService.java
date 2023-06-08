package uz.pdp.services;

import uz.pdp.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    static List<User> users;

    public UserService() {
        users = new ArrayList<>();
        try (
                FileInputStream in = new FileInputStream("src/uz/pdp/db/usersDB.txt");
                ObjectInput input = new ObjectInputStream(in)
        ) {
            while (true) {
                Object user = input.readObject();
                if (user == null) break;
                users.add((User) user);
            }
        } catch (IOException | ClassNotFoundException e) {

        }
    }

    public boolean addUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPhoneNumber().equals(user.getPhoneNumber())) {
                return false;
            }
        }

        try (
                FileOutputStream out = new FileOutputStream("src/uz/pdp/db/usersDB.txt", true);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            users.add(user);
            output.writeObject(user);
        } catch (IOException e) {
            System.out.println("Exception has happened");
            return false;
        }
        return true;
    }

    public boolean removeUser(User user) {
        if (!users.contains(user)) {
            return false;
        }
        users.remove(user);
        try (
                OutputStream out = new FileOutputStream("src/db/usersDB.txt");
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            for (User user1 : users) {
                output.writeObject(user1);
            }
        } catch (IOException e) {

        }
        return true;
    }

    public User getUser(String phone, String password) {
        for (User user : users) {
            if (user.getPassword().equals(password) && user.getPhoneNumber().equals(phone)) {
                return user;
            }
        }
        return null;
    }
}
