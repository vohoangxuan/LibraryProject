package business;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckoutRecord implements Serializable {
	private static final long serialVersionUID = 5826456330951355483L;
    private ArrayList<CheckoutRecordEntry> record = new ArrayList<CheckoutRecordEntry>();
    public CheckoutRecord(){
    }
    public void addEntry(CheckoutRecordEntry entry){
        record.add(entry);
    }
    @Override
	public String toString() {
        String ret = "Checkout Record: ";
        for (CheckoutRecordEntry c: record){
            ret += c + "\n";
        }
		return ret;
	}
	public ArrayList<CheckoutRecordEntry> getRecord() {
		return record;
	}
    
    
}
