package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.repository.IngredientRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@Controller
public class RecipeController {

	@Autowired RecipeRepository recipeRepository;

	@Autowired RecipeService recipeService;

	@Autowired RecipeValidator recipeValidator;

	@Autowired CookService cookService;
	
	@Autowired IngredientService ingredientService;

	@Autowired CookRepository cookRepository;

	@Autowired IngredientRepository ingredientRepository;

	@Autowired CredentialsRepository credentialsRepository;

	@Autowired EntityManager entityManager;
	
	
	@GetMapping(value = "/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", this.recipeRepository.findById(id).get());
		return "recipe.html";
	}

	@GetMapping(value = "/recipes")
	public String ShowRecipeIndex(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "recipes.html";
	}
	
	@GetMapping(value = "/formContact")
	public String formContact() {
		return "formContact.html";
	}	
	
	@PostMapping(value = "/formSearchRecipes")
	public String searchRecipes(Model model, @RequestParam String name) {
		String query = "SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER('%"+ name + "%')";
		List<Recipe> recipes = this.entityManager.createQuery(query, Recipe.class).getResultList();
		model.addAttribute("recipes", recipes);
		return "recipes.html";
	}

	@PostMapping(value = "admin/formSearchRecipes")
	public String searchRecipesAdmin(Model model, @RequestParam String name) {
		String query = "SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER('%"+ name + "%')";
		List<Recipe> recipes = this.entityManager.createQuery(query, Recipe.class).getResultList();
		model.addAttribute("recipes", recipes);
		return "/admin/manageRecipes.html";
	}

	@PostMapping(value = "cookUser/formSearchRecipes")
	public String searchRecipesCook(Model model, @RequestParam String name) {
		String query = "SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER('%"+ name + "%')";
		List<Recipe> recipes = this.entityManager.createQuery(query, Recipe.class).getResultList();
		model.addAttribute("recipes", recipes);
		return "/cookUser/manageRecipes.html";
	}

	@GetMapping(value = "/admin/manageRecipes")
	public String ShowRecipeAdmin(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "/admin/manageRecipes.html";
	}
	
	@GetMapping(value = "/cookUser/manageRecipes/{username}")
	public String ShowRecipeCook(@PathVariable("username") String username, Model model) {
	    Credentials tempUser = credentialsRepository.findByUsername(username);
	    User currentUser = tempUser.getUser();
	    Cook currentCook = this.cookRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		model.addAttribute("recipes", currentCook.getRecipes());
		return "/cookUser/manageRecipes.html";
	}

	@GetMapping(value = "/admin/formNewRecipe")
	public String formNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "/admin/formNewRecipe.html";
	}

	@PostMapping(value = "/admin/recipe")
	public String newRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult,
			Model model) {
		this.recipeValidator.validate(recipe, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.recipeRepository.save(recipe);
			model.addAttribute("recipe", recipe);
			return "recipe.html";
		} else {
			return "/admin/formNewRecipe.html";
		}
	}

	@GetMapping(value = "/cookUser/formNewRecipe/{username}")
	public String formNewRecipeCook(@PathVariable("username") String username, Model model) {
	    Credentials tempUser = credentialsRepository.findByUsername(username);
	    User currentUser = tempUser.getUser();
	    Cook currentCook = this.cookRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
	    Recipe recipe = new Recipe();
	    model.addAttribute("cook", currentCook);
	    model.addAttribute("cookId", currentCook.getId());
	    model.addAttribute("recipe", recipe);
	    model.addAttribute("userDetails", tempUser); // Aggiungi userDetails al modello
	    return "cookUser/formNewRecipe.html";
	}


	@PostMapping(value = "/cookUser/recipe")
	public String newRecipeCook(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, @RequestParam("username") String username, Model model) {
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		Cook currentCook = this.cookRepository.findByNameAndSurname(currentUser.getName(), currentUser.getSurname());
		recipe.setCook(currentCook);
		this.recipeValidator.validate(recipe, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.recipeRepository.save(recipe);
			model.addAttribute("recipe", recipe);
			return "recipe.html";
		} else {
			return "cookUser/formNewRecipe.html";
		}
	}

	@GetMapping(value = "/admin/addCook/{idRecipe}")
	public String addCook(@PathVariable("idRecipe") Long recipeId, Model model) {
		model.addAttribute("cooks", cookService.findAll());
		model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
		// Ritorna il name della pagina HTML da visualizzare
		return "/admin/addCook.html";
	}

	@GetMapping(value = "/admin/formUpdateRecipe/{id}")
	public String formUpdateRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(id).get());
		return "admin/formUpdateRecipe.html";
	}

	@GetMapping(value = "/cookUser/formUpdateRecipe/{id}/{username}")
	public String formUpdateRecipeCook(@PathVariable("id") Long id, @PathVariable("username") String username,
			Model model, RedirectAttributes redirectAttributes) {
		// Recupera l'utente dal repository
		Credentials tempUser = credentialsRepository.findByUsername(username);
		User currentUser = tempUser.getUser();
		// Recupera la recipe dal repository
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		if (recipe == null || recipe.getCook() == null || !recipe.getCook().getName().equals(currentUser.getName())
				|| !recipe.getCook().getSurname().equals(currentUser.getSurname())) {
			redirectAttributes.addFlashAttribute("messaggioErrore", "Non puoi modificare questa recipe perché non ti appartiene!");
			return "redirect:/cookUser/manageRecipes";
		}
		// Aggiungi la recipe al modello e restituisci la vista
		model.addAttribute("recipe", recipe);
		return "cookUser/formUpdateRecipe.html";
	}

	@GetMapping(value = "/admin/setCookToRecipe/{cookId}/{recipeId}")
	public String setCookToRecipe(@PathVariable("cookId") Long cookId, @PathVariable("recipeId") Long recipeId,
			Model model) {
		Cook cook = this.cookService.findById(cookId);
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		recipe.setCook(cook);
		this.recipeRepository.save(recipe);
		model.addAttribute("recipe", recipe);
		return "admin/formUpdateRecipe.html";
	}

	@GetMapping(value = "/admin/updateIngredients/{id}")
	public String updateIngredients(@PathVariable("id") Long id, Model model) {
		List<Ingredient> ingredientsToAdd = this.ingredientsToAdd(id);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeRepository.findById(id).get());
		return "admin/addIngredient.html";
	}

	@GetMapping(value = "/cookUser/updateIngredients/{id}")
	public String updateIngredientsCook(@PathVariable("id") Long id, Model model) {
		List<Ingredient> ingredientsToAdd = this.ingredientsToAdd(id);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeRepository.findById(id).get());
		return "cookUser/addIngredient.html";
	}

	@GetMapping(value = "/admin/addIngredientToRecipe/{ingredientId}/{recipeId}")
	public String addIngredientToRecipe(@PathVariable("ingredientId") Long ingredientId,
			@PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		Ingredient ingredient = this.ingredientRepository.findById(ingredientId).get();
		Set<Ingredient> ingredients = recipe.getIngredientsUtilizzati();
		ingredients.add(ingredient);
		this.recipeRepository.save(recipe);

		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);

		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);

		return "admin/addIngredient.html";
	}

	@GetMapping(value = "/cookUser/addIngredientToRecipe/{ingredientId}/{recipeId}")
	public String addIngredientToRecipeCook(@PathVariable("ingredientId") Long ingredientId,
			@PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		Ingredient ingredient = this.ingredientRepository.findById(ingredientId).get();
		Set<Ingredient> ingredients = recipe.getIngredientsUtilizzati();
		ingredients.add(ingredient);
		this.recipeRepository.save(recipe);

		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);

		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);

		return "cookUser/addIngredient.html";
	}

	@GetMapping(value = "/admin/removeIngredientFromRecipe/{ingredientId}/{recipeId}")
	public String removeIngredientFromRecipe(@PathVariable("ingredientId") Long ingredientId,
			@PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		Ingredient ingredient = this.ingredientRepository.findById(ingredientId).get();
		Set<Ingredient> ingredientsUtilizzati = recipe.getIngredientsUtilizzati();
		ingredientsUtilizzati.remove(ingredient);
		this.recipeRepository.save(recipe);

		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);

		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);

		return "admin/addIngredient.html";
	}

	@GetMapping(value = "/cookUser/removeIngredientFromRecipe/{ingredientId}/{recipeId}")
	public String removeIngredientFromRecipeCook(@PathVariable("ingredientId") Long ingredientId,
			@PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		Ingredient ingredient = this.ingredientRepository.findById(ingredientId).get();
		Set<Ingredient> ingredientsUtilizzati = recipe.getIngredientsUtilizzati();
		ingredientsUtilizzati.remove(ingredient);
		this.recipeRepository.save(recipe);

		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);

		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);

		return "cookUser/addIngredient.html";
	}
	private List<Ingredient> ingredientsToAdd(Long recipeId) {
		List<Ingredient> ingredientsToAdd = new ArrayList<>();

		for (Ingredient i : ingredientRepository.findIngredientsNotInRecipe(recipeId)) {
			ingredientsToAdd.add(i);
		}
		return ingredientsToAdd;
	}
	
	@GetMapping(value = "/cookUser/updateQuantity/{ingredientId}/{recipeId}")
	public String formUpdateQuantity(@PathVariable("recipeId") Long recipeId,
			@PathVariable("ingredientId") Long ingredientId, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
		model.addAttribute("ingredient", ingredientRepository.findById(ingredientId).get());
		return "/cookUser/updateQuantity.html";
	}
	
	@PostMapping(value = "/cookUser/updateQuantity")
	public String updateQuantity(@RequestParam("ingredientId") Long ingredientId,
								 @RequestParam("recipeId") Long recipeId,
								 @RequestParam("quantityValue") Integer quantityValue, 
								 @RequestParam("quantityUnit") String quantityUnit,
								 Model model) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		
		if (optionalIngredient.isPresent() && optionalRecipe.isPresent()) {
			Ingredient ingredient = optionalIngredient.get();
			Recipe recipe = optionalRecipe.get();
			Map<Long, Integer> quantityToRecipe = ingredient.getQuantityToRecipe();
			quantityToRecipe.put(recipe.getId(), quantityValue);
			ingredient.setUnitOfMeasure(quantityUnit);
			ingredient.setQuantityToRecipe(quantityToRecipe);
			ingredientRepository.save(ingredient);
			model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
			model.addAttribute("ingredient", ingredientRepository.findById(ingredientId).get());
			model.addAttribute("quantityValue", quantityValue);
		}
		return "cookUser/formUpdateRecipe.html";
	}

	@GetMapping(value = "/admin/updateQuantity/{ingredientId}/{recipeId}")
	public String formUpdateQuantityAdmin(@PathVariable("recipeId") Long recipeId, @PathVariable("ingredientId") Long ingredientId, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
		model.addAttribute("ingredient", ingredientRepository.findById(ingredientId).get());
		return "/admin/updateQuantity.html";
	}

	@PostMapping(value = "/admin/updateQuantity")
	public String updateQuantityAdmin(@RequestParam("ingredientId") Long ingredientId,
								 @RequestParam("recipeId") Long recipeId,
								 @RequestParam("quantityValue") Integer quantityValue, 
								 @RequestParam("quantityUnit") String quantityUnit,
								 Model model) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

		if (optionalIngredient.isPresent() && optionalRecipe.isPresent()) {
			Ingredient ingredient = optionalIngredient.get();
			Recipe recipe = optionalRecipe.get();
			Map<Long, Integer> quantityToRecipe = ingredient.getQuantityToRecipe();
			quantityToRecipe.put(recipe.getId(), quantityValue);
			ingredient.setUnitOfMeasure(quantityUnit);
			ingredient.setQuantityToRecipe(quantityToRecipe);
			ingredientRepository.save(ingredient);			// Aggiorna l'ingrediente nel database
			model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
			model.addAttribute("ingredient", ingredientRepository.findById(ingredientId).get());
			model.addAttribute("quantityValue", quantityValue);
		}
		return "admin/formUpdateRecipe.html";
	}
	
	public Integer getQuantityPerRecipe(Long ingredientId, Long recipeId) {
		Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

		if (optionalIngredient.isPresent() && optionalRecipe.isPresent()) {
			Ingredient ingredient = optionalIngredient.get();
			Recipe recipe = optionalRecipe.get();
			return ingredient.getQuantityToRecipe().getOrDefault(recipe, 0); // Restituisce la quantità, se presente,
																				// altrimenti 0
		} else {
			throw new RuntimeException("Ingrediente o Ricetta non trovati");
		}
	}
	

	@GetMapping(value = "/admin/deleteRecipe/{recipeId}")
	public String deleteRecipeAdmin(@PathVariable("recipeId") Long recipeId, Model model) {
		recipeService.deleteById(recipeId);
        return "redirect:/admin/manageRecipes";
	}

	@GetMapping(value = "/cookUser/deleteRecipe/{recipeId}")
	public String deleteRicettaCuoco(@PathVariable("recipeId") Long recipeId, Model model) {
		recipeService.deleteById(recipeId);
        return "redirect:/cookUser/manageRecipes";
	}
	
	
	
}
