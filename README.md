# Ebenezer Library System

A console-based Book Lending & Cataloguing System for Ebenezer Community Library, built for DCIT308 group project.

## Setup
1. Clone the repository: `git clone <repository-url>`
2. Ensure Java JDK 17+ is installed.
3. Compile and run: `javac src/*.java src/*/*.java && java src.Main`
4. Data files are stored in `data/` (books.txt, borrowers.txt, transactions.txt).

## Modules
- **model/**: Book, Borrower, Transaction classes.
- **datastructures/**: BookInventory, BorrowerRegistry, LendingTracker, OverdueMonitor.
- **utils/**: FileHandler, SearchUtil, SortUtil.
- **reports/**: ReportGenerator for analytics.

## Collaboration
- Create a branch: `git checkout -b <feature-name>`
- Commit changes: `git commit -m "Description"`
- Push: `git push origin <feature-name>`
- Create pull requests for review.
