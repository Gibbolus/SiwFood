package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Component
public class RecipeValidator implements Validator{
	
	@Autowired
	private RecipeRepository recipeRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Recipe recipe = (Recipe)o;
		if (recipe.getName()!=null && recipe.getCook()!=null 
				&& recipeRepository.existsByNameAndCook(recipe.getName(), recipe.getCook())) {
			errors.reject("recipe.duplicate");
		}
		if(recipe.getCookTime()>200 || recipe.getCookTime()<0) {
			errors.reject("recipe.time");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Recipe.class.equals(aClass);
	}
}
