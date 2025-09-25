import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ---- Book Class ----
class Book {
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        this.isIssued = issued;
    }

    @Override
    public String toString() {
        String status = isIssued ? "Issued" : "Available";
        return title + " by " + author + " - " + status;
    }
}

// ---- User Class ----
class User {
    private String name;
    private List<Book> borrowedBooks;

    public User(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book, Library library) {
        if (library.issueBook(book)) {
            borrowedBooks.add(book);
            System.out.println(name + " borrowed '" + book.getTitle() + "'.");
        } else {
            System.out.println("Sorry, '" + book.getTitle() + "' is not available.");
        }
    }

    public void returnBook(Book book, Library library) {
        if (borrowedBooks.contains(book)) {
            library.returnBook(book);
            borrowedBooks.remove(book);
            System.out.println(name + " returned '" + book.getTitle() + "'.");
        } else {
            System.out.println(name + " does not have '" + book.getTitle() + "' to return.");
        }
    }
}

// ---- Library Class ----
class Library {
    private String name;
    private List<Book> books;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean issueBook(Book book) {
        if (books.contains(book) && !book.isIssued()) {
            book.setIssued(true);
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (books.contains(book) && book.isIssued()) {
            book.setIssued(false);
            return true;
        }
        return false;
    }

    public void listBooks() {
        System.out.println("\nBooks in " + name + ":");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public Book getBookByIndex(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        }
        return null;
    }

    public int getBookCount() {
        return books.size();
    }
}

// ---- Main Class (with user input) ----
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------Library Management System-------------");
        // Create library and books
        Library library = new Library("City Library");
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("Pride and Prejudice", "Jane Austen"));

        // Create user
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        User user = new User(userName);

       
        int choice;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. View all books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    library.listBooks();
                    break;

                case 2:
                    library.listBooks();
                    System.out.print("Enter the book number to borrow: ");
                    int borrowIndex = getIntInput(scanner) - 1;
                    Book bookToBorrow = library.getBookByIndex(borrowIndex);
                    if (bookToBorrow != null) {
                        user.borrowBook(bookToBorrow, library);
                    } else {
                        System.out.println("Invalid book number.");
                    }
                    break;

                case 3:
                    library.listBooks();
                    System.out.print("Enter the book number to return: ");
                    int returnIndex = getIntInput(scanner) - 1;
                    Book bookToReturn = library.getBookByIndex(returnIndex);
                    if (bookToReturn != null) {
                        user.returnBook(bookToReturn, library);
                    } else {
                        System.out.println("Invalid book number.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the library system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 4);

        scanner.close();
    }

   
    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
