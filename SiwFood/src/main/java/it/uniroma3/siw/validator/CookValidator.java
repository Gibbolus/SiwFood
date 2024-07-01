package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CookService;

@Component
public class CookValidator implements Validator {

	@Autowired
	CookService cookService;

	@Override
	public void validate(Object target, Errors errors) {
		Cook c = (Cook) target;
		 if(c.getYear().getYear()<1900 || c.getYear().getYear()>2024) {
			 errors.reject("birth.error");
		 }
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}
}
