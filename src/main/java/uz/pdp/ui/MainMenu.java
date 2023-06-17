package uz.pdp.ui;

import uz.pdp.Main;
import uz.pdp.entities.Ad;
import uz.pdp.entities.User;
import uz.pdp.services.AdService;
import uz.pdp.services.AdServiceImpl;
import uz.pdp.services.UserService;
import uz.pdp.services.UserServiceImpl;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    static User currentUser;
    static Scanner scanner = new Scanner(System.in);
    static AdService adService = new AdServiceImpl();
    static UserService userService = new UserServiceImpl();
    static int chances = 3;

    public static void menu(User user) {
        currentUser = user;
        System.out.println("Welcome to your account, " + currentUser.getFirstName());
        while (true) {
            System.out.println("""
                    1.Advertisements Catalogue
                    2.Managing your advertisements
                    3.Settings
                    4.Log out
                    """);
            switch (scanner.next()) {
                case "1" -> AdMenu.menu(currentUser);
                case "2" -> managementOfAds();
                case "3" -> settings();
                case "4" -> {
                    return;
                }
                default -> System.out.println("This is not valid command");
            }
        }
    }

    private static void settings() {
        System.out.println("""
                1.Changing your name
                2.Changing your password
                3.Changing your email
                4.Your comments
                5.Back
                """);
        switch (scanner.next()) {
            case "1" -> changeName();
            case "2" -> changePassword();
            case "3" -> changeEmail();
            case "4" -> commentSection();
            case "5" -> {
                return;
            }
            default -> System.out.println("This is not valid command");
        }
    }

    private static void changeEmail() {
        String oldEmail;
        System.out.println("Are you sure you want to change your email: ");
        System.out.print("Y/N: ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("Y")) {
            oldEmail = currentUser.getEmail();
            System.out.print("Enter your new email address: ");
            String newEmail = scanner.nextLine();
            Pattern pattern = Pattern.compile("^(\\w{3,})@([\\w-]{2,})\\.(\\w){2,6}$");
            if (!pattern.matcher(newEmail).matches()) {
                System.out.println("Wrong email");
                return;
            }
            currentUser.setEmail(newEmail);
            Random random = new Random();
            String passcode = String.valueOf(random.nextInt(9999) + 1000);
            userService.sendEmail(currentUser, passcode);
            System.out.println("We have sent you email pls check");
            System.out.print("Please enter the code that is sent to your email: ");
            String input = scanner.nextLine();
            if (passcode.equals(input))
                System.out.println("You have successfully changed you email to " + newEmail);
            else {
                currentUser.setEmail(oldEmail);
                System.out.println("error");
            }
        }
    }

    private static void changePassword() {
        String oldPass;
        System.out.println("Are you sure you want to change your password: ");
        System.out.print("Y/N: ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("Y")) {
            System.out.print("Enter your current password: ");
            String temp = scanner.nextLine();
            if (!temp.equals(currentUser.getPassword())) {
                chances--;
                if (chances == 0) Main.stopApp("You have tried more than given chances");
                System.out.println("Wrong password: You have got " + chances + "to try");
            }
            System.out.print("Enter your new Password: ");
            String newPassword = scanner.nextLine();
            Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*<>])(?=.*[a-z]).{8,}$");
            if (pattern.matcher(newPassword).matches()) {
                currentUser.setPassword(newPassword);
                System.out.println("You have successfully changed you Password to " + newPassword);
                return;
            }
            System.out.println("Something went wrong. Try again.");
        }
    }


    private static void changeName() {
        System.out.println("Are you sure you want to change your NickName: ");
        System.out.print("Y/N: ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("Y")) {
            System.out.print("Enter your new NickName: ");
            String name = scanner.nextLine();
            if (name.isBlank()) {
                currentUser.setNickName(name);
                System.out.println("You have successfully changed your NickName to " + name);
            }
        }
    }

    private static void commentSection() {

    }

    private static void managementOfAds() {
        for (Ad ad : AdServiceImpl.getAdList()) {
            if (ad.getUser_id() == currentUser.getId())
                adService.displayAdvert(ad.getId());
        }
    }
}
