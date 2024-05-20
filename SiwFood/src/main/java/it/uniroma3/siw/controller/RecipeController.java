package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.RecipeService;


@Controller 
public class RecipeController {

	@Autowired RecipeService recipeService;
	@Autowired CookService cookService;

	@Autowired RecipeRepository recipeRepository;
	@Autowired CookRepository cookRepository;
	
	@GetMapping("/cookUser/formNewRecipe")
	public String formNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "cookUser/formNewRecipe.html";
	}

	@PostMapping("/recipes")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe, Model model) {
		this.recipeService.save(recipe);
		model.addAttribute("recipe", recipe);
		return "redirect:recipe/"+recipe.getId();
	}

	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", this.recipeRepository.findById(id).get());
		return "recipe.html";
	}

	@GetMapping("/recipe")
	public String showRecipe(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "recipes.html";
	}

	@GetMapping("/formSearchRecipes")
	public String formSearchRecipes() {
		return "formSearchRecipes.html";
	}

	@PostMapping("/formSearchRecipes")
	public String searchRecipes(Model model, @RequestParam String name) {
		model.addAttribute("recipes", this.recipeService.findByName(name));
		return "foundRecipes.html";
	}

	@GetMapping("/admin/manageRecipes")
	public String manageRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "admin/manageRecipes.html";
	}

	@GetMapping("/addCook/{idRecipe}")
	public String addCook(@PathVariable("idRecipe") Long recipeId, Model model) {
		model.addAttribute("cooks", cookService.findAll());
		model.addAttribute("recipe", recipeService.findById(recipeId));
		return "addCook.html";
	}

	@GetMapping("/cookUser/formUpdateRecipes/{id}")
	public String formUpdateRecipes(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "cookUser/formUpdateRecipes.html";
	}

	@GetMapping("/setCookToRecipe/{cookId}/{recipeId}")
	public String setCookToRecipe(@PathVariable("cookId") Long cookId, @PathVariable("recipeId") Long recipeId, Model model) {

		Cook cook = this.cookService.findById(cookId);
		Recipe recipe = this.recipeService.findById(recipeId);
		recipe.setCook(cook);
		this.recipeService.save(recipe);

		model.addAttribute("recipe", recipe);
		return "cookUser/formUpdateRecipes.html";
	}





}


