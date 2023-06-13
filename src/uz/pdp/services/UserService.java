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

    public void sendEmail(User user, String passcode) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("khudoshukur7@gmail.com",
                        "somecode");
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setSubject("Online Marketplace");

            String content = "Welcome " + user.getFirstName() + " to our platform!"
                    + "\n We hope that you will find the good bargains here and help other people by offering great deal."
                    + "\n So what are you waiting for? Let's get started by configuring your profile. Click the link to get started"
                    + ". By the way here is your passcode to help you register: " + passcode;
            String html = """
                    <html>
                    <body>
                                        
                    </body>
                    </html>
                    """;
            message.setContent("<html><body><table width=\"100%\" height=\"90%\" border=\"0\" cellspacing=\"0\" cellpadding=\"20\" background=\"https://ttslemc.tatatel.co.in/EMC_FEEDBACK/imagepool/indicom_logo.jpg\"><tr><td><p>this is message</p></td></tr></table></body></html>",
                    "text/html");
            message.setFrom(new InternetAddress("dontreply@gmail.com"));
            String email = user.getEmail();
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}