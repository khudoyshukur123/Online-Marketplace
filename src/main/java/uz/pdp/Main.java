package uz.pdp;

import uz.pdp.entities.Comment;
import uz.pdp.services.CommentService;
import uz.pdp.services.CommentServiceImpl;
import uz.pdp.ui.MainMenu;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        MainMenu.mainMenu();
//        CommentService commentService = new CommentServiceImpl();
//        Comment djf = new Comment("this is comment", 1, 0, 0, LocalDateTime.now());
//        commentService.addComment(djf);
//        commentService.displayComment(1);
    }
}
