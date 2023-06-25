package uz.pdp.services;

import com.google.gson.reflect.TypeToken;
import uz.pdp.entities.Ad;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AdServiceImpl implements AdService {
    List<Ad> adList;

    public AdServiceImpl() {
        try {
            String json = Files.readString(adListPath);
            if (!json.isBlank()) {
                Type type = new TypeToken<ArrayList<Ad>>() {
                }.getType();
                adList = gson.fromJson(json, type);
            } else adList = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ad> getAdList() {
        return adList;
    }

    public boolean addAdvert(Ad advert) {
        if (adList.contains(advert)) return false;
        adList.add(advert);
        try {
            adList.add(advert);
            String json = gson.toJson(adList);
            Files.writeString(adListPath, json);
        } catch (IOException e) {
            System.out.println("Exception has happened");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAdvert(int id) {
        Ad ad = getAd(id);
        if (ad == null) return false;
        try {
            String json = gson.toJson(adList);
            Files.writeString(adListPath, json);
        } catch (IOException e) {
            System.out.println("Exception in User AdService Impl remove user");
        }
        return true;
    }

    @Override
    public void displayAdvert(int id) {
        Ad ad = null;
        for (Ad ad1 : adList) {
            if (id == ad1.getId()) {
                ad = ad1;
                break;
            }
        }
        if (ad == null) {
            System.out.println("No such advertisement");
            return;
        }
        System.out.printf("""
                ------------%s------------
                      %s
                      count of likes: %d
                      Category: %s
                      """, ad.getTitle(), ad.getDescription(), ad.getCountOfLikes(), ad.getCategory());
    }

    @Override
    public boolean changeAdvert(int id, Ad changedAdvert) {
        removeAdvert(id);
        addAdvert(changedAdvert);
        return true;
    }

    @Override
    public boolean incrLike(int idOfAdvert) {
        Ad ad = getAd(idOfAdvert);
        if (ad == null) return false;
        ad.setCountOfLikes(ad.getCountOfLikes() + 1);
        return true;
    }

    public Ad getAd(int idOfAdvert) {
        for (Ad ad : adList) {
            if (ad.getId() == idOfAdvert) return ad;
        }
        return null;
    }
}
