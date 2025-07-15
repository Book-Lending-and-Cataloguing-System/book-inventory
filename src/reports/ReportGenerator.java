package reports;

import model.Book;
import model.Borrower;
import model.Transaction;
import java.util.List;

public class ReportGenerator {
    public void mostBorrowedBooks(List<Transaction> transactions) {
        // TODO: Implement report for most borrowed books in the past month
        System.out.println("\n--- Most Borrowed Books (Past 30 Days) ---");
        Transaction temp = new Transaction("dummy", "dummy"); // just to call instance method
        var topBooks = temp.mostBorrowedBooksLast30Days(transactions);

        if (topBooks.isEmpty()) {
            System.out.println("No borrowing activity in the past 30 days.");
        } else {
            topBooks.forEach((isbn, count) ->
                System.out.println("ISBN: " + isbn + " | Times Borrowed: " + count));
        }
    }

    public void highestFinesOwed(List<Borrower> borrowers) {
        // TODO: Implement report for borrowers with highest fines
        System.out.println("\n--- Top 5 Borrowers by Outstanding Fines ---");

        borrowers.stream()
            .sorted((b1, b2) -> Double.compare(b2.getFinesOwed(), b1.getFinesOwed()))
            .limit(5)
            .forEach(b -> System.out.println(
                "ID: " + b.getIdNumber() +
                " | Name: " + b.getName() +
                " | Fines Owed: GH₵" + b.getFinesOwed())
            );
    }

    public void categoryDistribution(List<Book> books) {
        // TODO: Implement report for inventory distribution by category
        System.out.println("\n--- Book Inventory by Category ---");

        var categoryMap = books.stream()
            .collect(java.util.stream.Collectors.groupingBy(Book::getCategory, java.util.stream.Collectors.counting()));

        if (categoryMap.isEmpty()) {
            System.out.println("No books in inventory.");
        } else {
            categoryMap.forEach((category, count) ->
                System.out.println("Category: " + category + " | Books: " + count));
        }
    }

    // TODO: Add performance analysis (Big O/Omega for all algorithms)
}