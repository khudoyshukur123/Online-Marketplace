package uz.pdp.ui;

import uz.pdp.entities.User;
import uz.pdp.services.UserService;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    static Scanner scanner;
    static UserService userService = new UserService();

    public static void mainMenu() {
        while (true) {
            scanner = new Scanner(System.in);
            System.out.print("""
                    1.Register
                    2.Login
                    3.Exit
                    """);
            switch (scanner.next()) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
                    return;
                }
                default -> System.out.println("This is not valid command");
            }
        }
    }

    private static void login() {
        String email, password;
        Scanner scanner1 = new Scanner(System.in);
        scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner1.nextLine();
        if (name.isBlank()) {
            System.out.println("Name cannot be blank");
            return;
        }
        Pattern pattern;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.next();
            pattern = Pattern.compile("^(\\w{3,})@([\\w-]{2,})\\.(\\w){2,6}$");
            if (!pattern.matcher(email).matches()) {
                System.out.println("Wrong email");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.next();
            pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*<>])(?=.*[a-z]).{8,}$");
            if (!pattern.matcher(password).matches()) {
                System.out.println("Enter strong password");
                continue;
            }
            break;
        }
        User user = userService.getUser(email, password);
        if (user != null) {
            System.out.println("You are found in db");
            AdMenu.menu(user);
        }
    }

    private static void register() {
        String name, email, password;
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("Enter your name: ");
        name = scanner1.nextLine();
        if (name.isBlank()) {
            System.out.println("Name cannot be blank");
            return;
        }
        Pattern pattern;
        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.next();
            pattern = Pattern.compile("^(\\w{3,})@([\\w-]{2,})\\.(\\w){2,6}$");
            if (!pattern.matcher(email).matches()) {
                System.out.println("Wrong email");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.next();
            pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*<>])(?=.*[a-z]).{8,}$");
            if (!pattern.matcher(password).matches()) {
                System.out.println("Enter strong password");
                continue;
            }
            break;
        }
        User user = new User(name, email, password);
        if (userService.addUser(user)) {
            System.out.println("We have sent you email pls check");
            Random random = new Random();
            String passcode = String.valueOf(random.nextInt(9999) + 1000);
            new Thread(() -> {
                try {
                    System.out.println("Sending message to the email...");
                    userService.sendEmail(user, passcode);
                } catch (Exception e) {
                    System.out.println("Something went wrong!");
                }
            }).start();
            System.out.print("Please enter the code that is sent to your email: ");
            String passcodeIn = scanner1.nextLine();
            if (passcodeIn.equals(passcode)) {
                System.out.println("You have successfully registered and authentificated!!!");
                AdMenu.menu(user);
            }
        } else System.out.println("error");
    }

}

