package dao;

import models.GeneralNews;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.ArrayList;

public class Sql2oGeneralNewsDao implements GeneralNewsDao{

    private final Sql2o sql2o;
    public Sql2oGeneralNewsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void addGeneralNews(GeneralNews generalnews) {
        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO generalnews (title,content,user_id) VALUES (:title,:content,:user_id)";
            int id= (int) con.createQuery(sql,true)
                    .bind(generalnews)
                    .executeUpdate()
                    .getKey();
            generalnews.setId(id);

        }catch (Sql2oException e){
            System.out.println(e);
        }

    }

    @Override
    public void addUsersToGeneralNews(Users users, GeneralNews generalNews) {
        String sql="INSERT INTO users_generalnews (user_id,generalnews_id) VALUES (:user_id,:generalnews_id_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("user_id", Users.getId())
                    .addParameter("generalnews_id", GeneralNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<GeneralNews> getAll() {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM generalnews";
            return con.createQuery(sql,true)
                    .executeAndFetch(GeneralNews.class);

        }
    }

    @Override
    public List<GeneralNews> getAllGeneralNewsByUsers(int user_id) {
        List<GeneralNews> generalNews = new ArrayList();
        String joinQuery = "SELECT generalnews_id FROM users_generalnews WHERE user_id = :user_id";
        try (Connection con = sql2o.open()) {
            List<Integer> allGeneralNewsIds = con.createQuery(joinQuery)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Integer.class);
            for (Integer GeneralNewsId : allGeneralNewsIds){
                String generalnewsQuery = "SELECT * FROM generalnews WHERE id = :generalnews_id";
                generalNews.add(
                        con.createQuery(generalnewsQuery)
                                .addParameter("generalnews_id", GeneralNewsId)
                                .executeAndFetchFirst(GeneralNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return generalNews;
    }

    @Override
    public GeneralNews findById(int id) {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM generalnews WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(GeneralNews.class);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from generalnews WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        try (Connection con=sql2o.open()){
            String sqlGeneralNews="DELETE FROM generalnews";
            con.createQuery(sqlGeneralNews).executeUpdate();

        }catch (Sql2oException e){
            System.out.println(e);
        }

    }
}
