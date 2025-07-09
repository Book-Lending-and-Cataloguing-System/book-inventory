package model;

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

    // Getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }
    public int getYear() { return year; }
    public String getPublisher() { return publisher; }
    public String getShelfLocation() { return shelfLocation; }

    // TODO: Add toString() for readable output
    // TODO: Add equals() and hashCode() for use in HashMap/Tree
}