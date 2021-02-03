package dao;

import models.*;

import java.util.List;

public interface UserDao {
    void add(Users user);

    List<Users> getAll();
    Users findById(int id);
    List<Departments> getAllUsersByDepartment(int user_id);

    void update(int id, String name, String role, String position);

    void deleteById(int id);
    void clearAll();

}
