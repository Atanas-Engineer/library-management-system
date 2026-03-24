package libraryManagment.service;

import libraryManagment.core.Library;
import libraryManagment.model.Book;
import libraryManagment.model.BookStatus;
import libraryManagment.model.Member;
import libraryManagment.storage.FileStorage;
import libraryManagment.util.InputReader;

import java.util.List;

public class BookService {
    private Library library;
    private EmployeeService employeeService;
    private MemberService memberService;
    private FileStorage storage;
    private InputReader inputReader;

    public void setLibrary(Library library) {
        this.library = library;
    }

    public BookService(Library library, EmployeeService employeeService,
                       MemberService memberService, FileStorage storage, InputReader inputReader) {
        this.library = library;
        this.employeeService = employeeService;
        this.memberService = memberService;
        this.storage = storage;
        this.inputReader = inputReader;
    }

    public void addBook(String isbn, String title, String author) {
        Book existingBook = findBookByIsbn(isbn);

        if (existingBook != null) {
            System.out.println("Book with this ISBN already exists");
            return;
        }

        Book book = new Book(isbn, title, author);
        library.getBooks().add(book);

        System.out.println("Book added successfully: " + title + " ( " + author + " )");
    }

    public void editBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book == null) {
            System.out.println("There is no book with this ISBN.");
            return;
        }

        System.out.println("\nCurrent book info:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());

        String newTitle = inputReader.readTitle(true);

        if (!newTitle.isEmpty()) {
            book.setTitle(newTitle);
        }

        String newAuthor = inputReader.readAuthor(true);

        if (!newAuthor.isEmpty()) {
            book.setAuthor(newAuthor);
        }
    }

    public void deleteBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book == null) {
            System.out.println("There is no book with this ISBN.");
            return;
        }

        if (book.getBookStatus() == BookStatus.BORROWED) {
            System.out.println("Return the book before deleting.");
            return;
        }

        System.out.println("\nBook information:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());

        library.getBooks().remove(book);
        System.out.println("\nBook deleted successfully");
    }

    public List<Book> getAllBooks() {
        return library.getBooks();
    }

    public void listBooks() {
        for (Book book : library.getBooks()) {
            System.out.println(book);
        }

    }

    public void borrowBook(int memberId, String isbn) {

        Member member = memberService.findMemberById(memberId);
        Book book = findBookByIsbn(isbn);

        if (member == null) {
            System.out.println("Member not found");
            return;
        }

        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        if (book.getBookStatus() == BookStatus.BORROWED) {
            System.out.println("Book already borrowed");
            return;
        }

        member.borrowBook(book);

        System.out.println("Book borrowed successfully");
    }

    public void returnBook(int memberId, String isbn) {
        Member member = memberService.findMemberById(memberId);
        Book book = findBookByIsbn(isbn);

        if (member == null) {
            System.out.println("Member not found");
            return;
        }

        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        member.returnBook(book);
    }

    private Book findBookByIsbn(String isbn) {
        for (Book book : library.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public void listAvailableBooks() {

        List<Book> books = library.getBooks();
        boolean hasAvailable = false;

        System.out.printf("%n%-15s %-45s %-35s %-10s%n",
                "ISBN", "TITLE", "AUTHOR", "STATUS");
        System.out.println("------------------------------------------------------------------------" +
                "-----------------------------------");

        for (Book book : books) {

            if (book.getBookStatus() == BookStatus.AVAILABLE) {

                System.out.printf("%-15s %-45s %-35s %-10s%n",
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getBookStatus());

                hasAvailable = true;
            }
        }

        if (!hasAvailable) {
            System.out.println("No available books.");
        }
    }

    public void listBorrowedBooks() {

        List<Book> books = library.getBooks();
        boolean hasBorrowed = false;

        System.out.printf("%n%-15s %-45s %-35s %-10s%n",
                "ISBN", "TITLE", "AUTHOR", "STATUS");
        System.out.println("------------------------------------------------------------------------" +
                "-----------------------------------");

        for (Book book : books) {

            if (book.getBookStatus() == BookStatus.BORROWED) {
                System.out.printf("%-15s %-45s %-35s %-10s%n",
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getBookStatus());

                hasBorrowed = true;
            }
        }

        if (!hasBorrowed) {
            System.out.println("No borrowed books.");
        }
    }

    private boolean matches(String source, String target) {

        if (target == null || target.isBlank()) {
            return false;
        }

        if (source == null) {
            return false;
        }

        return source.toLowerCase().contains(target.toLowerCase());
    }

    private void printSearchHeader() {
        System.out.println("\nSearch results:");
    }

    private void printBook(Book book) {


        System.out.printf("%-15s %-45s %-35s %-10s%n",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getBookStatus());
    }

    public void searchBooksByTitle(String title) {

        boolean found = false;

        printSearchHeader();

        String format = "%n%-15s %-45s %-35s %-10s%n";

        System.out.printf(format, "ISBN", "TITLE", "AUTHOR", "STATUS");

        System.out.println("------------------------------------------------------------------------" +
                "-----------------------------------");

        for (Book book : getAllBooks()) {

            if (matches(book.getTitle(), title)) {
                printBook(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with this title.");
        }
    }

    public void searchBooksByAuthor(String author) {

        boolean found = false;

        printSearchHeader();

        String format = "%n%-15s %-45s %-35s %-10s%n";

        System.out.printf(format, "ISBN", "TITLE", "AUTHOR", "STATUS");

        System.out.println("------------------------------------------------------------------------" +
                "-----------------------------------");

        for (Book book : getAllBooks()) {

            if (matches(book.getAuthor(), author)) {
                printBook(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found with this author.");
        }
    }
}
