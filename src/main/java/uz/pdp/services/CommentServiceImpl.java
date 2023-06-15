package uz.pdp.services;

import uz.pdp.entities.Comment;

import java.io.*;
import java.time.format.DateTimeFormatter;

public class CommentServiceImpl implements CommentService {
    UserService userService = new UserServiceImpl();

    public CommentServiceImpl() {
        try (
                FileInputStream in = new FileInputStream(commentsPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            while (true) {
                Object comment = input.readObject();
                if (comment == null) break;
                comments.add((Comment) comment);
            }
        } catch (IOException | ClassNotFoundException ignored) {

        }
    }

    @Override
    public boolean addComment(Comment comment) {
        try (
                FileOutputStream out = new FileOutputStream(commentsPath, true);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            comments.add(comment);
            output.writeObject(comment);
        } catch (IOException e) {
            System.out.println("Exception has happened");
            return false;
        }
        return true;
    }


    @Override
    public boolean removeComment(int id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                return comments.remove(comment);
            }
        }
        return false;
    }

    @Override
    public Comment getComment(int id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                return comment;
            }
        }
        return null;
    }

    @Override
    public void displayComment(int adId) {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            if (comment.getAd_id() == adId) {
                System.out.println(comment.getComment_text() + "  " + comment.getTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
                System.out.println("========================  " + userService.getUser(comment.getUser_id()).getNickName());
            }
        }
    }
}
