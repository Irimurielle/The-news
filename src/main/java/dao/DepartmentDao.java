package dao;

import models.*;

import java.util.List;

public interface DepartmentDao {
    void add(Departments departments);
    void addUserToDepartment(Users users, Departments departments);

    List<Departments> getAll();
    List<Users> getAllUsersInDepartment(int id);
    Departments findById(int id);
    List<DepartmentNews> getDepartmentNews(int id);

    void deleteById(int id);
    void clearAll();
}
