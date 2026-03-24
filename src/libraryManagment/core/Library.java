package libraryManagment.core;

import libraryManagment.model.Book;
import libraryManagment.model.Employee;
import libraryManagment.model.Member;

import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
