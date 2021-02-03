package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameReturnsCorrectName() throws Exception {
        User testUser = setUpUser();
        assertEquals("Merry Karangwa", testUser.getName());
    }

    @Test
    public void getRoleReturnsUserRole() throws Exception {
        User testUser = setUpUser();
        assertEquals("Directing the company", testUser.getRole());
    }

    @Test
    public void getPositionReturnsUserPosition() throws Exception{
        User testUser = setUpUser();
        assertEquals("Deputy Director", testUser.getPosition());
    }

    @Test
    public void setNameSetsCorrectName() throws Exception{
        User testUser = setUpUser();
        testUser.setName("Mukamana Adeline");
        assertNotEquals("Merry Karangwa", testUser.getName());
    }

    @Test
    public void setRoleSetsUserRole() throws Exception{
        User testUser = setUpUser();
        testUser.setRole("Directing the company");
        assertNotEquals("Recruiting employees", testUser.getRole());
    }

    @Test
    public void setPositionSetsUserPosition() throws  Exception{
        User testUser = setUpUser();
        testUser.setPosition("Deputy Director");
        assertNotEquals("Human Resource Manager", testUser.getPosition());
    }

    @Test
    public void setDepartmentIdSetsDepartmentId() {
        User testUser = setUpUser();
        testUser.setDepartment_id(1);
        assertNotEquals(2, testUser.getDepartment_id());
    }

    private User setUpUser() {
        return new User("Merry Karangwa","Directing the company","Deputy Director",1);
    }

}
