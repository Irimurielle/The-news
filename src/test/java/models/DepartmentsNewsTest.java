package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DepartmentsNewsTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setDepartmentIdSetsDepartmentId() throws Exception{
        DepartmentNews testDepartmentNews = setUpDepartmentNews();
        testDepartmentNews.setDepartment_id(1);
        assertNotEquals(2, testDepartmentNews.getDepartment_id());
    }

    @Test
    public void getType() throws Exception {
        DepartmentNews testDepartmentNews = setUpDepartmentNews();
        assertEquals("department", testDepartmentNews.getType());
    }

    private DepartmentNews setUpDepartmentNews() {
        return new DepartmentNews("This month total sales were higher than last month at the rate of 10%", "New sales", 1, 1);
    }
}
