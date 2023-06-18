package uz.pdp.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Comment implements Serializable {
    static int temp=0;
    private int id;
    private String comment_text;
    private int ad_id;
    private int user_id;
    private int countOfLikes;
    private int parent_comment_id;
    private LocalDateTime time;

    public Comment(String comment_text, int ad_id, int countOfLikes, int parent_comment_id, LocalDateTime time, int user_id) {
        this.comment_text = comment_text;
        this.ad_id = ad_id;
        this.countOfLikes = countOfLikes;
        this.parent_comment_id = parent_comment_id;
        this.time = time;
        this.user_id = user_id;
        temp++;
        id=temp;
    }
}
