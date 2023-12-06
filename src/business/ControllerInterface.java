package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void saveNewMember(LibraryMember member);
	public Book addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors, List<BookCopy> copies) throws BookException;
	public List<Author> getAllAuthor();
}
