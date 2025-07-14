import datastructures.*;
import utils.*;
import reports.*;
import model.Book;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        BookInventory inventory = new BookInventory();
        BorrowerRegistry registry = new BorrowerRegistry();
        LendingTracker tracker = new LendingTracker();
        OverdueMonitor monitor = new OverdueMonitor();
        FileHandler fileHandler = new FileHandler();
        ReportGenerator reporter = new ReportGenerator();

        Scanner scanner = new Scanner(System.in);

        // ✅ Load data from file before menu starts
        inventory.loadBooks(fileHandler.loadBooks());
        System.out.println("Books loaded from file.");

        boolean running = true;

        while (running) {
            System.out.println("\n=== EBENEZER COMMUNITY LIBRARY ===");
            System.out.println("1. Book Inventory");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    bookInventoryMenu(scanner, inventory);
                    break;

                case "0":
                    // ✅ Save data before exiting
                    fileHandler.saveBooks(new ArrayList<>(inventory.getAllBooks()));
                    System.out.println("Books saved to file. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        }

        scanner.close();
    }

    // === Book Inventory Menu ===
    private static void bookInventoryMenu(Scanner scanner, BookInventory inventory) {
        boolean managing = true;

        while (managing) {
            System.out.println("\n--- Book Inventory Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List All Books");
            System.out.println("4. List Books by Category");
            System.out.println("5. Filter Categories by Prefix");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addBook(scanner, inventory);
                    break;
                case "2":
                    removeBook(scanner, inventory);
                    break;
                case "3":
                    inventory.listBooks();
                    break;
                case "4":
                    listByCategory(scanner, inventory);
                    break;
                case "5":
                    filterByPrefix(scanner, inventory);
                    break;
                case "0":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    // === Add Book ===
    private static void addBook(Scanner scanner, BookInventory inventory) {
        try {
            System.out.println("\n--- Add New Book ---");
            System.out.print("Title: ");
            String title = scanner.nextLine().trim();
            System.out.print("Author: ");
            String author = scanner.nextLine().trim();
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine().trim();
            System.out.print("Category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Publisher: ");
            String publisher = scanner.nextLine().trim();
            System.out.print("Shelf Location: ");
            String shelf = scanner.nextLine().trim();

            Book book = new Book(title, author, isbn, category, year, publisher, shelf);
            inventory.addBook(book);
            System.out.println("Book added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // === Remove Book ===
    private static void removeBook(Scanner scanner, BookInventory inventory) {
        System.out.print("Enter ISBN of book to remove: ");
        String isbn = scanner.nextLine().trim();
        boolean removed = inventory.removeBook(isbn);
        if (removed) {
            System.out.println("Book removed.");
        } else {
            System.out.println("Book not found.");
        }
    }

    // === List by Category ===
    private static void listByCategory(Scanner scanner, BookInventory inventory) {
        System.out.print("Enter category: ");
        String category = scanner.nextLine().trim();
        TreeMap<String, Book> books = inventory.getBooksByCategory(category);
        if (books.isEmpty()) {
            System.out.println("No books found in this category.");
        } else {
            for (Book book : books.values()) {
                System.out.println(book);
            }
        }
    }

    // === Filter by Category Prefix ===
    private static void filterByPrefix(Scanner scanner, BookInventory inventory) {
        System.out.print("Enter category prefix: ");
        String prefix = scanner.nextLine().trim();
        TreeMap<String, Book> filtered = inventory.filterByCategoryPrefix(prefix);
        if (filtered.isEmpty()) {
            System.out.println("No matching categories.");
        } else {
            for (Book book : filtered.values()) {
                System.out.println(book);

                System.out.println("Top Borrowed Books:");
reports.mostBorrowedBooksLast30Days(Transaction).forEach((isbn, count) -> System.out.println(isbn + ": " + count));

System.out.println("\n Top Borrowers by Fine:");
reports.topBorrowersByFines(Borrower).forEach(System.out::println);

System.out.println("\n Inventory by Category:");
reports.inventoryByCategory(books).forEach((category, count) -> System.out.println(category + ": " + count));

            }
        }
    }
}
