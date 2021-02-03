package dao;

import models.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/the_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "wecode", "1234");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll();
        userDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingDepartmentSetsId() throws Exception {
        Departments testdepartment = setupDepartment();
        int originalDepartmentId = testdepartment.getId();
        departmentDao.add(testdepartment);
        assertNotEquals(originalDepartmentId, testdepartment.getId());
    }

    @Test
    public void addeddepartmentsAreReturnedFromGetAll() throws Exception {
        Departments testdepartment = setupDepartment();
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void nodepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Departments testdepartment = setupDepartment();
        Departments otherdepartment = setupDepartment();
        assertEquals(testdepartment, departmentDao.findById(testdepartment.getId()));
    }

    @Test
    public void deleteByIdDeletesCorrectRestaurant() throws Exception {
        Departments testdepartment = setupDepartment();
        departmentDao.deleteById(testdepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Departments testdepartment = setupDepartment();
        Departments otherdepartment = setupDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void getAllUsersaddedtoDepartmentCorrectly() throws Exception {
        Users testUser = new Users("Marie", "keeping bank records","accountant");
        userDao.add(testUser);
        Users otherUser = new Users("Marie", "keeping bank records","accountant");
        userDao.add(otherUser);
        Departments testdepartment = setupDepartment();
        departmentDao.add(testdepartment);
        departmentDao.addUserToDepartment(testUser,testdepartment);
        departmentDao.addUserToDepartment(otherUser,testdepartment);
        Users[] users = {testUser, otherUser};
        assertEquals(Arrays.asList(users), departmentDao.getAllUsersInDepartment(testdepartment.getId()));
    }

    @Test
    public void deletelingDepartmentAlsoUpdatesJoinTable() throws Exception {
        Users testUser = new Users("Marie", "keeping bank records","accountant");
        userDao.add(testUser);
        Departments testdepartment = setupDepartment();
        departmentDao.add(testdepartment);
        departmentDao.addUserToDepartment(testUser,testdepartment);
        departmentDao.deleteById(testdepartment.getId());
        assertNotEquals(0, departmentDao.getAllUsersInDepartment(testdepartment.getId()).size());
    }

    public Departments setupDepartment (){
        Departments department = new Departments("sales", "selling company products");
        departmentDao.add(department);
        return department;

    }




}