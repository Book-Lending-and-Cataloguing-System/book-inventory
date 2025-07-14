package reports;

import datastructures.BookInventory;
import java.util.List;
import model.Book;

public class ReportGenerator {
    private final BookInventory inventory;
// Example category
// Call the method with the category argument
    // Replace with the actual category you want to query
    // Assuming you have an instance of BookInventory
    // Assuming you have an instance of BookInventory


    public ReportGenerator(BookInventory inventory) {
        this.inventory = inventory;
    
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void generateInventoryDistributionReport() {
        System.out.println("Inventory Distribution Across Categories:");

        // Get all categories from the inventory
        for (String category : inventory.getAllCategories()) {
            List<Book> books = inventory.getBooksByCategory(category);
            System.out.println("Category: " + category + ", Count: " + books.size());
        }
    }
}
