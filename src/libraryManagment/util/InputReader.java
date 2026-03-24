package libraryManagment.util;

import libraryManagment.model.EmployeeRole;

import java.util.Scanner;

public class InputReader {

    private Scanner scanner = new Scanner(System.in);

    public int readInt() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Input should be a number");
            }
        }
    }

    public String readName(boolean employee) {
        if (employee) {
            System.out.println("Please type your name: ");
        } else {
            System.out.println("Please enter member's name: ");
        }

        while (true) {
            String name = scanner.nextLine();

            if (name.matches("[a-zA-Z]+")) {
                return name;
            }

            System.out.println("Name must contain only letters.");
        }
    }

    public String readPassword(boolean confirm) {
        System.out.println("Please enter your password: ");

        while (true) {
            String password = scanner.nextLine();

            if (password.length() < 4) {
                System.out.println("Password is too short. Password must be bigger than four symbols.");
                System.out.println("Please enter your password: ");
                continue;
            }

            if (confirm) {
                System.out.println("Please confirm your password: ");
                String confirmPassword = scanner.nextLine();

                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords does not match. Please try again");
                    continue;
                }
            }
            return password;
        }
    }

    public EmployeeRole readEmployeeRole() {
        while (true) {
            System.out.println("Available roles:");
            EmployeeRole[] roles = EmployeeRole.values();

            try {
                for (int i = 0; i < roles.length; i++) {
                    System.out.println((i + 1) + " " + roles[i].name().toLowerCase());
                }

                System.out.println("Choose account role: ");
                int choice = readInt();

                return roles[choice - 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input. Please choose from available options.");
            }
        }
    }

    public String readIsbn() {

        while (true) {

            System.out.println("Please enter ISBN: ");
            String isbn = scanner.nextLine();

            if (isbn.matches("^\\d{3}+(-\\d{4}+)$")) {
                return isbn;
            }

            System.out.println("ISBN format is: 000|-|0000.");
        }
    }


    public String readTitle(boolean editMode) {

        if (editMode) {
            System.out.println("Enter new title (press Enter to keep current): ");
            return scanner.nextLine().trim();
        }

        while (true) {
            System.out.println("Please enter title:");
            String title = scanner.nextLine().trim();

            if (!title.isBlank()) {
                return title;
            }

            System.out.println("Title cannot be empty.");
        }
    }

    public String readAuthor(boolean editMode) {

        if (editMode) {
            System.out.println("Enter new author (press Enter to keep current): ");
            return scanner.nextLine().trim();
        }

        while (true) {
            System.out.println("Please enter author:");
            String author = scanner.nextLine().trim();

            if (!author.isBlank()) {
                return author;
            }

            System.out.println("Author cannot be empty.");
        }
    }

    public int readId(boolean employee) {
        while (true) {

            if (employee) {
                System.out.println("Please enter employee's ID: ");
            } else {
                System.out.println("Please enter member's ID: ");
            }

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Id be should be a number.");
            }
        }
    }

    public String readSearchText(String message) {
        System.out.println(message);

        return scanner.nextLine().trim();
    }
}
