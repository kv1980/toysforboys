package be.vdab.toysforboys.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class DefaultErrorList {
		List<DefaultError> errorList = new ArrayList<>();
	
	public DefaultErrorList() {
		errorList.add(new DefaultError("the order(s) were not found."));
		errorList.add(new DefaultError("there was not enough stock, please click order Id for more detailled information"));
		errorList.add(new DefaultError("the transaction failed, please try again"));
	}
	
	public List<DefaultError> getAll(){
		return errorList;
	}

	public DefaultError getNotFound() {
		return errorList.stream()
				        .filter(error -> error.getMessage().contains("not found"))
				        .findFirst()
				        .get();
	}

	public DefaultError getNotEnoughStock() {
		return errorList.stream()
		        .filter(error -> error.getMessage().contains("not enough stock"))
		        .findFirst()
		        .get();
	}

	public DefaultError getFailedTransaction() {
		return errorList.stream()
		        .filter(error -> error.getMessage().contains("transaction failed"))
		        .findFirst()
		        .get();
	}
	
	public boolean isEmpty() {
		for (DefaultError error : errorList) {
			if (!error.getIds().isEmpty()) {
				return false;
			}
		}
		return true;			
	}
}
