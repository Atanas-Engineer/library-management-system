    package libraryManagment.app;

    import libraryManagment.core.Library;
    import libraryManagment.service.BookService;
    import libraryManagment.service.EmployeeService;
    import libraryManagment.service.MemberService;
    import libraryManagment.storage.FileStorage;
    import libraryManagment.ui.ConsoleUi;
    import libraryManagment.util.InputReader;

    public class Main {
        public static void main(String[] args) {

            FileStorage storage = new FileStorage();

            Library library = new Library();

            InputReader inputReader = new InputReader();

            EmployeeService employeeService = new EmployeeService(library, storage);
            MemberService memberService = new MemberService(library, employeeService, storage);
            BookService bookService = new BookService(library, employeeService, memberService,
                    storage, inputReader);

            ConsoleUi consoleUi = new ConsoleUi(employeeService, bookService, memberService, library,storage);

            consoleUi.start();
        }
    }
