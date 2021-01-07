package models;

import java.util.Objects;

public class GeneralNews {
    public static int id;
    public String title;
    public String content;
    public String type;
    public int user_id;
    public final String NEWS_TYPE="general";

    public GeneralNews(String title,String content,int user_id) {
        this.title=title;
        this.content=content;
        this.type=NEWS_TYPE;
        this.user_id=user_id;
    }

    public GeneralNews(String title,String content) {
        this.title = title;
        this.content = content;
        this.type=NEWS_TYPE;
    }

    public static int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_Id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneralNews)) return false;
        GeneralNews that = (GeneralNews) o;
        return id == that.id &&
                user_id == that.user_id &&
                Objects.equals(content, that.content) &&
                Objects.equals(title, that.title) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, title, type, user_id);
    }
}
