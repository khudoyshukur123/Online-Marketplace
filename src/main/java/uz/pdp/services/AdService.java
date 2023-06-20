package uz.pdp.services;

import uz.pdp.entities.Ad;

import java.util.List;

public interface AdService {
    String adListPath = "src\\main\\resources\\adDB.txt";

    boolean addAdvert(Ad advert);

    List<Ad> getAdList();

    void displayAdvert(int id);

    boolean changeAdvert(int id, Ad changedAdvert);

    boolean incrLike(int idOfAdvert);

    boolean removeAdvert(int id);

    Ad getAd(int idOfAdvert);
}
