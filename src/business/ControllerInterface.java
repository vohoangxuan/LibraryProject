package business;

import java.time.LocalDate;
import java.util.List;

import dataaccess.User;


public interface ControllerInterface {
	public void login(User loginUser) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void saveNewMember(LibraryMember member);
	public Book addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors, List<BookCopy> copies) throws BookException;
	public List<Author> getAllAuthor();
	public void addCheckoutEntry(String memId, String isbnNumb, LocalDate checkout, int due) throws BookException, MemberException ;
	public int getMaxMemberId();
	public List<Book> getAllBook();
	public Book addBookCopyByISBN(String isbn) throws BookException;
	public BookCopy addBookCopy(Book book) throws BookException;
	public void showError(String string);
	public Book getBookById(String isbn) throws BookException;
	public LibraryMember searchMember(String memberId) throws SearchMemberException;
	public void logout();
	public List<LibraryMember> getAllMembers();
}
