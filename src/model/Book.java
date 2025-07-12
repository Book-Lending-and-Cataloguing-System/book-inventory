package model;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int year;
    private String publisher;
    private String shelfLocation;

    // Constructor
    public Book(String title, String author, String isbn, String category, int year, String publisher, String shelfLocation) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.year = year;
        this.publisher = publisher;
        this.shelfLocation = shelfLocation;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }
    public int getYear() { return year; }
    public String getPublisher() { return publisher; }
    public String getShelfLocation() { return shelfLocation; }

    // Save book as a file-friendly string
    public String toFileString() {
        return String.join("||",
            title,
            author,
            isbn,
            category,
            String.valueOf(year),
            publisher,
            shelfLocation
        );
    }

    // Recreate a Book from a file line
    public static Book fromFileString(String line) {
        String[] parts = line.split("\\|\\|");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid book data: " + line);
        }

        String title = parts[0];
        String author = parts[1];
        String isbn = parts[2];
        String category = parts[3];
        int year = Integer.parseInt(parts[4]);
        String publisher = parts[5];
        String shelf = parts[6];

        return new Book(title, author, isbn, category, year, publisher, shelf);
    }


    // toString for readable display
    @Override
    public String toString() {
        return String.format(
            "Title: %s\nAuthor: %s\nISBN: %s\nCategory: %s\nYear: %d\nPublisher: %s\nShelf Location: %s\n",
            title, author, isbn, category, year, publisher, shelfLocation
        );
    }

    
    // equals based on ISBN
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return isbn.equalsIgnoreCase(other.isbn);
    }

    // hashCode based on ISBN
    @Override
    public int hashCode() {
        return Objects.hash(isbn.toLowerCase());
    }

    
}
