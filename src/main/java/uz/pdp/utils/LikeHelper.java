package uz.pdp.utils;

import uz.pdp.entities.Ad;
import uz.pdp.services.AdService;
import uz.pdp.services.AdServiceImpl;

import java.util.HashMap;

public class LikeHelper {
    static AdService adService = new AdServiceImpl();
    static HashMap<Integer, Integer> likeHistoryMap;

    static boolean like(int user_id, int ad_id) {
        if (likeHistoryMap.containsKey(user_id))
            return false;
        Ad ad = adService.getAd(ad_id);
        adService.removeAdvert(ad.getId());
        ad.setCountOfLikes(ad.getCountOfLikes() + 1);
        adService.addAdvert(ad);
        return true;
    }

    static void unlike(int user_id, int ad_id) {
        Ad ad = adService.getAd(ad_id);
        adService.removeAdvert(ad.getId());
        ad.setCountOfLikes(ad.getCountOfLikes() - 1);
        adService.addAdvert(ad);
    }

}
