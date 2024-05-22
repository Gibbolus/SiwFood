package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.repository.IngredientRepository;

@Service
public class IngredientService {
	
	@Autowired IngredientRepository ingredientRepository;
	
	public Ingredient findById(Long id) {
		return ingredientRepository.findById(id).get();
	}
	
	public Iterable<Ingredient> findAll() {
		return ingredientRepository.findAll();
	}
	
	public Ingredient save(Ingredient ingredient) {
		return ingredientRepository.save(ingredient);
	}
	
	public Iterable<Ingredient> findByName(String name) {
		return ingredientRepository.findByName(name);
	}
}
