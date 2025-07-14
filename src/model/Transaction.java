package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transaction {
    private final String bookIsbn;
    private final String borrowerId;
    private final LocalDate borrowDate;
    private LocalDate dueDate;  // Changed from returnDate to dueDate for clarity
    private LocalDate returnDate;
    private String status; // "BORROWED", "RETURNED", "OVERDUE"
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Transaction(String bookIsbn, String borrowerId, LocalDate borrowDate, 
                      LocalDate dueDate, String status) {
        if (bookIsbn == null || borrowerId == null) {
            throw new IllegalArgumentException("ISBN and borrower ID required");
        }
        this.bookIsbn = bookIsbn;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Constructor for new borrowings (auto-sets dates)
    public Transaction(String bookIsbn, String borrowerId, int loanDays) {
        this(bookIsbn, borrowerId, 
             LocalDate.now(), 
             LocalDate.now().plusDays(loanDays), 
             "BORROWED");
    }

    // Getters
    public String getBookIsbn() { return bookIsbn; }
    public String getBorrowerId() { return borrowerId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getStatus() { return status; }

    // Setters
    public void setReturnDate(LocalDate returnDate) { 
        this.returnDate = returnDate; 
    }
    
    public void setStatus(String status) {
        if (!List.of("BORROWED", "RETURNED", "OVERDUE").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

    public boolean isOverdue() {
        return status.equals("BORROWED") && dueDate.isBefore(LocalDate.now());
    }

    // File serialization
    public String toFileString() {
        return String.join("|",
            bookIsbn,
            borrowerId,
            borrowDate.format(formatter),
            dueDate.format(formatter),
            returnDate != null ? returnDate.format(formatter) : "null",
            status
        );
    }

    public static Transaction fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Transaction(
            parts[0], parts[1], 
            LocalDate.parse(parts[2], formatter),
            LocalDate.parse(parts[3], formatter),
            parts[5]
        );
    }

    @Override
    public String toString() {
        return String.format(
            "ISBN: %s | Borrower: %s | Borrowed: %s | Due: %s | Returned: %s | Status: %s",
            bookIsbn, borrowerId,
            borrowDate.format(formatter),
            dueDate.format(formatter),
            returnDate != null ? returnDate.format(formatter) : "N/A",
            status
        );
    }
}