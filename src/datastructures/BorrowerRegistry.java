package datastructures;

import model.Borrower;
import java.util.HashMap;

public class BorrowerRegistry {
    private HashMap<String, Borrower> borrowers; // ID -> Borrower

    public BorrowerRegistry() {
        borrowers = new HashMap<>();
    }

    public void addBorrower(Borrower borrower) {
        borrowers.put(borrower.getIdNumber(), borrower);
    }

    public Borrower findBorrower(String idNumber) {
        // TODO: Implement recursive search
        return borrowers.get(idNumber);
    }

    // TODO: Implement removeBorrower()
    // TODO: Implement recursive search for borrower by ID
    // TODO: Justify use of HashMap (O(1) lookup) vs. Tree (O(log n) for sorted traversal)
}