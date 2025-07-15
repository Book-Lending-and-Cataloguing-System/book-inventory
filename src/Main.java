import datastructures.*;
import utils.*;
import reports.*;
import model.Book;
import model.Borrower;
import model.Transaction;
import java.util.ArrayList;
import java.util.List;
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
        registry.loadBorrowers(fileHandler.loadBorrowers());
        tracker.loadTransactions(fileHandler.loadTransactions());
        System.out.println("Books, borrowers, and transactions loaded.");

        boolean running = true;

        while (running) {
            System.out.println("\n=== EBENEZER COMMUNITY LIBRARY ===");
            System.out.println("1. Book Inventory");
            System.out.println("2. Borrower Registry");
            System.out.println("3. Transactions");
            System.out.println("4. Reports");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    bookInventoryMenu(scanner, inventory);
                    break;
                case "2":
                    borrowerRegistryMenu(scanner, registry);
                    break;
                case "3":
                    transactionMenu(scanner, tracker, inventory, registry);
                    break;
                case "4":
                    reportsMenu(scanner, reporter, tracker, registry, inventory, monitor);
                    break;
                case "0":
                    fileHandler.saveBooks(new ArrayList<>(inventory.getAllBooks()));
                    fileHandler.saveBorrowers(new ArrayList<>(registry.getAllBorrowers()));
                    fileHandler.saveTransactions(tracker.getAllTransactions());
                    System.out.println("Books, borrowers, and transactions saved to file. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }

        scanner.close();
    }

    // === Book Inventory Menu and Helpers ===

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

    private static void filterByPrefix(Scanner scanner, BookInventory inventory) {
        System.out.print("Enter category prefix: ");
        String prefix = scanner.nextLine().trim();
        TreeMap<String, Book> filtered = inventory.filterByCategoryPrefix(prefix);
        if (filtered.isEmpty()) {
            System.out.println("No matching categories.");
        } else {
            for (Book book : filtered.values()) {
                System.out.println(book);
            }
        }
    }

    // === Borrower Registry Menu and Helpers ===

    private static void borrowerRegistryMenu(Scanner scanner, BorrowerRegistry registry) {
        boolean managing = true;

        while (managing) {
            System.out.println("\n--- Borrower Registry Menu ---");
            System.out.println("1. Add Borrower");
            System.out.println("2. Remove Borrower");
            System.out.println("3. Search Borrower by ID (direct)");
            System.out.println("4. Search Borrower by ID (recursive)");
            System.out.println("5. List All Borrowers");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addBorrower(scanner, registry);
                    break;
                case "2":
                    removeBorrower(scanner, registry);
                    break;
                case "3":
                    searchBorrowerDirect(scanner, registry);
                    break;
                case "4":
                    searchBorrowerRecursive(scanner, registry);
                    break;
                case "5":
                    listAllBorrowers(registry);
                    break;
                case "0":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    private static void addBorrower(Scanner scanner, BorrowerRegistry registry) {
        try {
            System.out.println("\n--- Add New Borrower ---");
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("ID Number: ");
            String id = scanner.nextLine().trim();
            System.out.print("Contact Info: ");
            String contact = scanner.nextLine().trim();

            Borrower borrower = new Borrower(name, id, contact);
            registry.addBorrower(borrower);
            System.out.println("Borrower added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeBorrower(Scanner scanner, BorrowerRegistry registry) {
        System.out.print("Enter ID of borrower to remove: ");
        String id = scanner.nextLine().trim();
        try {
            boolean removed = registry.removeBorrower(id);
            if (removed) {
                System.out.println("Borrower removed successfully.");
            } else {
                System.out.println("Borrower not found.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Cannot remove borrower: " + e.getMessage());
        }
    }

    private static void searchBorrowerDirect(Scanner scanner, BorrowerRegistry registry) {
        System.out.print("Enter ID to search: ");
        String id = scanner.nextLine().trim();
        Borrower b = registry.findBorrower(id);
        if (b != null) {
            System.out.println("Borrower found: " + b);
        } else {
            System.out.println("Borrower not found.");
        }
    }

    private static void searchBorrowerRecursive(Scanner scanner, BorrowerRegistry registry) {
        System.out.print("Enter ID to search: ");
        String id = scanner.nextLine().trim();
        Borrower b = registry.findBorrowerRecursive(id);
        if (b != null) {
            System.out.println("Borrower found (recursive): " + b);
        } else {
            System.out.println("Borrower not found.");
        }
    }

    private static void listAllBorrowers(BorrowerRegistry registry) {
        System.out.println("\n--- All Borrowers ---");
        List<Borrower> borrowers = registry.getAllBorrowers();
        if (borrowers.isEmpty()) {
            System.out.println("No borrowers registered.");
        } else {
            for (Borrower b : borrowers) {
                System.out.println(b);
            }
        }
    }

    // === Transaction Menu ===

    private static void transactionMenu(Scanner scanner, LendingTracker tracker, BookInventory inventory, BorrowerRegistry registry) {
        boolean managing = true;
        while (managing) {
            System.out.println("\n--- Transaction Menu ---");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. List Transactions");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    borrowBook(scanner, tracker, inventory, registry);
                    break;
                case "2":
                    returnBook(scanner, tracker);
                    break;
                case "3":
                    tracker.listTransactions();
                    break;
                case "0":
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void borrowBook(Scanner scanner, LendingTracker tracker, BookInventory inventory, BorrowerRegistry registry) {
        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Enter Borrower ID: ");
        String borrowerId = scanner.nextLine().trim();
        System.out.print("Enter Borrow Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();

        if (inventory.findBook(isbn) == null) {
            System.out.println("Book not found.");
            return;
        }

        if (registry.findBorrower(borrowerId) == null) {
            System.out.println("Borrower not found.");
            return;
        }

        try {
            Transaction txn = new Transaction(isbn, borrowerId, java.time.LocalDate.parse(dateStr));
            tracker.addTransaction(txn);
            System.out.println("Book borrowed successfully.");
        } catch (Exception e) {
            System.out.println("Failed to borrow book: " + e.getMessage());
        }
    }

    private static void returnBook(Scanner scanner, LendingTracker tracker) {
        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Enter Borrower ID: ");
        String borrowerId = scanner.nextLine().trim();
        System.out.print("Enter Return Date (YYYY-MM-DD): ");
        String returnDate = scanner.nextLine().trim();

        boolean success = tracker.returnBook(isbn, borrowerId, returnDate);
        if (success) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Matching transaction not found.");
        }
    }
    private static void reportsMenu(Scanner scanner, ReportGenerator reporter, LendingTracker tracker, BorrowerRegistry registry, BookInventory inventory, OverdueMonitor monitor) {  // 👈 New: create a monitor instance
        boolean reporting = true;
        while (reporting) {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Most Borrowed Books (Last 30 Days)");
            System.out.println("2. Top Borrowers by Fines");
            System.out.println("3. Inventory by Category");
            System.out.println("4. Run Overdue Check and Update Fines");  // 👈 New option
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    reporter.mostBorrowedBooks(tracker.getAllTransactions());
                    break;
                case "2":
                    reporter.highestFinesOwed(registry.getAllBorrowers());
                    break;
                case "3":
                    reporter.categoryDistribution(new ArrayList<>(inventory.getAllBooks()));
                    break;
                case "4":
                    monitor.checkOverdue(tracker.getAllTransactions());
                    monitor.updateFines(registry.getAllBorrowers());
                    System.out.println("Overdue check complete. Fines updated.");
                    break;
                case "0":
                    reporting = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
}
