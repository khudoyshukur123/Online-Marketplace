package uz.pdp.services;

import uz.pdp.entities.Ad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdService {
    static List<Ad> adList = new ArrayList<>();

    public AdService() {
        try (
                FileInputStream in = new FileInputStream("src/uz/pdp/db/usersDB.txt");
                ObjectInput input = new ObjectInputStream(in)
        ) {
            while (true) {
                Object ad = input.readObject();
                if (ad == null) break;
                adList.add((Ad) ad);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    boolean addAdvert(Ad advert) {
        adList.add(advert);
        try (
                FileOutputStream out = new FileOutputStream("src/uz/pdp/db/adDB.txt", true);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            output.writeObject(advert);
        } catch (IOException e) {
            System.out.println("Exception has happened");
            return false;
        }
        return true;
    }

    boolean removeAdvert(int id) {
        Ad ad = getAd(id);
        if (ad == null) return false;
        try (
                OutputStream out = new FileOutputStream("src/db/adDB.txt");
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            for (Ad advert : adList) {
                output.writeObject(advert);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    void displayAdvert(int id) {
        Ad ad = null;
        for (Ad ad1 : adList) {
            if (id == ad1.getId()) {
                ad = ad1;
                break;
            }
        }
        if (ad == null) System.out.println("No such advertisement");
        System.out.printf("""
                ------------%s------------
                      %s
                      count of likes: %d
                      Category: %s
                      """, ad.getTitle(), ad.getDescription(), ad.getCountOfLikes(), ad.getCategory());
    }

    boolean changeAdver(int id, Ad changedAdvert) {
        removeAdvert(id);
        addAdvert(changedAdvert);
        return true;
    }

    boolean increLike(int idOfAdvert) {
        Ad ad = getAd(idOfAdvert);
        if (ad == null) return false;
        ad.setCountOfLikes(ad.getCountOfLikes() + 1);
        return true;
    }

    private Ad getAd(int idOfAdvert) {
        for (int i = 0; i < adList.size(); i++) {
            if (adList.get(i).getId() == idOfAdvert) return adList.get(i);
        }
        return null;
    }
}
