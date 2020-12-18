package models;

import java.util.Objects;

public class Users {

    private int id;
    private String name;
    private String role;
    private String position;
    private int department_id;

    public Users(String name, String role, String position){
        this.name = name;
        this.position = position;
        this.role = role;
        this.id =id;
        this.department_id = department_id;
    }

    public Users(String name, String role, String position, int department_id){
        this.name=name;
        this.role=role;
        this.position=position;
        this.department_id=department_id;
    }

    public int getId() {
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
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return id == users.id &&
                department_id == users.department_id &&
                Objects.equals(position, users.position) &&
                Objects.equals(role, users.role) &&
                Objects.equals(name, users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, role, name, department_id);
    }
}
