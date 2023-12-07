package business;

import java.io.Serializable;

public class MemberException extends Exception implements Serializable {

	public MemberException() {
		super();
	}
	public MemberException(String msg) {
		super(msg);
	}
	public MemberException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 8978723266036027364L;
	
}
