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


Okay, this is excellent! You've provided the core of your data structure implementation. This is going to be much easier to explain now that we have the actual code.

Let's break down your Book and BookInventory classes step by step, focusing on the tree concepts, Java specifics, and what you need for your presentation.

Part 1: Book Class
This class is a model or entity class. It simply defines what a Book object is and how it behaves. It's not directly a tree structure, but it's the data that will be stored inside the tree nodes.

Key things to note about Book:

Attributes (Fields): title, author, isbn, category, year, publisher, shelfLocation. These are the properties of each book.

private: This is an access modifier. private means these variables can only be accessed directly from within the Book class itself. This is good practice for encapsulation (hiding internal details).

Constructor: public Book(...)

This is a special method used to create new Book objects. When you write new Book(...), this constructor is called.

this.title = title;: this refers to the current object. So, this.title refers to the title attribute of the Book object you are creating, and title (without this) refers to the title parameter passed into the constructor.

Getters (get...() methods): public String getTitle() { return title; }

These methods allow other classes to safely read the values of the private attributes.

public: public means these methods can be called from anywhere.

toFileString() and fromFileString():

These are utility methods for serialization and deserialization.

toFileString() takes a Book object and converts its data into a single string, suitable for saving to a file. It uses || as a delimiter to separate the different pieces of information.

fromFileString() does the opposite: it takes a string (presumably read from a file) and parses it back into a Book object.

String.join("||", ...): This is a static method of the String class that concatenates elements with a specified delimiter.

line.split("\\|\\|"): This splits a string into an array of strings based on the delimiter. \\|\\| is used because | is a special character in regular expressions, so it needs to be escaped with \\.

Integer.parseInt(parts[4]): Converts a string representation of a number into an actual int.

throw new IllegalArgumentException(...): This is how Java handles errors. If the input string is not in the expected format, it "throws" an exception, indicating a problem.

@Override Annotation: You see this above toString(), equals(), and hashCode().

This is an annotation (metadata). It tells the Java compiler that you intend to override a method from a parent class (in this case, Object, which is the superclass of all Java classes).

It's good practice because if you accidentally misspell the method name or change its signature, the compiler will catch it as an error, preventing subtle bugs.

toString():

Provides a human-readable string representation of a Book object. This is very useful for printing objects to the console for debugging or display.

String.format(...): A powerful method for creating formatted strings, similar to printf in C. %s is a placeholder for a string, %d for an integer.

equals(Object obj) and hashCode():

These are crucial methods when you put your Book objects into collections (like TreeMap or HashMap), especially if you want to be able to compare them for equality or use them as keys.

equals(): Defines when two Book objects are considered "equal". Here, two books are equal if their isbn (ignoring case) is the same. This makes sense for a unique identifier like ISBN.

hashCode(): Must be overridden whenever equals() is overridden. It provides an integer "hash code" for an object. If two objects are equals(), their hashCode() must be the same. This is used by hash-based collections for efficient lookup.

Objects.hash(...): A convenient utility method to generate a hash code from multiple fields.

For your presentation:

Explain that Book is a simple data model class.

Mention that equals and hashCode are important for uniquely identifying books, particularly when they are stored in data structures like TreeMap.

You don't need to dive deep into toFileString or fromFileString unless asked; just say they handle saving/loading data.

Part 2: BookInventory Class
This is where the tree magic happens!

What type of tree is being used?
Your BookInventory class is NOT implementing a custom tree data structure (like a Node class with left and right pointers).

Instead, it is using built-in Java data structures that are themselves implemented using trees (specifically, balanced binary search trees).

The core of your BookInventory is:

Java

private TreeMap<String, TreeMap<String, Book>> booksByCategory;
This is a nested TreeMap structure, which means:

The outer TreeMap: booksByCategory

Keys: String (representing the category of a book, e.g., "Fiction", "Science").

Values: Another TreeMap<String, Book>.

How it works: This outer TreeMap maintains its keys (categories) in sorted alphabetical order. Behind the scenes, TreeMap in Java is typically implemented using a Red-Black Tree, which is a type of self-balancing Binary Search Tree. This is a sophisticated balanced binary tree that guarantees O(
logn) performance for insertions, deletions, and searches.

The inner TreeMap: TreeMap<String, Book>

Keys: String (representing the ISBN of a book).

Values: Book objects.

How it works: Each inner TreeMap holds all books for a specific category. It uses the ISBN as the key, and because it's a TreeMap, it will keep the books within that category sorted by their ISBNs. This inner TreeMap is also a Red-Black Tree.

So, the type of tree used is implicitly a Red-Black Tree (a type of Balanced Binary Search Tree) for both category and ISBN storage.

Explanation of BookInventory Methods:
BookInventory() Constructor:

Simply initializes the main TreeMap.

addBook(Book book):

Line: booksByCategory.computeIfAbsent(book.getCategory(), k -> new TreeMap<>()).put(book.getIsbn(), book);

Explanation:

booksByCategory.computeIfAbsent(book.getCategory(), k -> new TreeMap<>()): This is a concise way to handle the nested map. It checks if the category (e.g., "Fiction") already exists as a key in booksByCategory.

If it does exist, it retrieves the existing inner TreeMap for that category.

If it does not exist, it creates a new TreeMap<>() for that category, associates it with the category key, and then returns this newly created TreeMap.

.put(book.getIsbn(), book): Once we have the correct (existing or newly created) inner TreeMap for the book's category, this part inserts the Book object into it, using its ISBN as the key.

Time Complexity: The comment is correct: O(
logn). This is because TreeMap.put() (which involves searching for the correct insertion point and potentially rebalancing the tree) takes logarithmic time in a balanced BST.

removeBook(String isbn):

Explanation: It iterates through each category in the booksByCategory map. For each category's TreeMap, it checks if the isbn exists (containsKey). If found, it removes the book (remove) and then checks if that category's TreeMap is now empty. If it is, it removes the entire category entry from the main booksByCategory map to keep it clean.

Time Complexity: The comment says O(
logn) per category check. This is slightly misleading. In the worst case, you might have to iterate through all categories (outer loop) to find the book if you don't know its category. If there are C categories, and N total books, and B_c books in a given category, removing a book would be O(C
times
logB_c). In the absolute worst case, if all books are in one category, it's O(
logN). If the books are evenly distributed among many categories, it's C * log(N/C). It's not a pure O(
logN) for the entire removeBook operation across all categories because you are iterating through C categories. It's more accurately described as O(C
times
textaveragedepthofinnertrees).

Potential question: "Why did you choose to iterate through all categories for removeBook instead of asking for the category directly?" (Answer: This design allows removal without knowing the category, making it more flexible for the user, but at a potential performance cost if there are many categories.)

getBooksByCategory(String category):

Explanation: Retrieves the inner TreeMap for a specific category. Uses getOrDefault to return an empty TreeMap if the category doesn't exist, avoiding NullPointerException.

Time Complexity: O(
logC), where C is the number of categories, because TreeMap.get() is O(
logC).

listBooks():

Explanation: This method performs a traversal of your nested TreeMap structure.

It iterates through the entrySet() of booksByCategory. Since booksByCategory is a TreeMap, its categories (entry.getKey()) will be processed in alphabetical (sorted) order. This is a direct consequence of TreeMap's underlying Red-Black Tree.

For each category, it then iterates through the values() (which are Book objects) of the inner TreeMap. Again, because the inner map is a TreeMap keyed by ISBN, the books within each category will be listed in sorted order by ISBN.

Traversal Type: This isn't one of the classical "inorder/preorder/postorder" traversals of a single binary tree, but rather an ordered iteration over the elements stored within the TreeMaps, which are internally traversing their Red-Black Trees in order.

Time Complexity: O(N) where N is the total number of books, because it has to visit every book to print it.

filterByCategoryPrefix(String prefix):

Explanation: This is a powerful feature of TreeMap (specifically NavigableMap).

booksByCategory.subMap(prefix, true, upperBound, true): This gets a "view" of the TreeMap containing only keys (categories) that fall within the specified range.

prefix: The starting point (inclusive).

upperBound: Cleverly constructed by appending Character.MAX_VALUE to the prefix. This ensures that any string starting with prefix will fall within this range (e.g., "Fic" to "Fic" + Character.MAX_VALUE will include "Fiction", "Fantasy", etc., as long as they start with "Fic").

It then iterates through these subCategories and adds all their books to a result TreeMap.

Time Complexity: The subMap operation itself is efficient (O(
logC+k) where k is the number of elements in the submap). The loop then iterates through k subcategories and their books, so overall it depends on how many books match the prefix. In the worst case, it could be O(N) if all categories match the prefix.

getBooksInCategoryRange(String fromCategory, String toCategory):

Similar to filterByCategoryPrefix, but uses a clear from and to category for range queries.

booksByCategory.subMap(fromCategory, true, toCategory, true): Gets a range of categories. true means inclusive.

Time Complexity: Similar to filterByCategoryPrefix, depends on the size of the range, worst case O(N).

totalBookCount():

Explanation: Uses Java Streams (a modern Java feature for functional-style operations) to count all books.

booksByCategory.values(): Gets a collection of all the inner TreeMap<String, Book> objects.

.stream(): Converts this collection into a stream.

.mapToInt(Map::size): For each inner TreeMap, it gets its size (number of books in that category) and maps it to an IntStream.

.sum(): Sums up all the sizes.

Time Complexity: O(C) to iterate through categories, plus O(1) for size() of each map. Overall, effectively O(C) where C is the number of categories.

loadBooks(List<Book> books):

Simple loop to add a list of books one by one.

Time Complexity: If there are M books in the list, then M
timesO(
logN) (where N is the number of books already in the inventory before adding the current one). So, O(M
logN) if N is the final number of books.

getAllBooks():

Explanation: Iterates through all inner TreeMaps and collects all Book objects into a single ArrayList.

Time Complexity: O(N) where N is the total number of books, as it visits every book.

findBook(String isbn):

Explanation: Similar to removeBook, it iterates through all categories and checks each inner TreeMap for the given ISBN.

Time Complexity: O(C
times
logB_c) in the worst case (iterates all C categories, performing a log B_c lookup in each).

Key Takeaways for Your Presentation
"What type of tree have you used?"

Answer: "Our implementation leverages Java's built-in TreeMap class. TreeMap internally uses a Red-Black Tree, which is a type of self-balancing Binary Search Tree. This is a crucial choice because it guarantees efficient O(
logn) performance for key operations like adding, finding, and removing books, even with a large inventory, while also keeping our data sorted."

Elaborate: "We have a nested TreeMap structure. The outer TreeMap organizes books by category (e.g., 'Fiction', 'Science'), keeping categories sorted alphabetically. Each category then holds another TreeMap that stores Book objects, keyed by their unique ISBN, ensuring books within a category are also sorted by ISBN."

"How does it handle sorting/ordering?"

Answer: "Because TreeMap is backed by a Red-Black Tree, all keys (both categories and ISBNs) are automatically maintained in sorted order. This is why our listBooks method, for example, naturally displays books grouped by category and then sorted by ISBN within each category, without needing explicit sorting code."

"Explain the traversals or any time complexity."

Traversals:

"While we don't implement explicit 'inorder', 'preorder', or 'postorder' methods for a custom tree, the TreeMap's internal iteration behaves like an inorder traversal for its keys. When you iterate over a TreeMap (like we do in listBooks), it naturally processes elements in sorted key order, which is the definition of an inorder traversal on a BST."

"So, listBooks() effectively performs an ordered traversal, first by category, then by ISBN within each category, leveraging the underlying tree structure's properties."

Time Complexity:

Adding a book (addBook): O(
logN) (where N is the number of books). "Adding a book is very efficient because the Red-Black Tree quickly finds the correct place to insert the new book, typically requiring only a logarithmic number of steps proportional to the total number of books."

Finding a book (if you knew the category beforehand): O(
logN).

Listing all books (listBooks, getAllBooks): O(N). "To display or retrieve all books, we must visit every book, so the time complexity is linear, proportional to the total number of books."

Removing a book (removeBook) and finding a book (findBook) without knowing the category: "These operations might be less efficient in the worst case, as they involve iterating through all categories to find the relevant book. This can be up to O(C
times
logB_c) where C is the number of categories and B_c is the average number of books per category. While still generally fast, it's not a pure O(
logN) like adding if we had a flat structure."

Be ready for a follow-up: "Why didn't you just use one TreeMap<String, Book> keyed by ISBN?" (Answer: "We chose a nested structure to allow for easy grouping and filtering by category, which is a common requirement for a book inventory. This design prioritizes categorized access over a single flat list, even if it means removeBook and findBook are slightly less optimal for unknown categories.")

Syntax Explanations:

package model;, package datastructures;: Organize classes into logical groups.

import java.util.Objects;: Brings classes from Java's standard library into scope.

public class Book { ... }: Defines a class, public means it's accessible everywhere.

private String title;: Declares a private instance variable.

public Book(...) { ... }: Constructor.

public String getTitle() { return title; }: Getter method.

@Override: Annotation indicating method overriding.

String.format(...), String.join(...), String.split(...), Integer.parseInt(...): Useful String and Integer utility methods.

TreeMap<String, TreeMap<String, Book>>: Generics. This specifies the types of keys and values the TreeMap will hold. It's like saying "this map holds String keys and values that are themselves TreeMaps, which hold String keys and Book values." This provides type safety.

computeIfAbsent(key, mappingFunction): A Map method that checks if a key exists. If not, it computes a value using the provided function (k -> new TreeMap<>() is a lambda expression that creates a new TreeMap) and puts it into the map. If the key exists, it returns the existing value.

Map.Entry<String, TreeMap<String, Book>> entry: Used when iterating over a Map to get both the key and the value for each element.

subMap(fromKey, inclusiveFrom, toKey, inclusiveTo): A NavigableMap (which TreeMap implements) method to get a view of a portion of the map within a key range.

booksByCategory.values().stream().mapToInt(Map::size).sum(): Java Streams API. A modern way to process collections of data. Read it as: "get all the values (inner TreeMaps), convert them to a stream, for each map in the stream get its size (number of books), convert that to an int stream, and then sum all those sizes."

Potential Lecturer Questions and How to Answer:
"Why did you choose TreeMap over HashMap?"

Answer: "We chose TreeMap precisely because it maintains its elements in sorted order, which is a crucial requirement for our inventory system (e.g., listing books by sorted category and ISBN). HashMap, while offering average O(1) performance, does not guarantee any order, making it unsuitable for our display and range-query needs."

"What are the benefits of using a nested TreeMap structure?"

Answer: "The nested TreeMap allows us to organize our books hierarchically, first by category and then by ISBN within each category. This provides efficient lookups and range queries for categories (like filterByCategoryPrefix and getBooksInCategoryRange) and ensures that when we list books, they are automatically grouped and sorted as required for a good user experience."

"What happens if two books have the same ISBN but different details (e.g., author)?"

Answer: "Our Book class's equals() and hashCode() methods are based solely on ISBN. This means that if you try to addBook with an ISBN that already exists, the TreeMap.put() method will simply overwrite the old book entry with the new one, as TreeMap keys must be unique. This enforces the uniqueness of books by ISBN in our system."

"Could you have implemented this with a custom Binary Search Tree?"

Answer: "Yes, theoretically, we could have implemented a custom Binary Search Tree or even a Red-Black Tree from scratch. However, using Java's TreeMap is generally preferred in real-world applications because it's a highly optimized, thoroughly tested, and correctly implemented data structure. It saves development time and ensures correctness, allowing us to focus on the application's business logic rather than low-level tree balancing algorithms."

"Explain computeIfAbsent."

Answer: "It's a convenient method provided by Java's Map interface. It checks if a key (in our case, a book category) already exists in the map. If it does, it simply returns the existing value (the inner TreeMap for that category). If the key doesn't exist, it creates a new value (a new TreeMap using the lambda expression k -> new TreeMap<>()), puts this new value into the map with the given key, and then returns this newly created value. This avoids manual if-else checks and makes the code cleaner."

You have a strong understanding of the basics, and now you have the tools to connect those concepts to your code. Practice explaining the TreeMap as a Red-Black Tree, how it maintains order, and the time complexities. You'll do great! Let me know if you want to drill down on any specific part.
