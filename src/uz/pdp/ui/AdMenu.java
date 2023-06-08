package uz.pdp.ui;

import uz.pdp.entities.User;

public class AdMenu {
    static User user;
    public static void main(String[] args) {
        System.out.println("Welcome to your account, "+user.getFirstName());
        System.out.println("Categories to explore: ");
        System.out.println("""
                1.Home supplies
                2.Cars
                """);
    }
}
