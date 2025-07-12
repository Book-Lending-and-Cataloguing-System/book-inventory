package utils;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {

    /**
     * Binary Search by Title (Assumes list is sorted by title)
     * Time: O(log n), Space: O(1)
     */
    public static Book binarySearchByTitle(List<Book> books, String title) {
        if (books == null || books.isEmpty() || title == null) return null;

        int left = 0;
        int right = books.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            Book midBook = books.get(mid);
            int comparison = midBook.getTitle().compareToIgnoreCase(title);

            if (comparison == 0) return midBook;
            if (comparison < 0) left = mid + 1;
            else right = mid - 1;
        }

        return null;
    }

    /**
     * Linear Search by ISBN
     * Time: O(n), Space: O(1)
     */
    public static Book linearSearchByIsbn(List<Book> books, String isbn) {
        if (books == null || books.isEmpty() || isbn == null) return null;

        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }

        return null;
    }

    /**
     * Linear Search by Partial Author Name
     * Returns list of all matching books
     */
    public static List<Book> searchByAuthor(List<Book> books, String authorKeyword) {
        List<Book> result = new ArrayList<>();

        if (books == null || books.isEmpty() || authorKeyword == null) return result;

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(authorKeyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Search books by publisher (partial match)
     */
    public static List<Book> searchByPublisher(List<Book> books, String publisherKeyword) {
        List<Book> result = new ArrayList<>();
        if (books == null || books.isEmpty() || publisherKeyword == null) return result;

        for (Book book : books) {
            if (book.getPublisher().toLowerCase().contains(publisherKeyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Search books by category (exact or partial match)
     */
    public static List<Book> searchByCategory(List<Book> books, String categoryKeyword) {
        List<Book> result = new ArrayList<>();
        if (books == null || books.isEmpty() || categoryKeyword == null) return result;

        for (Book book : books) {
            if (book.getCategory().toLowerCase().contains(categoryKeyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Search books by publication year
     */
    public static List<Book> searchByYear(List<Book> books, int year) {
        List<Book> result = new ArrayList<>();
        if (books == null || books.isEmpty()) return result;

        for (Book book : books) {
            if (book.getYear() == year) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Flexible keyword search (searches title, author, category, publisher)
     */
    public static List<Book> keywordSearch(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        if (books == null || books.isEmpty() || keyword == null) return result;

        String lowerKeyword = keyword.toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerKeyword)
                    || book.getAuthor().toLowerCase().contains(lowerKeyword)
                    || book.getCategory().toLowerCase().contains(lowerKeyword)
                    || book.getPublisher().toLowerCase().contains(lowerKeyword)) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Combined search by Title and Author
     */
    public static List<Book> searchByTitleAndAuthor(List<Book> books, String titleKeyword, String authorKeyword) {
        List<Book> result = new ArrayList<>();
        if (books == null || books.isEmpty() || titleKeyword == null || authorKeyword == null) return result;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(titleKeyword.toLowerCase())
                    && book.getAuthor().toLowerCase().contains(authorKeyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Suggest similar books by partial title when exact title not found
     */
    public static List<Book> suggestByTitle(List<Book> books, String titleKeyword) {
        return searchByPartialTitle(books, titleKeyword);
    }

    /**
     * Helper: Search by partial title (for suggestions or general search)
     */
    public static List<Book> searchByPartialTitle(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        if (books == null || books.isEmpty() || keyword == null) return result;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    /**
     * Justification: Binary vs Linear Search
     *
     * Binary Search:
     * - Time: O(log n)
     * - We decided to use when the list is sorted and searching by a unique, comparable field (e.g., title or ISBN)
     * - Efficient for large datasets
     *
     * Linear Search:
     * - Time: O(n)
     * - We decided to use when list is unsorted or when searching by partial matches (e.g., substring, category)
     * - More flexible, less efficient for large datasets
     *
     * Recommendation:
     * - We decided to use binary search for ISBN or title if pre-sorted
     * - We decided to use linear search for flexible keyword search, partial matches, or combined fields
     */
}
