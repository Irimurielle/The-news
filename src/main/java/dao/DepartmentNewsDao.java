package dao;

import models.*;

import java.util.List;

public interface DepartmentNewsDao {
    void addDepartmentNews(DepartmentNews departmentnews);
    void addUsersToDepartmentNews(Users users, DepartmentNews departmentNews);
    void addDepartmentToDepartmentNews(Departments department, DepartmentNews departmentnews);

    List<DepartmentNews> getAll();
    List<DepartmentNews> getAllDepartmentNewsByUsers(int user_id);
    List<DepartmentNews> getAllDepartmentNewsByDepartment(int department_id);
    DepartmentNews findById(int id);

    void deleteById(int id);
    void clearAll();
}
