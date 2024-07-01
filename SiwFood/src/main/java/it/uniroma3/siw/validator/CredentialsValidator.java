package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials c=(Credentials) target;
		if(c.getPassword().length()<5) {
			errors.reject("password.message");
		}
		if(this.credentialsService.existsByUsername(c.getUsername())) {
			errors.reject("username.duplicate");
		}
	}

}
