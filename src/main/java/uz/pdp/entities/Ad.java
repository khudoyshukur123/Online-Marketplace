package uz.pdp.entities;

import com.google.gson.reflect.TypeToken;
import lombok.Data;
import uz.pdp.services.AdService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
public class Ad {
    private static int temp;

    static {
        try {
            String json = Files.readString(AdService.adListPath);
            if (!json.isBlank()) {
                Type type = new TypeToken<ArrayList<Ad>>() {
                }.getType();
                List<Ad> ads = AdService.gson.fromJson(json, type);
                temp = ads.get(ads.size() - 1).getId();
            }
        } catch (IOException e) {
            System.out.println("Exception in Comment static method");
        }
    }

    private int id;
    private String title;
    private String description;
    private Category category;
    private int user_id;
    private double price;
    private int countOfLikes;

    public Ad(String title, String description, Category category, int user_id, int countOfLikes, double price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.user_id = user_id;
        this.countOfLikes = countOfLikes;
        this.price = price;
        id = ++temp;
    }

    public Ad() {
        this.id = ++temp;
    }
}
