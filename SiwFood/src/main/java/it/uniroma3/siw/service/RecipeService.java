package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeService {
	@Autowired
	private RecipeRepository recipeRepository;

	public Recipe findById(Long id) {
		return recipeRepository.findById(id).get();
	}

	public Iterable<Recipe> findAll() {
		return recipeRepository.findAll();
	}

	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	public Iterable<Recipe> findByCook(Cook cook) {
		return recipeRepository.findByCook(cook);
	}

	public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
