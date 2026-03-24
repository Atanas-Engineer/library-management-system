package libraryManagment.storage;

import libraryManagment.core.Library;

import java.io.*;

public class FileStorage {

    private final String FILE_PATH = "library.dat";
    private final String TESTING_FILE = "test_library.dat";

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public String getTESTING_FILE() {
        return TESTING_FILE;
    }

    public void saveLibrary(Library library) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {

            oos.writeObject(library);
            System.out.println("Library saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving library: " + e.getMessage());
        }
    }

    public Library loadLibrary(String fileName) {

        if (!fileExists(fileName)) {
            System.out.println("\nNo saved library found.");
            return new Library();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            Library library = (Library) ois.readObject();
            System.out.println("\nLoading from: " + fileName);
            System.out.println("Library loaded successfully.");
            return library;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading library: " + e.getMessage());
            return new Library();
        }
    }

    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void deleteSave(String fileName) {
        File file = new File(fileName);

        if (fileExists(fileName)) {
            file.delete();
            System.out.println("Saved library deleted.");
        } else {
            System.out.println("No save file found.");
        }
    }
}
