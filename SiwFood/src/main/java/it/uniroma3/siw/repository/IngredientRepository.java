package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
	
	public List<Ingredient> findByName(String name);
	
	public List<Ingredient> findByQuantity(String quantity);
	
	public boolean existsByName(String name);
	
	@Query(value="select * "
	        + "from ingredient i "
	        + "where i.id not in "
	        + "(select ir.ingredients_utilizzati_id "
	        + "from recipe_ingredients_utilizzati ir "
	        + "where ir.recipe_id = :recipeId)", nativeQuery=true)
	public Iterable<Ingredient> findIngredientsNotInRecipe(@Param("recipeId") Long recipeId);

}
