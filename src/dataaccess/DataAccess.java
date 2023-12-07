package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member);
	public void updateMemberRecord(String memberID, CheckoutRecordEntry entry);
	public void updateBook(Book book);
	// public void addCheckoutEntry(LibraryMember member, CheckoutRecord copy);
	public HashMap<String, Author> readAuthorsMap();
}
