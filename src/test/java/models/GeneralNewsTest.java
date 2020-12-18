package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GeneralNewsTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContentReturnsNewsContent() throws Exception{
        GeneralNews testNews = setUpGeneralNews();
        assertEquals("This month total sales were higher than last month at the rate of 10%", testNews.getContent());
    }

    @Test
    public void setContentSetsNewsContent() throws Exception{
        GeneralNews testNews = setUpGeneralNews();
        testNews.setContent("Today our company is celebrating 8 years of functioning");
        assertNotEquals("This month total sales were higher than last month at the rate of 10%", testNews.getContent());
    }

    @Test
    public void getTitleReturnsNewsTitle() throws Exception{
        GeneralNews testNews = setUpGeneralNews();
        assertEquals("New sales", testNews.getTitle());
    }

    @Test
    public void setTitleSetsNewsTitle() throws Exception{
        GeneralNews testNews = setUpGeneralNews();
        testNews.setTitle("Eight years annivarsary");
        assertNotEquals("New sales", testNews.getTitle());
    }

    @Test
    public void setUserIdSetsUserId() throws Exception{
        GeneralNews testNews = setUpGeneralNews();
        testNews.setUser_id(1);
        assertNotEquals(2, testNews.getUser_id());
    }

    private GeneralNews setUpGeneralNews() {
        return new GeneralNews("New sales","This month total sales were higher than last month at the rate of 10%", 1);
    }
}
