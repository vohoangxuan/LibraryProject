package business;

import java.io.Serializable;

public class SearchMemberException extends Exception implements Serializable {

	public SearchMemberException() {
		super();
	}
	public SearchMemberException(String msg) {
		super(msg);
	}
	public SearchMemberException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 1L;
	
}
