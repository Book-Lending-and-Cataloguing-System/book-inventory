package model;

import java.time.LocalDate;

public class Transaction {
    private String bookIsbn;
    private String borrowerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status; // e.g., "borrowed", "returned", "overdue"

    // Constructor
    public Transaction(String bookIsbn, String borrowerId, LocalDate borrowDate, LocalDate returnDate, String status) {
        this.bookIsbn = bookIsbn;
        this.borrowerId = borrowerId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters and setters
    public String getBookIsbn() { return bookIsbn; }
    public String getBorrowerId() { return borrowerId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // TODO: Add toString() for readable output
    // TODO: Add method to check if transaction is overdue (compare returnDate with current date)
}