package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DepartmentsTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Departments testDepartments = setUpDepartments();
        assertEquals("Sales Department", testDepartments.getName());
    }

    @Test
    public void getDescriptionReturnsDepartmentDescription() throws Exception{
        Departments testDepartments = setUpDepartments();
        assertEquals("Responsible for selling company products", testDepartments.getDescription());
    }

    @Test
    public void setNameSetsDepartmentName() throws Exception{
        Departments testDepartments = setUpDepartments();
        testDepartments.setName("Sales Department");
        assertNotEquals("Marketing Department", testDepartments.getName());
    }

    @Test
    public void setDescriptionSetsDepartmentDescription() throws Exception{
        Departments testDepartments = setUpDepartments();
        testDepartments.setDescription("Responsible for selling company products");
        assertNotEquals("Responsible for advertising company products", testDepartments.getDescription());
    }

    private Departments setUpDepartments() {
        return new Departments("Sales Department","Responsible for selling company products");
    }
}
