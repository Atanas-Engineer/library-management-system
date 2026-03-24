package libraryManagment.config;

import libraryManagment.core.Library;
import libraryManagment.service.BookService;
import libraryManagment.service.EmployeeService;
import libraryManagment.service.MemberService;
import libraryManagment.storage.FileStorage;
import libraryManagment.ui.ConsoleUi;
import libraryManagment.util.InputReader;

public class AppConfig {
    public static ConsoleUi createApp() {
        FileStorage storage = new FileStorage();
        Library library = new Library();
        InputReader inputReader = new InputReader();

        EmployeeService employeeService = new EmployeeService(library, storage);
        MemberService memberService = new MemberService(library, employeeService, storage);
        BookService bookService = new BookService(library, employeeService, memberService, storage, inputReader);

        return new ConsoleUi(employeeService, bookService, memberService, library, storage);
    }
}
