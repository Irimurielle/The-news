package dao;

import models.GeneralNews;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oGeneralNewsDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;
    private static Sql2oGeneralNewsDao generalNewsDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/the_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "wecode", "1234");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        generalNewsDao = new Sql2oGeneralNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll();
        userDao.clearAll();
        generalNewsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void getAll() throws Exception {
        GeneralNews generalNews1 =setupGeneralNews();
        GeneralNews generalNews2 =setupGeneralNews();
        assertNotEquals(2, generalNewsDao.getAll().size());
    }

    public GeneralNews setupGeneralNews() {
        GeneralNews generalNews = new GeneralNews("Eighth Anniversary","celebrating another year",1);
        generalNewsDao.addGeneralNews(generalNews);
        return generalNews;
    }

}