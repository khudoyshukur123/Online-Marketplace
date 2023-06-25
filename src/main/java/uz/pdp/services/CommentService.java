package uz.pdp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.entities.Comment;
import uz.pdp.utils.LocalDateTimeDeserializer;
import uz.pdp.utils.LocalDateTimeSerializer;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    Path commentsPath = Path.of("src\\main\\resources\\commentsDB.json");
    Gson gson = new GsonBuilder().
            registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .setPrettyPrinting()
            .create();

    boolean addComment(Comment comment);

    List<Comment> getComments();

    boolean removeComment(int id);

    Comment getComment(int id);

    void displayComment(int adId);

}
