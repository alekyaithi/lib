package com.example.library;

public class LibraryApp {
    public static void main(String[] args) {
        try {
            LibraryService service = new LibraryService();

            // Add Books
            service.addBook("The Alchemist", "Paulo Coelho");
            service.addBook("Clean Code", "Robert C. Martin");

            // Add Members
            service.addMember("Ragini");
            service.addMember("Ananya");

            // List Books
            service.listBooks();

            // Issue and Return
            service.issueBook(1, 1);
            service.listBooks();
            service.returnBook(1);
            service.listBooks();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
