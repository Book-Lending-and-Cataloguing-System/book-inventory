package functionality;

import model.Borrower;
import java.util.ArrayList;
import java.util.List;
import datastructures.HashMap;

public class BorrowerRegistry {
    private HashMap<String, Borrower> borrowers; // ID -> Borrower

    public BorrowerRegistry() {
        borrowers = new HashMap<>();
    }

    public void addBorrower(Borrower borrower) {
        if (borrower == null) {
            throw new IllegalArgumentException("Borrower cannot be null");
        }
        if (borrowers.containsKey(borrower.getIdNumber())) {
            throw new IllegalArgumentException("Borrower with ID " + borrower.getIdNumber() + " already exists");
        }
        borrowers.put(borrower.getIdNumber(), borrower);
    }

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

    public Borrower findBorrower(String idNumber) {
        return borrowers.get(idNumber);
    }

    public Borrower findBorrowerRecursive(String idNumber) {
        List<String> sortedIds = new ArrayList<>();
        for (String id : borrowers.keySetArray(new String[0])) {
            sortedIds.add(id);
        }
        sortedIds.sort(String::compareTo);
        return recursiveBinarySearch(idNumber, sortedIds, 0, sortedIds.size() - 1);
    }

    private Borrower recursiveBinarySearch(String targetId, List<String> sortedIds, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        String midId = sortedIds.get(mid);
        int comparison = targetId.compareTo(midId);

        if (comparison == 0) {
            return borrowers.get(midId);
        } else if (comparison < 0) {
            return recursiveBinarySearch(targetId, sortedIds, low, mid - 1);
        } else {
            return recursiveBinarySearch(targetId, sortedIds, mid + 1, high);
        }
    }

    public List<Borrower> getAllBorrowers() {
        List<Borrower> allBorrowers = new ArrayList<>();
        for (Borrower b : borrowers.valuesArray(new Borrower[0])) {
            allBorrowers.add(b);
        }
        return allBorrowers;
    }

    public int size() {
        return borrowers.size();
    }

    public void clear() {
        borrowers.clear();
    }

    public String getDataStructureJustification() {
        return "HashMap provides O(1) average time complexity for insert, delete, and lookup operations, " +
                "which are our most common operations. While TreeMap would provide O(log n) operations " +
                "and maintain sorted order, we don't need constant sorting and can sort when needed " +
                "(like for the recursive search implementation). HashMap is more memory efficient " +
                "for our primary use cases.";
    }

    public void loadBorrowers(List<Borrower> borrowerList) {
        for (Borrower borrower : borrowerList) {
            borrowers.put(borrower.getIdNumber(), borrower);
        }
    }
}

