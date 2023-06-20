package uz.pdp.entities;

import lombok.Data;
import uz.pdp.services.CommentService;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = -7365022467349960924L;
    static int temp = 0;

    static {
        try (
                InputStream in = new FileInputStream(CommentService.commentsPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            List<Comment> comments = (List<Comment>) input.readObject();
            temp = comments.get(comments.size() - 1).getId();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in Comment static method");
        }

    }

    private int id;
    private String comment_text;
    private int ad_id;
    private int user_id;
    private int countOfLikes;
    private int parent_comment_id;
    private LocalDateTime time;
    private int level_Of_Relation;

    public Comment(String comment_text, int ad_id, int countOfLikes, int parent_comment_id, LocalDateTime time, int user_id) {
        this.comment_text = comment_text;
        this.ad_id = ad_id;
        this.countOfLikes = countOfLikes;
        this.parent_comment_id = parent_comment_id;
        this.time = time;
        this.user_id = user_id;
        temp++;
        id = temp;
    }
}
