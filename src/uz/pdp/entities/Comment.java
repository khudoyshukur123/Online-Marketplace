package uz.pdp.entities;

public class Comment {
    private int id;
    private String comment_text;
    private int ad_id;
    private int countOfLikes;
    private int parent_comment_id;

    public Comment(String comment_text, int ad_id, int countOfLikes,int parent_comment_id ){
        this.comment_text = comment_text;
        this.ad_id = ad_id;
        this.countOfLikes = countOfLikes;
        this.parent_comment_id=parent_comment_id;
        id++;
    }

    public Comment() {
        id++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public int getAd_id() {
        return ad_id;
    }

    public void setAd_id(int ad_id) {
        this.ad_id = ad_id;
    }

    public int getCountOfLikes() {
        return countOfLikes;
    }

    public void setCountOfLikes(int countOfLikes) {
        this.countOfLikes = countOfLikes;
    }

    public int getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(int parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment_text='" + comment_text + '\'' +
                ", ad_id=" + ad_id +
                ", countOfLikes=" + countOfLikes +
                ", parent_comment_id=" + parent_comment_id +
                '}';
    }
}
