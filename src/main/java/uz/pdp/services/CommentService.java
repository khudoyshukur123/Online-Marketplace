package uz.pdp.services;

import uz.pdp.entities.Comment;

import java.util.List;

public interface CommentService {
    String commentsPath = "src\\main\\resources\\commentsDB.txt";

    boolean addComment(Comment comment);

    List<Comment> getComments();

    boolean removeComment(int id);

    Comment getComment(int id);

    void displayComment(int adId);

}
