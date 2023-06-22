package uz.pdp.ui;

import uz.pdp.entities.Ad;
import uz.pdp.entities.Category;
import uz.pdp.entities.Comment;
import uz.pdp.entities.User;
import uz.pdp.services.AdService;
import uz.pdp.services.AdServiceImpl;
import uz.pdp.services.CommentService;
import uz.pdp.services.CommentServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AdMenu {
    static User currentUser;
    static Scanner scanner = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);
    static AdService adService = new AdServiceImpl();
    static CommentService commentService = new CommentServiceImpl();
    public static void menu(User user) {
        currentUser = user;
        while (true) {
            System.out.println("Categories to explore: ");
            System.out.println("""
                    1.Gadgets
                    2.Cosmetics
                    3.Clothes
                    4.Back to Main menu
                    """);
            switch (scanner.nextLine()) {
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
        List<Ad> adList = adService.getAdList();
        for (Ad ad : adList) {
            if (ad.getCategory() == category) {
                System.out.println(ad.getTitle() + " ID: " + ad.getId());
            }
        }
        System.out.print("Enter the id of the advert that you like to explore: ");
        int id = scannerInt.nextInt();
        if (adService.getAd(id) != null && adService.getAd(id).getCategory() == category)
            adService.displayAdvert(id);
        while (true) {
            System.out.print("""
                    Enter the  command to proceed:
                    1.Comment Section
                    2.Like the ad!
                    3.Back
                    """);
            switch (scanner.nextLine()) {
                case "1" -> comment(id);
                case "2" -> adService.incrLike(id);
                case "3" -> {
                    return;
                }
                default -> System.out.println("Wrong command");
            }
        }
    }

    private static void comment(int adId) {
        commentService.displayComment(adId);
        while (true) {
            System.out.print("""
                    Enter the  command to proceed:
                    1.Add comment
                    2.Reply to a comment
                    3.Back
                    """);
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.print("Write your comment: ");
                    String comment_text = scanner.nextLine();
                    commentService.addComment(new Comment(comment_text, adId, 0, 0, LocalDateTime.now(), currentUser.getId()));
                }
                case "2" -> {
                    System.out.print("which comment you would like to reply (id): ");
                    int idOfParentComment = scannerInt.nextInt();
                    System.out.print("Write your comment: ");
                    String comment_text = scanner.nextLine();
                    Comment newComment = new Comment(comment_text, adId, 0, idOfParentComment, LocalDateTime.now(), currentUser.getId());
                    newComment.setLevel_Of_Relation(commentService.getComment(idOfParentComment).getLevel_Of_Relation() + 1);
                    commentService.addComment(newComment);
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("Wrong command");
            }
        }
    }
}
