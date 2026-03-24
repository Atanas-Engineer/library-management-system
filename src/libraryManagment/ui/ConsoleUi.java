package libraryManagment.ui;

import libraryManagment.core.Library;
import libraryManagment.model.Book;
import libraryManagment.model.Employee;
import libraryManagment.model.EmployeeRole;
import libraryManagment.model.Member;
import libraryManagment.service.BookService;
import libraryManagment.service.EmployeeService;
import libraryManagment.service.MemberService;
import libraryManagment.storage.FileStorage;
import libraryManagment.util.InputReader;

import java.util.List;

public class ConsoleUi {

    private EmployeeService employeeService;
    private BookService bookService;
    private MemberService memberService;
    private InputReader inputReader;
    private FileStorage storage;
    private Library library;

    public ConsoleUi(EmployeeService employeeService, BookService bookService,
                     MemberService memberService, Library library, FileStorage storage) {
        this.employeeService = employeeService;
        this.bookService = bookService;
        this.memberService = memberService;
        this.inputReader = new InputReader();
        this.library = library;
        this.storage = storage;
    }

    private void printGuestMenu() {
        System.out.println();
        System.out.println("====== LIBRARY MENU ======");
        System.out.println("1 Login Employee");
        System.out.println("2 Save Library");
        System.out.println("3 Load library");
        System.out.println("0 Exit");
    }

    private boolean guestMenu() {

        printGuestMenu();

        int choice = inputReader.readInt();

        switch (choice) {
            case 1:
                if (employeeService.hasAdmin()) {
                    loginEmployee();
                } else {
                    registerEmployee();
                }
                return true;

            case 2:
                saveLibrary();
                return true;

            case 3:
                loadLibrary(storage.getFILE_PATH());
                return true;

            case 0:
                return false;

            default:
                System.out.println("Invalid input. Please choose from available options.");
                return true;
        }
    }

    private void printAdminMenu() {
        System.out.println();
        System.out.println("====== ADMIN MENU ======");
        System.out.println("1 Register employee");
        System.out.println("2 Register Member");

        System.out.println("\n3 List books");
        System.out.println("4 Add book");
        System.out.println("5 Edit book");
        System.out.println("6 Delete book");

        System.out.println("\n7 List employees");
        System.out.println("8 Delete employees");

        System.out.println("\n9 List members");
        System.out.println("10 Delete member");

        System.out.println("\n11 Logout");
    }

    private void adminMenu() {

        printAdminMenu();

        int choice = inputReader.readInt();

        switch (choice) {
            case 1:
                registerEmployee();
                break;

            case 2:
                registerMember();
                break;

            case 3:
                listBooks();
                break;

            case 4:
                addBook();
                break;

            case 5:
                editBook();
                break;

            case 6:
                deleteBook();
                break;

            case 7:
                listEmployees();
                break;

            case 8:
                deleteEmployee();
                break;

            case 9:
                listMembers();
                break;

            case 10:
                deleteMember();
                break;

            case 11:
                logoutEmployee();
                break;

            default:
                System.out.println("Invalid input. Please choose from available options.");
        }
    }

    private void printEmployeeMenu() {
        System.out.println();
        System.out.println("====== EMPLOYEE MENU ======");
        System.out.println("1 Register member");

        System.out.println("\n2 Borrow book");
        System.out.println("3 Return book");

        System.out.println("\n4 List books");
        System.out.println("5 List available books");
        System.out.println("6 List borrowed books");
        System.out.println("7 List member's borrowed books");
        System.out.println("8 List member's borrowed books history");

        System.out.println("\n9 Search books by title");
        System.out.println("10 Search books by author");
        System.out.println("11 Search member by name");

        System.out.println("\n12 Logout");
    }

    private void employeeMenu() {

        printEmployeeMenu();

        int choice = inputReader.readInt();

        switch (choice) {
            case 1:
                registerMember();
                break;

            case 2:
                borrowBook();
                break;

            case 3:
                returnBook();
                break;

            case 4:
                listBooks();
                break;

            case 5:
                listAvailableBooks();
                break;

            case 6:
                listBorrowedBooks();
                break;

            case 7:
                printBorrowedBooksById();
                break;

            case 8:
                showBorrowHistory();
                break;

            case 9:
                searchBooksByTitle();
                break;

            case 10:
                searchBooksByAuthor();
                break;

            case 11:
                searchMemberByName();
                break;

            case 12:
                logoutEmployee();
                break;

            default:
                System.out.println("Invalid input. Please choose from available options.");
        }
    }

    public void start() {
        boolean running = true;

        while (running) {
            if (employeeService.getLoggedEmployee() == null) {
                running = guestMenu();
            } else if (employeeService.getLoggedEmployee().getEmployeeRole() == EmployeeRole.ADMIN) {
                adminMenu();
            } else {
                employeeMenu();
            }
        }
    }

    private void loginEmployee() {
        employeeService.loginEmployee(inputReader.readName(true), inputReader.readPassword(false));
    }

    private void logoutEmployee() {
        employeeService.logoutEmployee();
    }

    private void registerEmployee() {
        if (employeeService.hasAdmin()) {
            while (true) {
                String name = inputReader.readName(true);
                if (employeeService.employeeNameExists(name)) {
                    System.out.println("Name already exists. Please choose another name.");
                } else {
                    employeeService.registerEmployee(name,
                            inputReader.readPassword(true), inputReader.readEmployeeRole());
                    break;
                }
            }
        } else {

            System.out.println("\nNo employees found.\n" +
                    "System requires at least one ADMIN account.\n" +
                    "Create first ADMIN account.\n");

            employeeService.registerEmployee(inputReader.readName(true),
                    inputReader.readPassword(true), EmployeeRole.ADMIN);
        }
    }

    private void registerMember() {
        memberService.registerMember(inputReader.readName(false));
    }

    private void addBook() {
        bookService.addBook(inputReader.readIsbn(),
                inputReader.readTitle(false), inputReader.readAuthor(false));
    }

    private void deleteBook() {
        bookService.deleteBook(inputReader.readIsbn());
    }

    private void editBook() {
        String isbn = inputReader.readIsbn();
        bookService.editBook(isbn);
    }

    private void borrowBook() {
        bookService.borrowBook(inputReader.readId(false), inputReader.readIsbn());
    }

    private void returnBook() {
        bookService.returnBook(inputReader.readId(false), inputReader.readIsbn());
    }

    private void listBooks() {
        printBooksTable(bookService.getAllBooks());
    }


    private void listMembers() {
        printMembersTable(memberService.getAllMembers());
    }

    private void deleteMember() {
        memberService.deleteMember(inputReader.readId(false));
    }

    private void listEmployees() {
        printEmployeesTable(employeeService.getAllEmployees());
    }

    private void deleteEmployee() {
        employeeService.deleteEmployee(inputReader.readId(true));
    }

    private void printBooksTable(List<Book> books) {

        if (books.isEmpty()) {
            System.out.println("\nNo books in library.");
            return;
        }

        String format = "%n%-15s %-45s %-35s %-10s%n";

        System.out.printf(format, "ISBN", "TITLE", "AUTHOR", "STATUS");

        System.out.println("------------------------------------------------------------------------" +
                "-----------------------------------");

        for (Book book : books) {
            System.out.printf("%-15s %-45s %-35s %-10s%n",
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getBookStatus());
        }
    }

    private void printMembersTable(List<Member> members) {

        if (members.isEmpty()) {
            System.out.println("\nNo members in library.");
            return;
        }

        String format = "%n%-10s %-20s %-15s%n";

        System.out.printf(format, "ID", "NAME", "BOOKS");
        System.out.println("--------------------------------------");

        for (Member member : members) {
            System.out.printf("%-10s %-20s %-15s%n",
                    member.getId(),
                    member.getName(),
                    member.getBorrowedBooks().size());
        }
    }

    private void printEmployeesTable(List<Employee> employees) {

        if (employees.isEmpty()) {
            System.out.println("\nNo Employees in library");
            return;
        }

        String format = "%n%-10s %-20s %-10s%n";

        System.out.printf(format, "ID", "NAME", "ROLE");
        System.out.println("----------------------------------------");

        for (Employee employee : employees) {
            System.out.printf("%-10s %-20s %-10s%n",
                    employee.getId(),
                    employee.getName(),
                    employee.getEmployeeRole());
        }
    }

    public void saveLibrary() {
        storage.saveLibrary(library);
    }

    public void loadLibrary(String fileName) {

        library = storage.loadLibrary(fileName);

        employeeService.setLibrary(library);
        memberService.setLibrary(library);
        bookService.setLibrary(library);

        employeeService.syncEmployeeIdCounter();
        memberService.syncMemberIdCounter();
    }

    private void listAvailableBooks() {
        bookService.listAvailableBooks();
    }

    private void listBorrowedBooks() {
        bookService.listBorrowedBooks();
    }

    private void printBorrowedBooksById() {
    memberService.printBorrowedBooksById(inputReader.readId(false));
    }

    private void searchBooksByTitle() {
        bookService.searchBooksByTitle(inputReader.readSearchText("Enter book title:"));
    }

    private void searchBooksByAuthor() {
        bookService.searchBooksByAuthor(inputReader.readSearchText("Enter author name:"));
    }

    private void searchMemberByName() {
        memberService.searchMemberByName(inputReader.readSearchText("Enter member name"));
    }

    private void showBorrowHistory() {
        memberService.showBorrowHistory(inputReader.readId(false));
    }
}
