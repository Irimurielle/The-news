package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UsersTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Users testUsers = setUpUser();
        assertEquals("Merry Karangwa", testUsers.getName());
    }

    @Test
    public void getRoleReturnsUserRole() throws Exception {
        Users testUsers = setUpUser();
        assertEquals("Directing the company", testUsers.getRole());
    }

    @Test
    public void getPositionReturnsUserPosition() throws Exception{
        Users testUsers = setUpUser();
        assertEquals("Deputy Director", testUsers.getPosition());
    }

    @Test
    public void setNameSetsCorrectName() throws Exception{
        Users testUsers = setUpUser();
        testUsers.setName("Mukamana Adeline");
        assertNotEquals("Merry Karangwa", testUsers.getName());
    }

    @Test
    public void setRoleSetsUserRole() throws Exception{
        Users testUsers = setUpUser();
        testUsers.setRole("Directing the company");
        assertNotEquals("Recruiting employees", testUsers.getRole());
    }

    @Test
    public void setPositionSetsUserPosition() throws  Exception{
        Users testUsers = setUpUser();
        testUsers.setPosition("Deputy Director");
        assertNotEquals("Human Resource Manager", testUsers.getPosition());
    }

    @Test
    public void setDepartmentIdSetsDepartmentId() {
        Users testUsers = setUpUser();
        testUsers.setDepartment_id(1);
        assertNotEquals(2, testUsers.getDepartment_id());
    }

    private Users setUpUser() {
        return new Users("Merry Karangwa","Directing the company","Deputy Director",1);
    }

}
