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


    public String toFileString() {
        String books = String.join(";", borrowedBooks); // separate ISBNs with ;
        return String.join("||",
            name,
            idNumber,
            books,
            String.valueOf(finesOwed),
            contactInfo
        );
    }

    public static Borrower fromFileString(String line) {
        String[] parts = line.split("\\|\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid borrower data: " + line);
        }

        Borrower borrower = new Borrower(parts[0], parts[1], parts[4]);
        borrower.finesOwed = Double.parseDouble(parts[3]);

        if (!parts[2].isEmpty()) {
            String[] isbns = parts[2].split(";");
            for (String isbn : isbns) {
                borrower.addBorrowedBook(isbn);
            }
        }

        return borrower;
    }

    @Override
    public String toString() {
        return String.format(
            "Name: %s\nID: %s\nContact: %s\nBooks: %s\nFines: %.2f\n",
            name, idNumber, contactInfo,
            borrowedBooks.isEmpty() ? "None" : String.join(", ", borrowedBooks),
            finesOwed
        );
    }

    public void addFine(double amount) {
        finesOwed += amount; // Method to add fines
    }
    public void payFine(double amount) {
        finesOwed -= amount; // Method to pay fines
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Borrower)) return false;
        Borrower other = (Borrower) obj;
        return idNumber.equalsIgnoreCase(other.idNumber);
    }

    @Override
    public int hashCode() {
        return idNumber.toLowerCase().hashCode();
    }

    // TODO: Add toString() for readable output
    // TODO: Add equals() and hashCode() based on idNumber
}