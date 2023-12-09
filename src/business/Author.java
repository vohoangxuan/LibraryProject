package business;

import java.io.Serializable;

final public class Author extends Person implements Serializable {
	private String authorId;
	public String getAuthorId() { return  authorId; }
	private String bio;
	public String getBio() {
		return bio;
	}
	
	public Author(String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.bio = bio;
	}

	public String toString(){
		String ret = "";
		ret += "Author ID: " + authorId + " - bio: " + bio;
		return ret;
	}

	private static final long serialVersionUID = 7508481940058530471L;
}
