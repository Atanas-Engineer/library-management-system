package libraryManagment.service;

import libraryManagment.core.Library;
import libraryManagment.model.Book;
import libraryManagment.model.Member;
import libraryManagment.storage.FileStorage;

import java.util.List;

public class MemberService {
    private Library library;
    private EmployeeService employeeService;
    private FileStorage storage;

    public void setLibrary(Library library) {
        this.library = library;
    }

    public MemberService(Library library, EmployeeService employeeService, FileStorage storage) {
        this.library = library;
        this.employeeService = employeeService;
        this.storage = storage;
    }

    public void registerMember(String name) {
        Member member = new Member(name);
        library.getMembers().add(member);

        System.out.println("Member registered successfully");
    }

    public void deleteMember(int id) {
        Member member = findMemberById(id);

        if (member == null) {
            System.out.println("No member registered with this ID.");
            return;
        }

        System.out.println("\nMember information:");
        System.out.println("ID: " + member.getId());
        System.out.println("Name: " + member.getName());

        if (!member.getBorrowedBooks().isEmpty()) {
            printBooksToReturn(member);
            System.out.println("Return the books before deleting.");
            return;
        }

        library.getMembers().remove(member);
        System.out.println("\nMember deleted successfully.");
    }

    public void listMembers() {
        for (Member member : library.getMembers()) {
            System.out.println(member);
        }
    }

    public List<Member> getAllMembers() {
        return library.getMembers();
    }

    public Member findMemberById(int memberId) {
        for (Member member : library.getMembers()) {
            if (member.getId() == memberId) {
                return member;
            }
        }
        return null;
    }

    public void syncMemberIdCounter() {
        int maxId = 0;

        for (Member member : library.getMembers()) {
            if (member.getId() > maxId) {
                maxId = member.getId();
            }
        }

        Member.setNextId(maxId + 1);
    }

    public void printBorrowedBooksById(int memberId) {
        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found");
            return;
        }

        if (member.getBorrowedBooks().isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }

        System.out.println("Borrowed books:");

        for (Book book : member.getBorrowedBooks()) {
            System.out.printf(" - %s by %s (ISBN: %s)%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn());
        }
    }

    public void printBooksToReturn(Member member) {
        List<Book> borrowedBooks = member.getBorrowedBooks();

        if (borrowedBooks.isEmpty()) {
            System.out.println("\nNo borrowed books.");
            return;
        }

        System.out.println("\nBorrowed books: ");

        for (Book book : borrowedBooks) {
            System.out.printf(" - %s by %s (ISBN: %s)%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn());
        }
    }

    private boolean matches(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }

    private void printSearchHeader() {
        System.out.println("\nSearch results:");
    }

    private void printMember(Member member) {
        System.out.printf("%-10s %-20s %-15s%n",
                member.getId(),
                member.getName(),
                member.getBorrowedBooks().size());
    }

    public void searchMemberByName(String name) {

        if (name == null || name.isBlank()) {
            System.out.println("Search cancelled.");
            return;
        }

        boolean found = false;

        printSearchHeader();

        String format = "%n%-10s %-20s %-15s%n";

        System.out.printf(format, "ID", "NAME", "BOOKS");
        System.out.println("--------------------------------------");

        for (Member member : getAllMembers()) {

            if (matches(member.getName(), name)) {
                printMember(member);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No member found.");
        }
    }

    public void showBorrowHistory(int memberId) {
        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.println("Borrow history for " + member.getName() + ":");
        member.printBorrowHistory();
    }
}
