package utils;

import model.Book;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("001", "Java Basics", "John Doe", "Programming", 2015, "Pearson", "A1"));
        books.add(new Book("002", "Algorithms", "Alice Smith", "CS", 2012, "O'Reilly", "B3"));
        books.add(new Book("003", "Networks", "Bob Ray", "Networking", 2018, "Cisco Press", "C2"));
        books.add(new Book("004", "Database Systems", "Dana Jones", "Databases", 2010, "McGraw Hill", "D1"));
        books.add(new Book("005", "AI for All", "Ada Lovelace", "AI", 2022, "MIT Press", "E1"));

        // === Test Sorting ===
        System.out.println("📚 Before Sorting by Title:");
        books.forEach(System.out::println);

        SortUtil.mergeSortByTitle(books);
        System.out.println("\n📚 After Sorting by Title (Merge Sort):");
        books.forEach(System.out::println);

        SortUtil.selectionSortByYear(books);
        System.out.println("\n📆 After Sorting by Year (Selection Sort):");
        books.forEach(System.out::println);

        // === Test Searching ===
        System.out.println("\n🔍 Searching for title 'Algorithms' (binary):");
        Book b1 = SearchUtil.binarySearchByTitle(books, "Algorithms");
        System.out.println(b1 != null ? b1 : "Not found.");

        System.out.println("\n🔍 Searching for ISBN '003' (linear):");
        Book b2 = SearchUtil.linearSearchByIsbn(books, "003");
        System.out.println(b2 != null ? b2 : "Not found.");

        System.out.println("\n🔍 Searching for books by author 'Ada':");
        List<Book> results = SearchUtil.searchByAuthor(books, "Ada");
        results.forEach(System.out::println);
    }
}
