package uz.pdp.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private int id;
    private String comment_text;
    private int ad_id;
    private int countOfLikes;
    private int parent_comment_id;
    private LocalDateTime time;

    public Comment(String comment_text, int ad_id, int countOfLikes,int parent_comment_id,LocalDateTime time){
        this.comment_text = comment_text;
        this.ad_id = ad_id;
        this.countOfLikes = countOfLikes;
        this.parent_comment_id=parent_comment_id;
        this.time=time;
        id++;
    }


}
