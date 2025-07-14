package reports;

import datastructures.BookInventory;
import datastructures.BorrowerRegistry;
import datastructures.LendingTracker;
import model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private BookInventory bookInventory;
    private BorrowerRegistry borrowerRegistry;
    private LendingTracker lendingTracker;

    public ReportGenerator(BookInventory bookInventory, BorrowerRegistry borrowerRegistry, LendingTracker lendingTracker) {
        this.bookInventory = bookInventory;
        this.borrowerRegistry = borrowerRegistry;
        this.lendingTracker = lendingTracker;
    }

    // Generate report for most borrowed books in the past month
    public void generateMostBorrowedBooksReport() {
        Map<String, Integer> borrowCount = new HashMap<>();

        for (Transaction transaction : lendingTracker.getTransactions()) {
            if (transaction.getStatus().equals("returned") && isWithinLastMonth(transaction.getBorrowDate())) {
                String bookISBN = transaction.getBookISBN();
                borrowCount.put(bookISBN, borrowCount.getOrDefault(bookISBN, 0) + 1);
            }
        }

        System.out.println("Most Borrowed Books in the Past Month:");
        borrowCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by borrow count
                .limit(5) // Top 5
                .forEach(entry -> {
                    String bookISBN = entry.getKey();
                    int count = entry.getValue();
                    System.out.println("Book ISBN: " + bookISBN + ", Borrowed Count: " + count);
                });
    }

    // Generate report for borrowers with highest outstanding fines
    public void generateFinesReport() {
        Map<String, Double> finesMap = new HashMap<>();

        for (String borrowerId : borrowerRegistry.getBorrowers().keySet()) {
            double fines = borrowerRegistry.getBorrower(borrowerId).getFinesOwed();
            finesMap.put(borrowerId, fines);
        }

        System.out.println("Borrowers with Highest Outstanding Fines:");
        finesMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by fines owed
                .limit(5) // Top 5
                .forEach(entry -> {
                    String borrowerId = entry.getKey();
                    double fines = entry.getValue();
                    System.out.println("Borrower ID: " + borrowerId + ", Fines Owed: " + fines);
                });
    }

    // Generate report for inventory distribution across categories
    public void generateInventoryDistributionReport() {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (String category : bookInventory.getBooksByCategory().keySet()) {
            int count = bookInventory.getBooksByCategory().get(category).size();
            categoryCount.put(category, count);
        }

        System.out.println("Inventory Distribution Across Categories:");
        categoryCount.forEach((category, count) -> {
            System.out.println("Category: " + category + ", Number of Books: " + count);
        });
    }

    // Helper method to check if a date is within the last month
    private boolean isWithinLastMonth(Date date) {
        // Implement logic to check if the date is within the last month
        // This can be done using Calendar or LocalDate classes
        return true; // Placeholder
    }
}
