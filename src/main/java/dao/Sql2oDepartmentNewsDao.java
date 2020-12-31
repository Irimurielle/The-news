package dao;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentNewsDao implements DepartmentNewsDao{
    private final Sql2o sql2o;
    public Sql2oDepartmentNewsDao(Sql2o sql2o) { this.sql2o = sql2o; }


    @Override
    public void addDepartmentNews(DepartmentNews departmentnews) {
        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO departmentnews  (title,content,user_id,department_id) VALUES (:tittle,:content, :user_id,:department_id)";
            int id= (int) con.createQuery(sql,true)
                    .bind(departmentnews)
                    .executeUpdate()
                    .getKey();
            departmentnews.setId(id);
        }catch (Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public void addUsersToDepartmentNews(Users users, DepartmentNews departmentNews) {
        String sql="INSERT INTO users_departmentnews (user_id,departmentnews_id) VALUES (:user_id,:departmentnews_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("user_id", Users.getId())
                    .addParameter("departmentnews_id", DepartmentNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


    @Override
    public void addDepartmentToDepartmentNews(Departments department, DepartmentNews departmentNews) {
        String sql="INSERT INTO departments_departmentnews (department_id,departmentnews_id) VALUES (:department_id,:departmentnews_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("department_id", Departments.getId())
                    .addParameter("departmentnews_id", DepartmentNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public List<DepartmentNews> getAll() {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM departmentnews";
            return con.createQuery(sql,true)
                    .executeAndFetch(DepartmentNews.class);

        }
    }

    @Override
    public List<DepartmentNews> getAllDepartmentNewsByUsers(int user_id) {
        List<DepartmentNews> departmentNews = new ArrayList();
        String joinQuery = "SELECT departmentnews_id FROM users_departmentnews WHERE user_id = :user_id";
        try (Connection con = sql2o.open()) {
            List<Integer> DepartmentNewsIds = con.createQuery(joinQuery)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Integer.class);
            for (Integer DepartmentNewsId : DepartmentNewsIds){
                String departmentnewsQuery = "SELECT * FROM departmentnews WHERE id = :departmentnews_id";
                departmentNews.add(con.createQuery(departmentnewsQuery)
                        .addParameter("departmentnews_id", DepartmentNewsId)
                        .executeAndFetchFirst(DepartmentNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departmentNews;
    }

    @Override
    public List<DepartmentNews> getAllDepartmentNewsByDepartment(int department_id) {
        List<DepartmentNews> departmentNews = new ArrayList();
        String joinQuery = "SELECT departmentnews_id FROM departments_departmentnews WHERE department_id = :department_id";
        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentNewsIds = con.createQuery(joinQuery)
                    .addParameter("department_id", department_id)
                    .executeAndFetch(Integer.class);
            for (Integer DepartmentNewsId : allDepartmentNewsIds){
                String departmentnewsQuery = "SELECT * FROM departmentnews WHERE id = :departmentnews_id";
                departmentNews.add(
                        con.createQuery(departmentnewsQuery)
                                .addParameter("departmentnews_id", DepartmentNewsId)
                                .executeAndFetchFirst(DepartmentNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departmentNews;
    }


    @Override
    public DepartmentNews findById(int id) {
        try(Connection con=sql2o.open()) {
            String sql="SELECT * FROM departmentnews WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(DepartmentNews.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departmentnews WHERE id=:id";
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
            String sql="DELETE FROM departments";
            String sqlDepartmentNews="DELETE FROM departmentnews";
            con.createQuery(sql).executeUpdate();
            con.createQuery(sqlDepartmentNews).executeUpdate();

        }catch (Sql2oException e){
            System.out.println(e);
        }

    }
}
