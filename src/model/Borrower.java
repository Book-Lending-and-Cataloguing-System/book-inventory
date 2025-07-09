package model;

import java.util.ArrayList;
import java.util.List;

public class Borrower {
    private String name;
    private String idNumber;
    private List<String> borrowedBooks; // List of ISBNs
    private double finesOwed;
    private String contactInfo;

    // Constructor
    public Borrower(String name, String idNumber, String contactInfo) {
        this.name = name;
        this.idNumber = idNumber;
        this.borrowedBooks = new ArrayList<>();
        this.finesOwed = 0.0;
        this.contactInfo = contactInfo;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getIdNumber() { return idNumber; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }
    public double getFinesOwed() { return finesOwed; }
    public String getContactInfo() { return contactInfo; }
    public void addBorrowedBook(String isbn) { borrowedBooks.add(isbn); }
    public void removeBorrowedBook(String isbn) { borrowedBooks.remove(isbn); }
    public void addFine(double fine) { finesOwed += fine; }

    // TODO: Add toString() for readable output
    // TODO: Add equals() and hashCode() based on idNumber
}