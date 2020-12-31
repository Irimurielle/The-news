package dao;

import models.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentNewsDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;
    private static Sql2oGeneralNewsDao generalNewsDao;
    private static Sql2oDepartmentNewsDao departmentNewsDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/the_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "murielle", "murielle12");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll();
        userDao.clearAll();
        generalNewsDao.clearAll();
        departmentNewsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingDepartmentnewsSetsId() throws Exception {
        Departments testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        DepartmentNews testDepartmentNews = new DepartmentNews("Eighth Anniversary","celebrating another year",1,1);
        int originalDepartmentNewsId = testDepartmentNews.getId();
        departmentNewsDao.addDepartmentNews(testDepartmentNews);
        assertEquals(originalDepartmentNewsId,testDepartmentNews.getId());
    }

    @Test
    public void getAll() throws Exception {
        DepartmentNews DepartmentNews1 =setupDepartmentNews();
        DepartmentNews DepartmentNews2 =setupDepartmentNews();
        assertNotEquals(2, departmentNewsDao.getAll().size());
    }

    public DepartmentNews setupDepartmentNews() {
        DepartmentNews DepartmentNews = new DepartmentNews("Eighth Anniversary","celebrating another year",1,1);
        departmentNewsDao.addDepartmentNews(DepartmentNews);
        return DepartmentNews;
    }

    public Departments setupDepartment (){
        Departments department = new Departments("sales", "selling company products");
        departmentDao.add(department);
        return department;

    }


}