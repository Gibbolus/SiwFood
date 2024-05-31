package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
	
	@Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) = LOWER(?1)")
	public List<Ingredient> findByName(String name);
	
	public boolean existsByName(String name);
	
	@Query(value="select * "
	        + "from ingredient i "
	        + "where i.id not in "
	        + "(select ir.ingredients_utilizzati_id "
	        + "from recipe_ingredients_utilizzati ir "
	        + "where ir.recipe_id = :recipeId)", nativeQuery=true)
	public Iterable<Ingredient> findIngredientsNotInRecipe(@Param("recipeId") Long recipeId);

}
