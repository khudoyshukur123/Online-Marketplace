package uz.pdp.services;

import com.google.gson.reflect.TypeToken;
import uz.pdp.entities.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UserServiceImpl implements UserService {
    private List<User> users;

    public UserServiceImpl() {
        try {
            String userInput = Files.readString(usersPath);
            if (!userInput.isBlank()) {
                Type targetClassType = new TypeToken<ArrayList<User>>() {
                }.getType();
                users = gson.fromJson(userInput, targetClassType);
            } else users = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Exception in User Service Impl constructor");
        }
    }

    @Override
    public User getUser(int idOfUser) {
        for (User user : users) {
            if (user.getId() == idOfUser) return user;
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean addUser(User user) {
        for (User value : users) {
            if (value.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        try {
            users.add(user);
            String json = gson.toJson(users);
            Files.writeString(usersPath, json);
        } catch (IOException e) {
            System.out.println("Exception has happened");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeUser(User user) {
        if (!users.contains(user)) {
            return false;
        }
        users.remove(user);
        try {
            String json = gson.toJson(users);
            Files.writeString(usersPath, json);
        } catch (IOException e) {
            System.out.println("Exception in User Service Impl remove user");
        }
        return true;
    }

    @Override
    public User getUser(String email, String password) {
        for (User user : users) {
            if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void sendEmail(User user, String passcode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,
                        password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setSubject("Online Marketplace");
            ArrayList<String> emailBody = (ArrayList<String>) Files.readAllLines(Path.of(emailPath));
            var sb = new StringBuilder();
            for (String s : emailBody) {
                if (s.contains("%s")) sb.append(s.formatted(String.valueOf(passcode))).append("\n");
                else sb.append(s).append("\n");
            }
            message.setContent(sb.toString(), "text/html");
            message.setFrom(new InternetAddress("dontreply@gmail.com"));
            String email = user.getEmail();
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}