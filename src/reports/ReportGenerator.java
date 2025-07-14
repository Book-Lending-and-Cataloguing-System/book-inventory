package reports;

import model.Book;
import model.Borrower;
import model.Transaction;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {
    private static final int REPORT_PERIOD_DAYS = 30;

    /**
     * Generates report of most borrowed books in the last 30 days
     * Time Complexity: O(n) where n is number of transactions
     */
    public void mostBorrowedBooks(List<Transaction> transactions) {
        LocalDate cutoffDate = LocalDate.now().minusDays(REPORT_PERIOD_DAYS);
        
        Map<String, Long> bookBorrowCounts = transactions.stream()
            .filter(t -> t.getBorrowDate().isAfter(cutoffDate))
            .collect(Collectors.groupingBy(
                Transaction::getBookIsbn,
                Collectors.counting()
            ));
        
        System.out.println("\n=== MOST BORROWED BOOKS (LAST 30 DAYS) ===");
        bookBorrowCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5) // Top 5
            .forEach(entry -> System.out.printf("- ISBN: %s | Borrows: %d\n", 
                entry.getKey(), entry.getValue()));
    }

    /**
     * Generates report of borrowers with highest fines
     * Time Complexity: O(n log n) due to sorting
     */
    public void highestFinesOwed(List<Borrower> borrowers) {
        System.out.println("\n=== BORROWERS WITH HIGHEST FINES ===");
        borrowers.stream()
            .filter(b -> b.getFinesOwed() > 0)
            .sorted(Comparator.comparing(Borrower::getFinesOwed).reversed())
            .limit(5) // Top 5
            .forEach(b -> System.out.printf("- %s (ID: %s) | Owed: $%.2f\n",
                b.getName(), b.getIdNumber(), b.getFinesOwed()));
    }

    /**
     * Generates inventory distribution by category
     * Time Complexity: O(n) where n is number of books
     */
    public void categoryDistribution(List<Book> books) {
        System.out.println("\n=== INVENTORY BY CATEGORY ===");
        books.stream()
            .collect(Collectors.groupingBy(
                Book::getCategory,
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(entry -> System.out.printf("- %s: %d books (%.1f%%)\n",
                entry.getKey(), entry.getValue(),
                (entry.getValue() * 100.0 / books.size())));
    }

    /**
     * Performance analysis of implemented algorithms
     * Time Complexity: O(1) - just prints information
     */
    public void printPerformanceAnalysis() {
        System.out.println("\n=== ALGORITHM PERFORMANCE ANALYSIS ===");
        System.out.println("1. Binary Search (Title):");
        System.out.println("   - Best Case: O(1)");
        System.out.println("   - Worst Case: O(log n)");
        System.out.println("   - Space: O(1)");
        
        System.out.println("\n2. Linear Search (ISBN):");
        System.out.println("   - Best/Worst Case: O(n)");
        System.out.println("   - Space: O(1)");
        
        System.out.println("\n3. Merge Sort (Books by Title):");
        System.out.println("   - Best/Worst Case: O(n log n)");
        System.out.println("   - Space: O(n)");
        
        System.out.println("\n4. Priority Queue (Overdue Books):");
        System.out.println("   - Insertion: O(log n)");
        System.out.println("   - Retrieval: O(1)");
        System.out.println("   - Space: O(n)");
    }
}