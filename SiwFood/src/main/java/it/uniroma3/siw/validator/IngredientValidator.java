package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.service.IngredientService;

@Component
public class IngredientValidator implements Validator {

	@Autowired IngredientService ingredientService;

	@Override
	public void validate(Object target, Errors errors) {
		Ingredient in = (Ingredient) target;
		if(this.ingredientService.existByName(in.getName())) {
			errors.reject("ingredient.duplicate");
		}
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}
}
