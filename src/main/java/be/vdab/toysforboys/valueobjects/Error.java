package be.vdab.toysforboys.valueobjects;

import java.util.HashSet;
import java.util.Set;

public class Error {
	private String message;
	private Set<Long> ids;

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
}