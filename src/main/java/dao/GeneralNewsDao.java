package dao;

import models.*;

import java.util.List;

public interface GeneralNewsDao {
    void addGeneralNews(GeneralNews generalnews);
    void addUsersToGeneralNews(Users users, GeneralNews generalNews);

    List<GeneralNews> getAll();
    List<GeneralNews> getAllGeneralNewsByUsers(int user_id);
    GeneralNews findById(int id);

    void deleteById(int id);
    void clearAll();
}
