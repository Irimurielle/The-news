import com.google.gson.Gson;
import dao.*;
import exceptions.ApiException;
import org.sql2o.*;
import models.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    public static void main(String[] args) {
        Sql2oGeneralNewsDao sql2oGeneralNewsDao;
        Sql2oDepartmentNewsDao sql2oDepartmentNewsDao;
        Sql2oUserDao sql2oUserDao;
        Sql2oDepartmentDao sql2oDepartmentDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/the_news";
        Sql2o sql2o = new Sql2o(connectionString, "murielle", "murielle12");

        sql2oDepartmentDao = new Sql2oDepartmentDao(sql2o);
        sql2oUserDao = new Sql2oUserDao(sql2o);
        sql2oGeneralNewsDao = new  Sql2oGeneralNewsDao(sql2o);
        sql2oDepartmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn=sql2o.open();

        port(getHerokuAssignedPort());
        staticFileLocation("/public");


        get("/",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        get("/departments/new",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            return new ModelAndView(model,"newDepartment.hbs");
        },new HandlebarsTemplateEngine());

        post("/departments",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            String description=request.queryParams("description");
            String size=request.queryParams("size");
            Departments department=new Departments(name,description);
            sql2oDepartmentDao.add( department);
            return new ModelAndView(model,"newDepartment.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/departments",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("departments",sql2oDepartmentDao.getAll());
            return new ModelAndView(model,"departments.hbs");
        },new HandlebarsTemplateEngine());

        post("/departments/new", "application/json", (req, res) -> {
            Departments department = gson.fromJson(req.body(), Departments.class);
            sql2oDepartmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });

        get("/departments","application/json",(request, response) -> {
            if(sql2oDepartmentDao.getAll().size()>0){
                return gson.toJson(sql2oDepartmentDao.getAll());
            }
            else {
                return "{\"message\":\"Sorry, No departments .\"}";
            }
        });

        get("/department/:id/users","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentDao.getAllUsersInDepartment(id).size()>0){
                return gson.toJson(sql2oDepartmentDao.getAllUsersInDepartment(id));
            }
            else {
                return "{\"message\":\"This department has no users.\"}";
            }
        });

        get("/department/:id","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentDao.findById(id)==null){
                throw new ApiException(404, String.format("No department with id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oDepartmentDao.findById(id));
            }
        });

        get("/departmentnews/department/:id","application/json",(request, response) -> {

            int id=Integer.parseInt(request.params("id"));
            Departments departments=sql2oDepartmentDao.findById(id);
            if(departments==null){
                throw new ApiException(404, String.format("No department with id: \"%s\" exists",
                        request.params("id")));
            }
            if(sql2oDepartmentDao.getDepartmentNews(id).size()>0){
                return gson.toJson(sql2oDepartmentDao.getDepartmentNews(id));
            }
            else {
                return "{\"message\":\"no news in this department.\"}";
            }
        });

        get("/users/new",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            model.put("departments",sql2oDepartmentDao.getAll());
            return new ModelAndView(model,"newUser.hbs");
        },new HandlebarsTemplateEngine());

        post("/users",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            String name=request.queryParams("name");
            String position=request.queryParams("position");
            String role=request.queryParams("role");
            Users user = new Users(name,position,role);
            sql2oUserDao.add( user);
            return new ModelAndView(model,"newUser.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/users",(request, response) -> {
            Map<String,Object> model=new HashMap<>();
            model.put("users",sql2oUserDao.getAll());
            model.put("departments",sql2oDepartmentDao.getAll());
            return new ModelAndView(model,"users.hbs");
        },new HandlebarsTemplateEngine());

        post("/users/new","application/json",(request, response) -> {
            Users user=gson.fromJson(request.body(),Users.class);
            sql2oUserDao.add(user);
            response.status(201);
            return gson.toJson(user);
        });

        post("/add/user/:user_id/department/:department_id","application/json",(request, response) -> {

            int user_id=Integer.parseInt(request.params("user_id"));
            int department_id=Integer.parseInt(request.params("department_id"));
            Departments departments=sql2oDepartmentDao.findById(department_id);
            Users users=sql2oUserDao.findById(user_id);
            if(departments==null){
                throw new ApiException(404, String.format("No department with id: \"%s\" exists",
                        request.params("department_id")));
            }
            if(users==null){
                throw new ApiException(404, String.format("No user with id: \"%s\" exists",
                        request.params("user_id")));
            }
            sql2oDepartmentDao.addUserToDepartment(users,departments);

            List<Users> departmentUsers=sql2oDepartmentDao.getAllUsersInDepartment(departments.getId());

            response.status(201);
            return gson.toJson(departmentUsers);
        });



        get("/users", "application/json", (request, response) -> {

            if(sql2oDepartmentDao.getAll().size() > 0){
                return gson.toJson(sql2oUserDao.getAll());
            }
            else {
                return "{\"message\":\"No users in the database.\"}";
            }
        });

        get("/user/:id/departments","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUserDao.getAllUsersByDepartment(id).size()>0){
                return gson.toJson(sql2oUserDao.getAllUsersByDepartment(id));
            }
            else {
                return "{\"message\":\"User is in no department.\"}";
            }
        });

        get("/user/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUserDao.findById(id)==null){
                throw new ApiException(404, String.format("No user with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oUserDao.findById(id));
            }
        });

        get("/generalnews/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUserDao.getAll());
            return new ModelAndView(model,"newGeneralNews.hbs");
        },new HandlebarsTemplateEngine());

        post("/generalnews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String title=request.queryParams("title");
            String content=request.queryParams("content");
            GeneralNews generalnews=new GeneralNews(title,content);
            sql2oGeneralNewsDao.addGeneralNews(generalnews);
            return new ModelAndView(model,"newGeneralNews.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/generalnews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("generalnews",sql2oGeneralNewsDao.getAll());
            model.put("users",sql2oUserDao.getAll());
            return new ModelAndView(model,"generalNews.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/generalnews","application/json",(request, response) -> {
            GeneralNews generalnews =gson.fromJson(request.body(), GeneralNews.class);
            sql2oGeneralNewsDao.addGeneralNews(generalnews);
            response.status(201);
            return gson.toJson(generalnews);
        });

        get("/generalnews/news","application/json",(request, response) -> {
            if(sql2oGeneralNewsDao.getAll().size()>0){
                return gson.toJson(sql2oGeneralNewsDao.getAll());
            }
            else {
                return "{\"message\":\"No news.\"}";
            }
        });

        get("/generalnews/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oGeneralNewsDao.findById(id)==null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oGeneralNewsDao.findById(id));
            }
        });

        get("/departmentnews/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUserDao.getAll());
            model.put("departments",sql2oDepartmentDao.getAll());
            return new ModelAndView(model,"newDepartmentNews.hbs");
        },new HandlebarsTemplateEngine());

        post("departmentnews",(request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String title = request.queryParams("title");
            String content = request.queryParams("content");
            int userid = Integer.parseInt(request.params("user"));
            int departmentId = Integer.parseInt(request.params("department"));
            DepartmentNews departmentnews = new DepartmentNews(title, content, userid, departmentId);
            sql2oDepartmentNewsDao.addDepartmentNews(departmentnews);
            return new ModelAndView(model, "newDepartmentNews.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/departmentnews",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("users",sql2oUserDao.getAll());
            model.put("departments",sql2oDepartmentDao.getAll());
            model.put("departmentnews",sql2oDepartmentNewsDao.getAll());
            return new ModelAndView(model,"departmentNews.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/departmentnews","application/json",(request, response) -> {
            DepartmentNews departmentNews =gson.fromJson(request.body(),DepartmentNews.class);
            Departments department=sql2oDepartmentDao.findById(departmentNews.getDepartment_id());
            Users user=sql2oUserDao.findById(departmentNews.getUser_id());
            if(department==null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            if(user==null){
                throw new ApiException(404, String.format("No employee with the id: \"%s\" exists",
                        request.params("id")));
            }
            sql2oDepartmentNewsDao.addDepartmentNews(departmentNews);
            response.status(201);
            return gson.toJson(departmentNews);
        });

        get("/departmentnews/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentNewsDao.findById(id)==null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oDepartmentNewsDao.findById(id));
            }
        });

        exception(ApiException.class, (exception, request, response) -> {
            ApiException error = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", error.getStatusCode());
            jsonMap.put("errorMessage", error.getMessage());
            response.type("application/json");
            response.status(error.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });
    }
}
