package uz.pdp.entities;

public class Ad {
    private int id;
    private String description;
    private int category_id;
    private int user_id;
    private int countOfLikes;

    public Ad(String description, int category_id, int user_id, int countOfLikes) {
        this.description = description;
        this.category_id = category_id;
        this.user_id = user_id;
        this.countOfLikes = countOfLikes;
        id++;
    }

    public Ad() {
        id++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCountOfLikes() {
        return countOfLikes;
    }

    public void setCountOfLikes(int countOfLikes) {
        this.countOfLikes = countOfLikes;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", category_id=" + category_id +
                ", user_id=" + user_id +
                ", countOfLikes=" + countOfLikes +
                '}';
    }
}
