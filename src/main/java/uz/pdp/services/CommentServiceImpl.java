package uz.pdp.services;

import uz.pdp.entities.Comment;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    List<Comment> comments;
    UserService userService = new UserServiceImpl();

    public CommentServiceImpl() {
        try (
                FileInputStream in = new FileInputStream(commentsPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            Object inputList = input.readObject();
            comments = (List<Comment>) inputList;
        } catch (EOFException e) {
            comments = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in Comment Service Impl");
        }

    }

    public List<Comment> getComments() {
        return comments;
    }


    @Override
    public boolean addComment(Comment comment) {
        try (
                FileOutputStream out = new FileOutputStream(commentsPath);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            comments.add(comment);
            output.writeObject(comments);
//            comments = (List<Comment>) input.readObject();
        } catch (IOException e) {
            System.out.println("Exception has happened in addComment");
            return false;
        }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        return true;
    }


    @Override
    public boolean removeComment(int id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                comments.remove(comment);
                try (
                        FileOutputStream out = new FileOutputStream(commentsPath);
                        ObjectOutput output = new ObjectOutputStream(out)
                ) {
                    comments.add(comment);
                    output.writeObject(comments);
                } catch (IOException e) {
                    System.out.println("Exception has happened in removeComment");
                    return false;
                }
                return true;
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
        if (comments.isEmpty()) {
            System.out.println("No comments yet");
            return;
        }
        for (Comment comment : comments) {
            if (comment.getAd_id() == adId && comment.getParent_comment_id() == 0) {
                System.out.println(comment.getComment_text() + "  " + comment.getTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")) + ", id: " + comment.getId());
                System.out.println("========================  " + userService.getUser(comment.getUser_id()).getNickName());
                displayChildComments(comment);
            }
        }
    }

    private void displayChildComments(Comment comment) {
        var sb = new StringBuilder();
        for (Comment child : comments) {
            if (child.getParent_comment_id() == comment.getId() && child.getParent_comment_id() != 0) {
                sb.append("      ".repeat(child.getLevel_Of_Relation()));
                System.out.println(sb + child.getComment_text() + "  " + child.getTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")) + ", id: " + child.getId());
                System.out.println(sb + "========================  " + userService.getUser(child.getUser_id()).getNickName());
                sb.setLength(0);
                displayChildComments(child);
            }
        }
    }
}
