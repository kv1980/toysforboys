package be.vdab.toysforboys.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class DefaultError implements Error {
	private String message;
	private Set<Long> ids;

	public DefaultError(String message) {
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
		if (!(obj instanceof DefaultError))
			return false;
		DefaultError other = (DefaultError) obj;
		return message.equals(other.message);
	}
}