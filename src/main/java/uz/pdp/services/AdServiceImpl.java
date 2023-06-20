package uz.pdp.services;

import uz.pdp.entities.Ad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdServiceImpl implements AdService {
    List<Ad> adList;

    public AdServiceImpl() {
        try (
                FileInputStream in = new FileInputStream(adListPath);
                ObjectInput input = new ObjectInputStream(in)
        ) {
            Object ads = input.readObject();
            adList = (List<Ad>) ads;
        } catch (EOFException e) {
            adList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ad> getAdList() {
        return adList;
    }

    public boolean addAdvert(Ad advert) {
        if (adList.contains(advert)) return false;
        adList.add(advert);
        try (
                FileOutputStream out = new FileOutputStream(adListPath);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            output.writeObject(adList);
        } catch (IOException e) {
            System.out.println("Exception has happened in addAdvert");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeAdvert(int id) {
        Ad ad = getAd(id);
        if (ad == null) return false;
        try (
                OutputStream out = new FileOutputStream(adListPath);
                ObjectOutput output = new ObjectOutputStream(out)
        ) {
            adList.remove(ad);
            output.writeObject(adList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
