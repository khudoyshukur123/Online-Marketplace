package uz.pdp.entities;

import com.github.javafaker.Cat;
import lombok.Data;

@Data
public class Ad {
    static int temp;
    private int id;
    private String title;
    private String description;
    private Category category;
    private int user_id;
    private int countOfLikes;

    public Ad(String title,String description, Category category, int user_id, int countOfLikes) {
        this.title=title;
        this.description = description;
        this.category = category;
        this.user_id = user_id;
        this.countOfLikes = countOfLikes;
        temp++;
        id=temp;
    }

    public Ad() {
        temp++;
        id=temp;
    }
}
