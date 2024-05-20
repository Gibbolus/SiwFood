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
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.RecipeService;

@Controller
public class CookController {

	@Autowired CookService cookService;

	@Autowired CookRepository cookRepository;

	@Autowired RecipeService recipeService;

	@GetMapping("/admin/formNewCook")
	public String formNewCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "admin/formNewCook.html";
	}
	@PostMapping("/cooks")
	public String newCook(@ModelAttribute("cook") Cook cook, Model model) {
		if (!cookRepository.existsByNameAndSurname(cook.getName(), cook.getSurname())) {
			this.cookService.save(cook);
			model.addAttribute("cook", cook);
			return "redirect:cook/"+cook.getId();
		} else {
			model.addAttribute("messaggioErrore", "Questo cuoco esiste già");
			return "admin/formNewcook.html";
		}
	}

	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cook", this.cookRepository.findById(id).get());
		return "cook.html";
	}

	@GetMapping("/cook")
	public String showCook(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "cooks.html";
	}

	@GetMapping("/formSearchCooks")
	public String formSearchCooks() {
		return "formSearchCooks.html";
	}

	@PostMapping("/formSearchCooks")
	public String searchCooks(Model model, @RequestParam String name) {
		model.addAttribute("cooks", this.cookService.findByName(name));
		return "foundCooks.html";
	}





}
