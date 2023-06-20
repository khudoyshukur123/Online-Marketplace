package uz.pdp.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Ad implements Serializable {
    static int temp;
    private int id;
    private String title;
    private String description;
    private Category category;
    private int user_id;
    private int countOfLikes;

    public Ad(String title, String description, Category category, int user_id, int countOfLikes) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.user_id = user_id;
        this.countOfLikes = countOfLikes;
        temp++;
        id = temp;
    }
}
