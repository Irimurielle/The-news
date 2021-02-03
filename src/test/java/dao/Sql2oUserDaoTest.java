package dao;

import models.Users;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oUserDaoTest {
    private static Connection conn;
    private static Sql2oUserDao userDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/the_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "wecode", "1234");
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        userDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingUser() {
        Users user = setNewUser();
        int userId= user.getId();
        userDao.add(user);
        assertNotEquals(userId,user.getId());
    }

    @Test
    public void FindUserById() {
        Users user = setNewUser();
        userDao.add(user);
        assertEquals(user.getName(),userDao.findById(user.getId()).getName());
    }

    private Users setNewUser() {
        return new Users("Marie", "Accountant","finance manager");
    }

}