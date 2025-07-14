package datastructures;

    import model.Borrower;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.ArrayList;
    import java.util.List;

public class BorrowerRegistry {
    private HashMap<String, Borrower> borrowers; // ID -> Borrower

    public BorrowerRegistry() {
        borrowers = new HashMap<>();
    }

    /**
     * Adds a borrower to the registry
     * @param borrower The borrower to add
     * @throws IllegalArgumentException if borrower is null or ID already exists
     */
    public void addBorrower(Borrower borrower) {
        if (borrower == null) {
            throw new IllegalArgumentException("Borrower cannot be null");
        }
        if (borrowers.containsKey(borrower.getIdNumber())) {
            throw new IllegalArgumentException("Borrower with ID " + borrower.getIdNumber() + " already exists");
        }
        borrowers.put(borrower.getIdNumber(), borrower);
    }

    /**
     * Removes a borrower from the registry
     * @param idNumber The ID of the borrower to remove
     * @return true if borrower was removed, false if not found
     * @throws IllegalStateException if borrower has outstanding books or fines
     */
    public boolean removeBorrower(String idNumber) {
        Borrower borrower = findBorrower(idNumber);
        if (borrower == null) {
            return false;
        }

        if (!borrower.getBorrowedBooks().isEmpty()) {
            throw new IllegalStateException("Cannot remove borrower with borrowed books");
        }

        if (borrower.getFinesOwed() > 0) {
            throw new IllegalStateException("Cannot remove borrower with outstanding fines");
        }

        borrowers.remove(idNumber);
        return true;
    }

    /**
     * Finds a borrower by ID using direct hash map lookup (O(1))
     * @param idNumber The borrower ID to search for
     * @return The Borrower object or null if not found
     */
    public Borrower findBorrower(String idNumber) {
        return borrowers.get(idNumber);
    }

    /**
     * Finds a borrower by ID using recursive binary search (O(log n))
     * Note: This requires the borrower IDs to be sorted first
     * @param idNumber The borrower ID to search for
     * @return The Borrower object or null if not found
     */
    public Borrower findBorrowerRecursive(String idNumber) {
        List<String> sortedIds = new ArrayList<>(borrowers.keySet());
        sortedIds.sort(String::compareTo);
        return recursiveBinarySearch(idNumber, sortedIds, 0, sortedIds.size() - 1);
    }

    /**
     * Helper method for recursive binary search
     */
    private Borrower recursiveBinarySearch(String targetId, List<String> sortedIds, int low, int high) {
        if (low > high) {
            return null; // Base case: not found
        }

        int mid = (low + high) / 2;
        String midId = sortedIds.get(mid);
        int comparison = targetId.compareTo(midId);

        if (comparison == 0) {
            return borrowers.get(midId); // Found the borrower
        } else if (comparison < 0) {
            return recursiveBinarySearch(targetId, sortedIds, low, mid - 1); // Search left half
        } else {
            return recursiveBinarySearch(targetId, sortedIds, mid + 1, high); // Search right half
        }
    }

    /**
     * Gets all borrowers in the registry
     * @return List of all borrowers
     */
    public List<Borrower> getAllBorrowers() {
        return new ArrayList<>(borrowers.values());
    }

    /**
     * Gets the number of registered borrowers
     * @return Count of borrowers
     */
    public int size() {
        return borrowers.size();
    }

    /**
     * Clears all borrowers from the registry
     */
    public void clear() {
        borrowers.clear();
    }

    /**
     * Justification for using HashMap:
     * - Primary operation is lookup by ID which is O(1) average case in HashMap
     * - Insertion and deletion are also O(1) average case
     * - Memory overhead is slightly higher than array but provides faster access
     * - TreeMap would provide O(log n) operations and maintain sorted order, but:
     *   - Sorting isn't required for our primary operations
     *   - We can sort when needed (like for recursive search)
     *   - HashMap better fits our most common use cases
     */
    public String getDataStructureJustification() {
        return "HashMap provides O(1) average time complexity for insert, delete, and lookup operations, " +
                "which are our most common operations. While TreeMap would provide O(log n) operations " +
                "and maintain sorted order, we don't need constant sorting and can sort when needed " +
                "(like for the recursive search implementation). HashMap is more memory efficient " +
                "for our primary use cases.";
    }
}