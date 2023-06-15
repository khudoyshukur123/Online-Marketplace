package uz.pdp.services;

import uz.pdp.entities.Ad;

import java.util.ArrayList;
import java.util.List;

public interface AdService {
    List<Ad> adList = new ArrayList<>();
    String adListPath = "C:\\Users\\user\\OneDrive\\Рабочий стол\\G25\\JAVA Development\\4 module\\Online marketplace\\src\\main\\resources\\adDB.txt";

    boolean addAdvert(Ad advert);
    void displayAdvert(int id);
    boolean changeAdver(int id, Ad changedAdvert);
    boolean increLike(int idOfAdvert);
}
