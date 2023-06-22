package uz.pdp.ui;

import uz.pdp.Main;
import uz.pdp.entities.Ad;
import uz.pdp.entities.Category;
import uz.pdp.entities.Comment;
import uz.pdp.entities.User;
import uz.pdp.services.*;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    static User currentUser;
    static Scanner scanner = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);
    static AdService adService = new AdServiceImpl();
    static UserService userService = new UserServiceImpl();
    static CommentService commentService = new CommentServiceImpl();
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
            switch (scannerInt.next()) {
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
        while (true) {
            System.out.println("""
                    1.Changing your name
                    2.Changing your password
                    3.Changing your email
                    4.Your comments
                    5.Back
                    """);
            switch (scannerInt.next()) {
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
            String passcode = String.valueOf(random.nextInt(8999) + 1000);
            userService.sendEmail(currentUser, passcode);
            System.out.println("We have sent you email pls check");
            System.out.print("Please enter the code that is sent to your email: ");
            String input = scanner.nextLine();
            if (passcode.equals(input)) {
                currentUser.setEmail(oldEmail);
                userService.removeUser(currentUser);
                currentUser.setEmail(newEmail);
                userService.addUser(currentUser);
                System.out.println("You have successfully changed you email to " + newEmail);
            } else {
                currentUser.setEmail(oldEmail);
                System.out.println("error");
            }
        }
    }

    private static void changePassword() {
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
                userService.removeUser(currentUser);
                currentUser.setPassword(newPassword);
                userService.addUser(currentUser);
                System.out.println("You have successfully changed you Password to " + newPassword);
            } else {
                System.out.println("Something went wrong. Try again.");
            }
        }
    }


    private static void changeName() {
        System.out.println("Are you sure you want to change your NickName: ");
        System.out.print("Y/N: ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("Y")) {
            System.out.print("Enter your new NickName: ");
            String name = scanner.nextLine();
            if (!name.isBlank()) {
                userService.removeUser(currentUser);
                currentUser.setNickName(name);
                userService.addUser(currentUser);
                System.out.println("You have successfully changed your NickName to " + name);
                System.out.println(currentUser);
            } else {
                System.out.println("Something went wrong");
            }
        }
    }

    private static void commentSection() {
        System.out.println("Your comments: ");
        for (Comment comment : commentService.getComments()) {
            if (comment.getUser_id() == currentUser.getId()) {
                System.out.println(comment);
            }
        }
    }

    private static void managementOfAds() {
        boolean hasAds = false;
        for (Ad ad : adService.getAdList()) {
            if (ad.getUser_id() == currentUser.getId()) {
                adService.displayAdvert(ad.getId());
                hasAds = true;
            }
        }
        if (!hasAds) {
            System.out.println("You have no any advertisements published.");
            System.out.print("Do you want to add advertisement ? (Y/N): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) addAdvert();
            else return;
        }
        while (true) {
            System.out.println("""
                    Please choose one of them
                    1.Adding another advertisement to the site
                    2.Removing one of your advertisement from the site
                    3. Editing your ad
                    4.Exit
                    """);
            switch (scannerInt.next()) {
                case "1" -> addAdvert();
                case "2" -> removeAdvert();
                case "3" -> editAd();
                case "4" -> {
                    return;
                }
                default -> System.out.println("This is not valid command");
            }
        }
    }

    private static void editAd() {
        System.out.println("Which add you want to remove: ");
    }

    private static void removeAdvert() {
        System.out.print("Enter the id of the advertisement that you want to remove: ");
        int id = scannerInt.nextInt();
        if (adService.getAd(id).getUser_id() == currentUser.getId()) {
            adService.removeAdvert(id);
            System.out.println("You have successfully deleted your ad");
        } else System.out.println("Something went wrong!");
    }

    private static void addAdvert() {
        String title, description;
        Category category;
        while (true) {
            System.out.print("Enter your title for the ad (it should not exceed 50 chars): ");
            title = scanner.nextLine();
            if (title.toCharArray().length > 50) {
                System.out.println("Your title should not be more than 50 characters!!!");
                continue;
            }
            if (title.isBlank()) {
                System.out.println("Title should not be blank");
            }
            break;
        }
        System.out.print("Enter your description for the ad: ");
        description = scanner.nextLine();
        System.out.println("""
                Enter the category of the good:
                1.Gadgets
                2.Cosmetics
                3.Clothes
                """);
        switch (scannerInt.nextInt()) {
            case 1 -> category = Category.GADGETS;
            case 2 -> category = Category.COSMETICS;
            case 3 -> category = Category.CLOTHES;
            default -> {
                System.out.println("Something went wrong!");
                return;
            }
        }
        adService.addAdvert(new Ad(title, description, category, currentUser.getId(), 0, 8));
        System.out.println("You have successfully added new ad to the site!!!");
    }
}
