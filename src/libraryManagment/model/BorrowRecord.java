package libraryManagment.model;

import java.io.Serializable;
import java.time.LocalDate;

public class BorrowRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowRecord(Book book, LocalDate borrowDate) {
        this.book = book;
        this.borrowDate = borrowDate;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }
}
