package be.vdab.toysforboys.web;

import java.util.Set;

public interface Errors {
	Set<Error> getErrors();
	void addError(String errorMessage,long id);
	void removeAllPreviousErrors();
}
