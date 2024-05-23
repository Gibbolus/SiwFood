package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.repository.IngredientRepository;
import it.uniroma3.siw.service.IngredientService;


@Controller
public class IngredientController {
	
	@Autowired IngredientRepository ingredientRepository;
	
	@Autowired IngredientService ingredientService;
	
	
	@GetMapping("/ingredient/{id}")
	public String getIngredient(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingredient", this.ingredientRepository.findById(id).get());
		return "ingredient.html";
	}

	@GetMapping("/ingredients")
	public String ShowIngredients(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());
		return "ingredients.html";
	}
	
	@PostMapping("/searchIngredients")
	public String searchIngredients(Model model, @RequestParam String name) {
		model.addAttribute("ingredients", this.ingredientRepository.findByName(name));
		return "ingredients.html";
	}
	
	@PostMapping("admin/searchIngredients")
	public String searchIngredientsAdmin(Model model, @RequestParam String name) {
		model.addAttribute("ingredients", this.ingredientRepository.findByName(name));
		return "/admin/manageIngredients.html";
	}

	
	@GetMapping("/admin/manageIngredients")
	public String ShowIngredientsAdmin(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());
		return "/admin/manageIngredients.html";
	}
	
	@GetMapping(value = "/admin/formNewIngredient")
	public String formNewIngredient(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "/admin/formNewIngredient.html";
	}
	
	@PostMapping("/admin/ingredient")
	public String newIngredient(@ModelAttribute("ingredient") Ingredient ingredient, Model model) {
		if (!ingredientRepository.existsByName(ingredient.getName())) {
			this.ingredientService.save(ingredient);
			model.addAttribute("ingredient", ingredient);
			return "ingredient.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo ingredient esiste già");
			return "/admin/formNewIngredient.html";
		}
	}
	
	@GetMapping(value="/cookUser/formNewIngredient")
	public String formNewIngredientCook(Model model) {
	    model.addAttribute("ingredient", new Ingredient());
		return "cookUser/formNewIngredient.html";
	}
	
	@PostMapping("/cookUser/ingredient")
	public String newIngredientCook(@ModelAttribute("ingredient") Ingredient ingredient, Model model) {
		if (!ingredientRepository.existsByName(ingredient.getName())) {
			this.ingredientService.save(ingredient);
			model.addAttribute("ingredient", ingredient);
			return "ingredient.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo ingredient esiste già");
			return "/cookUser/formNewIngredient.html";
		}
	}
	
	@GetMapping(value = "/admin/deleteIngredient/{ingredientId}")
	public String deleteIngredientAdmin(@PathVariable("ingredientId") Long ingredientId, Model model) {
		ingredientService.deleteById(ingredientId);
        return "redirect:/admin/manageIngredients";
	}
	
}
