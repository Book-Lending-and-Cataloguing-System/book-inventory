package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a lending transaction in the library system.
 * Tracks book ISBN, borrower ID, borrow and return dates, and status.
 */
public class Transaction {
    private String bookIsbn;
    private String borrowerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status; 

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public Transaction(String bookIsbn, String borrowerId, LocalDate borrowDate, String status) {
        this.bookIsbn = bookIsbn;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Transaction(String bookISBN, String borrowerID, LocalDate now, String borrowed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getBookISBN() {
        return bookIsbn;
    }

    // Minimal constructor for borrowing
    public Transaction(String bookIsbn, String borrowerId) {
        this.bookIsbn = bookIsbn;
        this.borrowerId = borrowerId;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
        this.status = "borrowed";
    }

    // Getters and setters
    public String getBookIsbn() { return bookIsbn; }
    public String getBorrowerId() { return borrowerId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getStatus() { return status; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setStatus(String status) { this.status = status; }

    /**
     * Check if this transaction is overdue (more than 14 days after borrowDate).
     */
    public boolean isOverdue() {
        if (status.equals("returned")) return false;
        LocalDate today = LocalDate.now();
        return borrowDate.plusDays(14).isBefore(today);
    }

    /**
     * Readable string for console output.
     */
    @Override
    public String toString() {
        return String.format(
            "ISBN: %s | Borrower: %s | Borrowed: %s | Return: %s | Status: %s",
            bookIsbn,
            borrowerId,
            borrowDate.format(formatter),
            returnDate != null ? returnDate.format(formatter) : "N/A",
            status
        );
    }

    /**
     * Convert this transaction to a single-line string for file saving.
     */
    public String toFileString() {
        return String.join("|",
            bookIsbn,
            borrowerId,
            borrowDate.format(formatter),
            (returnDate != null ? returnDate.format(formatter) : "null"),
            status
        );
    }

    /**
     * Reconstruct a Transaction object from a file line.
     */
    public static Transaction fromFileString(String line) {
        String[] parts = line.split("\\|");
        String isbn = parts[0];
        String borrower = parts[1];
        LocalDate borrow = LocalDate.parse(parts[2], formatter);
        LocalDate ret = parts[3].equals("null") ? null : LocalDate.parse(parts[3], formatter);
        String status = parts[4];
        return new Transaction(isbn, borrower, borrow, status);
    }
}
