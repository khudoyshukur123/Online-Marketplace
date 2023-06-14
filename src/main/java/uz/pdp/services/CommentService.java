package uz.pdp.services;

import uz.pdp.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public interface CommentService {
    List<Comment> comments=new ArrayList<>();
    boolean addComment(Comment comment);
    boolean removeComment(int id);
    Comment getComment(int id);
    void displayComment(int adId);

}
