package uz.pdp.ui;

import uz.pdp.entities.Ad;
import uz.pdp.entities.Category;
import uz.pdp.entities.User;
import uz.pdp.services.AdService;

import java.util.List;
import java.util.Scanner;

public class AdMenu {
    static User currentUser;
    static Scanner scanner = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);
    static AdService adService=new AdService();

    public static void menu(User user){
        currentUser=user;
        System.out.println("Welcome to your account, " + currentUser.getFirstName());
        while (true) {
            System.out.println("Categories to explore: ");
            System.out.println("""
                    1.Gadgets
                    2.Cosmetics
                    3.Clothes
                    4.Back to Main menu
                    """);
            switch (scanner.next()) {
                case "1" -> printAds(Category.GADGETS);
                case "2" -> printAds(Category.COSMETICS);
                case "3" -> printAds(Category.CLOTHES);
                case "4" -> {
                    return;
                }
            }
        }
    }

    private static void printAds(Category category) {
        List<Ad> adList = AdService.getAdList();
        for (Ad ad : adList) {
            if (ad.getCategory()==category){
                System.out.println(ad.getTitle()+"ID: "+ad.getId());
            }
        }
        System.out.print("Enter the id of the advert that you like to explore: ");
        int id = scannerInt.nextInt();
        adService.displayAdvert(id);
    }
}
