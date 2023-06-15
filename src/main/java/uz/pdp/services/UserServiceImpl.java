package uz.pdp.services;

import uz.pdp.entities.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;


public class UserServiceImpl implements UserService {
    public UserServiceImpl() {
        try (
                FileInputStream in = new FileInputStream(usersPath);
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

    @Override
    public User getUser(int idOfUser) {
        for (User user : users) {
            if (user.getId() == idOfUser) return user;
        }
        return null;
    }

    public boolean addUser(User user) {
        for (User value : users) {
            if (value.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        try (
                FileOutputStream out = new FileOutputStream(usersPath, true);
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
                OutputStream out = new FileOutputStream(usersPath);
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

    public void sendEmail(User user, String passcode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("khudoshukur7@gmail.com",
                        "password");
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setSubject("Online Marketplace");
            String html1 = """
                    <!DOCTYPE html>
                    <html lang="en">                \s
                    <head>
                        <meta charset="UTF-8">
                        <meta http-equiv="X-UA-Compatible" content="IE=edge">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Document</title>
                        <style>
                            #parg {
                                background-color: lightblue;
                                color: black;
                                padding: 40px;
                                text-align: center;
                            }
                            #pass{
                                font-size: 30px;
                            }
                        </style>
                    </head>
                                        
                    <body>
                            <p id="parg">Verify your new Online Marketplace account</p>
                            <p>To verify your email address, please use the following One Time Password (OTP):</p>
                            <p id="pass">""";
            String html2 = """
                            </p>
                            <p>
                                Do not share this OTP with anyone. Online Marketplace takes your account security very seriously. Online Marketplace Customer
                                Service will never<br>
                                ask you to disclose or verify your Online Marketplace password, OTP, credit card, or banking account number. If you
                                receive a<br>
                                suspicious email with a link to update your account information, do not click on the link—instead, report
                                the email to<br>
                                Online Marketplace for investigation.     
                                <b>Thank you!</b>
                                <table width="100%" height="100" cellspacing="0" cellpadding="20"
                            background="https://media.istockphoto.com/id/1331579485/vector/background-curved-light-blue.jpg?s=612x612&w=0&k=20&c=Wslr-PIxcQDoxXmzC7_w8rbFM9_s_5Jz99tE3ftNM1A="
                            ></table>
                            </p>
                    </body>          
                    </html>""";
            message.setContent(html1 + passcode + html2, "text/html");
            message.setFrom(new InternetAddress("dontreply@gmail.com"));
            String email = user.getEmail();
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}