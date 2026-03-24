package libraryManagment.model;

import java.io.Serializable;

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String password;
    private EmployeeRole employeeRole;

    private static int nextId = 1;

    public Employee(String name, String password, EmployeeRole employeeRole) {
        this.id = nextId++;
        this.name = name;
        this.password = password;
        this.employeeRole = employeeRole;
    }

    public static void setNextId(int nextId) {
        Employee.nextId = nextId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name +
                "', account status=" + employeeRole +
                "}";

    }
}
