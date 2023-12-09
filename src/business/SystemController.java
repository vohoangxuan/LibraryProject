package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static User currentUser = null;
	
	public void logout(){
		currentAuth = null;
		currentUser = null;
	}
	public void saveNewMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}
	
	
	public LibraryMember searchMember(String memberId) throws SearchMemberException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> map = da.readMemberMap();
		if(!map.containsKey(memberId)) {
			throw new SearchMemberException("Member ID " + memberId + " not found");
		} else {
			return map.get(memberId);
		}
	}
	public int getMaxMemberId() {
		List<String> allMemIds = allMemberIds();
		int maxId = 0;
		int curId = 0;
		try {
			for(String m:allMemIds) {
				curId = Integer.valueOf(m);
				if(maxId < curId)
					maxId = curId;
				}
		} catch (NumberFormatException e) {
			return 0;
		}
		return maxId;
	}
	
	public List<LibraryMember> getAllMembers() {
		DataAccess da = new DataAccessFacade();
		return new ArrayList<LibraryMember>(da.readMemberMap().values());
	}
	
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public Book addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors, List<BookCopy> copies) throws BookException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		if(map.containsKey(isbn)) {
			throw new BookException("This ISBN exists in the system");
		}
		Book book = new Book(isbn, title, maxCheckoutLength, authors, copies.toArray(new BookCopy[0]));
		map.put(isbn, book);
		da.updateBook(book);
		return book;
	}

	@Override
	public List<Author> getAllAuthor() {
		DataAccess da = new DataAccessFacade();
		return da.readAuthorsMap().values().stream().toList();
	}

	@Override
	public void addCheckoutEntry(String memId, String isbnNumb, LocalDate checkout, int due) throws BookException, MemberException {
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> mapBook = da.readBooksMap();
		Book b;
		if (mapBook.containsKey(isbnNumb)){
			b = mapBook.get(isbnNumb);
		}
		else{
			throw new BookException("Requested book does not exist");
		}
		if (due > b.getMaxCheckoutLength()){
			throw new BookException("Due date exceeds book max checkout length");
		}
		// HashMap<String, LibraryMember> map = da.readMemberMap();
		if (b.isAvailable()){
			BookCopy copy = b.getNextAvailableCopy();
			HashMap<String, LibraryMember> mapMem = da.readMemberMap();
			if(!mapMem.containsKey(memId)){
				throw new MemberException("Member ID does not exist");
			}
			copy.changeAvailability();
			da.updateMemberRecord(memId, new CheckoutRecordEntry(checkout, due, copy));
			da.updateBook(b);
		}
		else{
			throw new BookException("No book copy left");
		}
	}

	@Override
	public List<Book> getAllBook() {
		DataAccess da = new DataAccessFacade();
		return da.readBooksMap().values().stream().toList();
	}

	@Override
	public Book addBookCopyByISBN(String isbn) throws BookException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		Book book = map.get(isbn);
		if(book == null) {
			throw new BookException("This ISBN does not exist in the system");
		}
		book.addCopy();
		da.updateBook(book);
		return book;
	}

	@Override
	public BookCopy addBookCopy(Book book) throws BookException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		if (!map.containsKey(book.getIsbn())) {
			throw new BookException("This ISBN does not exist in the system");
		}
		BookCopy bookCopy = book.addCopy();
		da.updateBook(book);
		return bookCopy;
	}

	@Override
	public Book getBookById(String isbn) throws BookException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		Book book = map.get(isbn);
		if (book == null) {
			throw new BookException("This ISBN does not exist in the system");
		}
		return book;
	}

	@Override
	public void showError(String string) {

	}
	@Override
	public void login(User loginUser) throws LoginException {

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(loginUser.getId())) {
			throw new LoginException("ID " + loginUser.getId() + " not found");
		}
		String passwordFound = map.get(loginUser.getId()).getPassword();
		if(!passwordFound.equals(loginUser.getPassword())) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(loginUser.getId()).getAuthorization();
		currentUser = loginUser;
	}
	@Override
	public HashMap<BookCopy, LibraryMember> findOverdueEntries(String isbn, LocalDate checkDate) throws BookException {

		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		if (!map.containsKey(isbn)) {
			throw new BookException("This ISBN does not exist");
		}

		HashMap<BookCopy,LibraryMember> bookCopyLibraryMemberMap = new HashMap<>();
		Book book = map.get(isbn);
		List<BookCopy> bookCopyList = Arrays.asList(book.getCopies());
		
		HashMap<String, LibraryMember> memberMap = da.readMemberMap();
		for (LibraryMember member: memberMap.values()) {
			if(member.getRecord() == null || member.getRecord().getRecord() == null)
				continue;
			List<CheckoutRecordEntry> entries = new ArrayList<CheckoutRecordEntry>(); 
			entries.addAll(member.getRecord().getRecord());
			member.getRecord().getRecord().clear();
			for (int i = 0; i < entries.size(); i++) {
				LocalDate dueDate = entries.get(i).getDueDate();
				if(dueDate != null && (dueDate.isBefore(checkDate))) {
					BookCopy copy = entries.get(i).getBookCopy();
					if(bookCopyList.contains(copy)) {
						if (!bookCopyLibraryMemberMap.containsKey(copy)) {
							LibraryMember newMem = new LibraryMember(member.getMemberId()
									, member.getFirstName()
									, member.getLastName()
									, member.getTelephone()
									, member.getAddress());
							bookCopyLibraryMemberMap.put(copy,newMem);
						} 
						bookCopyLibraryMemberMap.get(copy).getRecord().getRecord().add(entries.get(i));
					}
				}
			}
		}
		return bookCopyLibraryMemberMap;
	
	}

}
