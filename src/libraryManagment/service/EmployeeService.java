package libraryManagment.service;

import libraryManagment.core.Library;
import libraryManagment.model.Employee;
import libraryManagment.model.EmployeeRole;
import libraryManagment.storage.FileStorage;

import java.util.List;

public class EmployeeService {
    private Library library;
    private Employee loggedEmployee;
    private FileStorage storage;

    public void setLibrary(Library library) {
        this.library = library;
    }

    public EmployeeService(Library library, FileStorage storage) {
        this.library = library;
        this.storage = storage;
    }

    public void loginEmployee(String name, String password) {
        Employee employee = findEmployeeByName(name);

        if (employee == null) {
            System.out.println("Employee not found");
            return;
        }

        if (!employee.getPassword().equals(password)) {
            System.out.println("Wrong password");
            return;
        }

        loggedEmployee = employee;
        System.out.println("\nLogin successful");
        System.out.println("Welcome, " + employee.getName() + " (Id=" +
                employee.getId() + ", Role=" + employee.getEmployeeRole() + ")");
    }

    public void registerEmployee(String name, String password, EmployeeRole employeeRole) {

        if (library.getEmployees().isEmpty()) {
            Employee employee = new Employee(name, password, EmployeeRole.ADMIN);
            library.getEmployees().add(employee);

            System.out.println("Admin registered successfully");
            return;
        }

        Employee employee = new Employee(name, password, employeeRole);
        library.getEmployees().add(employee);

        System.out.println("Employee registered successfully");
    }

    public void deleteEmployee(int id) {
        Employee employee = findEmployeeById(id);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("\nEmployee information: ");
        System.out.println("Name: " + employee.getName());
        System.out.println("Role: " + employee.getEmployeeRole());

        if (employee != null && employee.getId() == loggedEmployee.getId()) {
            loggedEmployee = null;
            System.out.println("\nYou deleted your own account. Logging out...");
        }

        library.getEmployees().remove(employee);
        System.out.println("\nEmployee removed successfully.");
    }

    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }

    public void logoutEmployee() {
        loggedEmployee = null;
        System.out.println("You have logged out");
    }

    public void listEmployees() {
        for (Employee employee : library.getEmployees()) {
            System.out.println(employee);
        }
    }

    private Employee findEmployeeByName(String employeeName) {
        for (Employee employee : library.getEmployees()) {
            if (employee.getName().equals(employeeName)) {
                return employee;
            }
        }
        return null;
    }

    private Employee findEmployeeById(int employeeId) {
        for (Employee employee : library.getEmployees()) {
            if (employee.getId() == employeeId) {
                return employee;
            }
        }
        return null;
    }

    public boolean employeeNameExists(String name) {
        for (Employee employee : library.getEmployees()) {
            if (employee.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyEmployees() {
        return !library.getEmployees().isEmpty();
    }

    public boolean hasAdmin() {
        for (Employee employee : library.getEmployees()) {
            if (employee.getEmployeeRole() == EmployeeRole.ADMIN) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdmin() {
        return loggedEmployee.getEmployeeRole() == EmployeeRole.ADMIN;
    }

    public boolean isLoggedIn() {
        return loggedEmployee != null;
    }

    public void syncEmployeeIdCounter() {
        int maxId = 0;

        for (Employee employee : library.getEmployees()) {
            if (employee.getId() > maxId) {
                maxId = employee.getId();
            }
        }

        Employee.setNextId(maxId + 1);
    }

    public List<Employee> getAllEmployees() {
        return library.getEmployees();
    }
}
