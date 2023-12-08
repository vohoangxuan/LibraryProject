package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BookCopy copy;
    public CheckoutRecordEntry(LocalDate checkout, LocalDate due, BookCopy bookcopy) {
        checkoutDate = checkout;
        dueDate = due;
        copy = bookcopy;
    }
    public CheckoutRecordEntry(LocalDate checkout, int dueAfter, BookCopy bookcopy){
        checkoutDate = checkout;
        dueDate = checkoutDate.plusDays(dueAfter);
        copy = bookcopy;
    }
    public LocalDate getCheckoutDate(){
        return checkoutDate;
    }
    public BookCopy getBookCopy(){
        return copy;
    }
    public LocalDate getDueDate(){
        return dueDate;
    }
    public boolean isOverDue(LocalDate returnDate){
        return returnDate.isAfter(dueDate);
    }
    @Override
	public String toString() {
        String ret = "Checkout date: " + getCheckoutDate() + " - Due date: " + getDueDate();
		ret += "; Book copy: " + copy;
        return ret;
	}
}
