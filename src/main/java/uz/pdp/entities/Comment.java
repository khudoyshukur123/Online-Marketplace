package uz.pdp.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import uz.pdp.services.CommentService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Comment {
    static int temp = 0;
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {

        try {
            String json = Files.readString(CommentService.commentsPath);
            if (!json.isBlank()) {
                Type type = new TypeToken<ArrayList<Comment>>() {
                }.getType();
                List<Comment> comments = Comment.gson.fromJson(json, type);
                temp = comments.get(comments.size() - 1).getId();
            }
        } catch (IOException e) {
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
