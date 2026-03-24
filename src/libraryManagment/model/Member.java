package libraryManagment.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    private static int nextId = 1;

    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    private List<BorrowRecord> borrowHistory = new ArrayList<>();

    public Member (String name) {
        this.id = nextId++;
        this.name = name;
    }

    public static void setNextId(int nextId) {
        Member.nextId = nextId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }

    public void borrowBook (Book book) {
        borrowedBooks.add(book);
        book.setBookStatus(BookStatus.BORROWED);

        BorrowRecord record = new BorrowRecord(book, LocalDate.now());
        borrowHistory.add(record);
    }

    public void returnBook (Book book) {
        if (!borrowedBooks.contains(book)) {
            System.out.println("This member didn't borrow this book");
            return;
        }

        borrowedBooks.remove(book);
        book.setBookStatus(BookStatus.AVAILABLE);

        for (BorrowRecord record : borrowHistory) {
            if (record.getBook().equals(book) && !record.isReturned()) {
                record.setReturnDate(LocalDate.now());
                break;
            }
        }

        System.out.println("Book returned successfully.");
    }

    public void printBorrowHistory() {

        System.out.printf("%n%-20s %-15s %-15s%n", "BOOK", "BORROW DATE", "RETURN DATE");
        System.out.println("-----------------------------------------------");

        for (BorrowRecord record : borrowHistory) {
            System.out.printf("%-20s %-15s %-15s%n",
                    record.getBook().getTitle(),
                    record.getBorrowDate(),
                    record.isReturned() ? record.getReturnDate() : "Not returned");
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }
}
