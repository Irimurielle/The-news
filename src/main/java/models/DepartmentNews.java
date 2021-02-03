package models;

public class DepartmentNews extends GeneralNews {
    private int department_id;
    public static final String NEWS_TYPE="department";

    public DepartmentNews(String title,String content,int user_id,int department_id) {
        super(content, title, user_id);
        this.department_id = department_id;
        this.type=NEWS_TYPE;
    }
    public DepartmentNews(String title, String content) {
        super(content, title);
        this.department_id = department_id;
        this.type=NEWS_TYPE;
    }

    @Override
    public int getUser_id() { return user_id; }

    @Override
    public void setUser_id(int employee_id) { this.user_id = user_id; }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int departmentId) {
        this.department_id = department_id;
    }
}
