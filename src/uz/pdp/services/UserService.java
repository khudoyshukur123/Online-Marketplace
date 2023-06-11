package uz.pdp.services;

import uz.pdp.entities.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        for (User value : users) {
            if (value.getEmail().equals(user.getEmail())) {
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
            if (user.getPassword().equals(password) && user.getEmail().equals(phone)) {
                return user;
            }
        }
        return null;
    }

    public void sendEmail(User user) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "mail@gmail.com",
                        "passcode"
                );
            }
        });

        Message message = new MimeMessage(session);

        message.setSubject("the subject");
        message.setText("this is the text");
        message.setFrom(new InternetAddress("dontreply@gmail.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

        Transport.send(message);
        System.out.println("OK");

    }
}