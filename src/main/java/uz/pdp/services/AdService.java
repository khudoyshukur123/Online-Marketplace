package uz.pdp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.entities.Ad;
import uz.pdp.utils.LocalDateTimeDeserializer;
import uz.pdp.utils.LocalDateTimeSerializer;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public interface AdService {
    Path adListPath = Path.of("src\\main\\resources\\adDB.json");
    Gson gson = new GsonBuilder().
            setPrettyPrinting().
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    boolean addAdvert(Ad advert);

    List<Ad> getAdList();

    void displayAdvert(int id);

    boolean changeAdvert(int id, Ad changedAdvert);

    boolean incrLike(int idOfAdvert);

    boolean removeAdvert(int id);

    Ad getAd(int idOfAdvert);
}
