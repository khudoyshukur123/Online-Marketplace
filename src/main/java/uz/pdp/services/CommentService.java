package uz.pdp.services;

import uz.pdp.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public interface CommentService {
    String commentsPath = "C:\\Users\\user\\OneDrive\\Рабочий стол\\G25\\JAVA Development\\4 module\\Online marketplace\\src\\main\\resources\\commentsDB.txt";
    boolean addComment(Comment comment);
    List<Comment> getComments();

    boolean removeComment(int id);

    Comment getComment(int id);

    void displayComment(int adId);

}
