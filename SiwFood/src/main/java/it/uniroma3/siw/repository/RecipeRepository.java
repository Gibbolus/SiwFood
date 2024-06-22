package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
	@Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(?1)")
	public List<Recipe> findByName(String name);
	
	public boolean existsByNameAndCook(String name, Cook cook);
	
	public List<Recipe> findByCook(Cook cook);
	
	public Recipe findByCookId(Long Id);
}
