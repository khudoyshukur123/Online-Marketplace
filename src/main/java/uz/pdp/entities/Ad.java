package uz.pdp.entities;

import lombok.Data;
import uz.pdp.services.AdService;

import java.io.*;
import java.util.List;

@Data
public class Ad implements Serializable {
    static int temp;
    private int id;
    private String title;
    private String description;
    private Category category;
    private int user_id;
    private int countOfLikes;

    static {
        try (
                InputStream in = new FileInputStream(AdService.adListPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            List<Ad> ads = (List<Ad>) input.readObject();
            temp = ads.get(ads.size() - 1).getId();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in Ad static method");
        }

    }

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
