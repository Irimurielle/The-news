package models;

import java.util.Objects;

public class User {

    private static int id;
    private String name;
    private String role;
    private String position;
    private int department_id;

    public User(String name, String role, String position){
        this.name = name;
        this.position = position;
        this.role = role;
        this.id =id;
        this.department_id = department_id;
    }

    public User(String name, String role, String position, int department_id){
        this.name=name;
        this.role=role;
        this.position=position;
        this.department_id=department_id;
    }

    public static int getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public int getDepartment_id() { return department_id; }

    public void setDepartment_id(int department_id) { this.department_id = department_id; }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setRole(String role) { this.role = role; }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                department_id == user.department_id &&
                Objects.equals(name, user.name) &&
                Objects.equals(position, user.position) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, role, department_id);
    }
}