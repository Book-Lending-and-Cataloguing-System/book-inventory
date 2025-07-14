import datastructures.*;
import utils.*;
import model.*;
import reports.ReportGenerator;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static BookInventory inventory = new BookInventory();
    private static BorrowerRegistry registry = new BorrowerRegistry();
    private static LendingTracker tracker = new LendingTracker();
    private static FileHandler fileHandler = new FileHandler();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadInitialData();
        
        while (true) {
            printMainMenu();
            int choice = getIntInput("Select: ", 0, 4);
            
            switch (choice) {
                case 1 -> handleBookInventory();
                case 2 -> handleBorrowerManagement();
                case 3 -> handleLendingOperations();
                case 4 -> generateReports();
                case 0 -> { 
                    saveAllData(); 
                    System.exit(0); 
                }
            }
        }
    }

    private static Object generateReports() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateReports'");
    }

    private static Object handleLendingOperations() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLendingOperations'");
    }

    private static Object handleBorrowerManagement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBorrowerManagement'");
    }

    // === Initialization Methods ===
    private static void loadInitialData() {
        try {
            inventory.loadBooks(fileHandler.loadBooks());
            registry.loadBorrowers(fileHandler.loadBorrowers());
            tracker.loadTransactions(fileHandler.loadTransactions());
            System.out.println("System initialized successfully");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static void saveAllData() {
        try {
            fileHandler.saveBooks(inventory.getAllBooks());
            fileHandler.saveBooks("data/borrowers.txt", registry.getAllBorrowers());
            fileHandler.saveTransactions("data/transactions.txt", tracker.listTransactions());
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // === Menu Methods ===
    private static void printMainMenu() {
        System.out.println("\n=== WELCOME TO EBENEZER COMMUNITY LIBRARY ===");
        System.out.println("1. Book Inventory");
        System.out.println("2. Borrower Management");
        System.out.println("3. Lending Operations");
        System.out.println("4. Reports");
        System.out.println("0. Exit");
    }

    private static void handleBookInventory() {
        while (true) {
            System.out.println("\n--- BOOK INVENTORY ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List All Books");
            System.out.println("4. Search Books");
            System.out.println("0. Back");
            
            int choice = getIntInput("Choose: ", 0, 4);
            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> inventory.listBooks();
                case 4 -> searchBooks();
                case 0 -> { return; }
            }
        }
    }

    private static Object removeBook() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeBook'");
    }

    // === Core Operations ===
    private static void addBook() {
        try {
            System.out.println("\n--- ADD BOOK ---");
            String title = getNonEmptyInput("Title: ");
            String author = getNonEmptyInput("Author: ");
            String isbn = getNonEmptyInput("ISBN: ");
            String category = getNonEmptyInput("Category: ");
            int year = getIntInput("Year: ", 1800, LocalDate.now().getYear());
            String publisher = getNonEmptyInput("Publisher: ");
            String shelf = getNonEmptyInput("Shelf Location: ");

            Book book = new Book(title, author, isbn, category, year, publisher, shelf);
            inventory.addBook(book);
            System.out.println("Book added successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        System.out.println("\nSearch by:");
        System.out.println("1. Title (Binary Search)");
        System.out.println("2. ISBN (Linear Search)");
        System.out.println("3. Author");
        int choice = getIntInput("Choose: ", 1, 3);
        
        String query = getNonEmptyInput("Enter search term: ");
        SearchUtil searchUtil = new SearchUtil();
        List<Book> results = new ArrayList<>();
        
        switch (choice) {
            case 1 -> results.add(searchUtil.binarySearchByTitle(inventory.getAllBooks(), query));
            case 2 -> results.add(searchUtil.linearSearchByIsbn(inventory.getAllBooks(), query));
            case 3 -> results = searchUtil.searchByAuthor(inventory.getAllBooks(), query);
        }
        
        displaySearchResults(results);
    }

    // === Utility Methods ===
    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) return input;
                System.out.printf("Please enter between %d-%d\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
        }
    }

    private static String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be empty");
        }
    }

    private static void displaySearchResults(List<Book> results) {
        results.removeIf(Objects::isNull);
        if (results.isEmpty()) {
            System.out.println("No books found");
            return;
        }
        System.out.println("\nSearch Results:");
        results.forEach(System.out::println);
    }
}