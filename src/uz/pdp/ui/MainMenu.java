package uz.pdp.ui;

import uz.pdp.entities.User;
import uz.pdp.services.UserService;

import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    static Scanner scanner;
    static UserService userService = new UserService();

    public static void main(String[] args) {
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
        String phone, password;
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
            System.out.print("Enter your phone number: ");
            phone = scanner.next();
            pattern = Pattern.compile("^(?=.*\\+998)(?=.*9[34590])(?=.*\\d).{13}");
            if (!pattern.matcher(phone).matches()) {
                System.out.println("Wrong phone number");
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
        User user = userService.getUser(phone, password);
        if (user != null) {
            System.out.println("You are found in db");
            AdMenu.menu(user);
        }
    }

    private static void register() {
        String name, phone, password;
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("Enter your name: ");
        name = scanner1.nextLine();
        if (name.isBlank()) {
            System.out.println("Name cannot be blank");
            return;
        }
        Pattern pattern;
        while (true) {
            System.out.print("Enter your phone number: ");
            phone = scanner.next();
            pattern = Pattern.compile("^(?=.*\\+998)(?=.*9[34590])(?=.*\\d).{13}");
            if (!pattern.matcher(phone).matches()) {
                System.out.println("Wrong phone number");
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
        User user = new User(name, phone, password);
        if (userService.addUser(user)) {
            System.out.println("You have successfully registered");
            AdMenu.menu(user);
        }
        else System.out.println("error");
    }

}

