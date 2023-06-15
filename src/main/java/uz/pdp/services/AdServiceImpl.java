package uz.pdp.services;

import uz.pdp.entities.Ad;
import uz.pdp.entities.Category;

import java.io.*;
import java.util.List;

public class AdServiceImpl implements AdService {

    static {
        adList.add(new Ad("Iphone 12", "brand new iphone 12 for sale", Category.GADGETS, 1, 0));
    }


    public AdServiceImpl() {
        try (
                FileInputStream in = new FileInputStream(adListPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            while (true) {
                Object ad = input.readObject();
                if (ad == null) break;
                if (ad instanceof Ad)
                    adList.add((Ad) ad);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean addAdvert(Ad advert) {
        adList.add(advert);
        try (
                FileOutputStream out = new FileOutputStream(adListPath, true);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            output.writeObject(advert);
        } catch (IOException e) {
            System.out.println("Exception has happened");
            return false;
        }
        return true;
    }

    public boolean removeAdvert(int id) {
        Ad ad = getAd(id);
        if (ad == null) return false;
        try (
                OutputStream out = new FileOutputStream(adListPath);
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

    public boolean changeAdver(int id, Ad changedAdvert) {
        removeAdvert(id);
        addAdvert(changedAdvert);
        return true;
    }

    public static List<Ad> getAdList() {
        return adList;
    }

    public boolean increLike(int idOfAdvert) {
        Ad ad = getAd(idOfAdvert);
        if (ad == null) return false;
        ad.setCountOfLikes(ad.getCountOfLikes() + 1);
        return true;
    }

    private Ad getAd(int idOfAdvert) {
        for (Ad ad : adList) {
            if (ad.getId() == idOfAdvert) return ad;
        }
        return null;
    }
}