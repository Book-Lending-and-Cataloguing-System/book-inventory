import datastructures.*;
import utils.*;
import reports.*;

public class Main {
    public static void main(String[] args) {
        BookInventory inventory = new BookInventory();
        BorrowerRegistry registry = new BorrowerRegistry();
        LendingTracker tracker = new LendingTracker();
        OverdueMonitor monitor = new OverdueMonitor();
        FileHandler fileHandler = new FileHandler();
        ReportGenerator reporter = new ReportGenerator();

        // TODO: Implement console menu for user interaction
        // TODO: Load data from files at startup
        // TODO: Save data to files on exit
    }
}