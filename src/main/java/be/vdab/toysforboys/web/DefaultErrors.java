package be.vdab.toysforboys.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
class DefaultErrors implements Errors, Serializable{
	private static final long serialVersionUID = 1L;
	private final Set<Error> errorSet = new HashSet<>();

	protected DefaultErrors() {
	}

	@Override
	public Set<Error> getErrors() {
		return errorSet;
	}

	@Override
	public void addError(String errorMessage, long id) {
		Optional<Error> optionalError = errorSet.stream()
										.filter(error -> error.getMessage().equals(errorMessage))
										.findFirst();
		if (optionalError.isPresent()) {
			optionalError.get().add(id);			
		} else {
			Error error = new Error(errorMessage);
			error.add(id);
			errorSet.add(error);
		}
	}

	@Override
	public void removeAllPreviousErrors() {
		errorSet.clear();		
	}
}