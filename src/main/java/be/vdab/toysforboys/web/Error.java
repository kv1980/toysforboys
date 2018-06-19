package be.vdab.toysforboys.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Error implements Serializable {
	private static final long serialVersionUID = 1L;
	private String message;
	private Set<Long> ids;
	
	protected Error() {
	}

	public Error(String message) {
		this.message = message;
		this.ids = new HashSet<>();
	}

	public String getMessage() {
		return message;
	}

	public Set<Long> getIds() {
		return ids;
	}

	public void add(long id) {
		ids.add(id);
	}

	@Override
	public int hashCode() {
		return message.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Error))
			return false;
		Error other = (Error) obj;
		return message.equals(other.message);
	}
}