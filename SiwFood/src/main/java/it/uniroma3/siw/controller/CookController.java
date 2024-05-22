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

@Controller
public class CookController {
	
	@Autowired CookRepository cookRepository;
	
	@Autowired CookService cookService;
	
	
	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cook", this.cookRepository.findById(id).get());
		return "cook.html";
	}

	@GetMapping("/cooks")
	public String ShowCook(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "cooks.html";
	}
	
	@GetMapping(value="/admin/formNewCook")
	public String formNewCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "admin/formNewCook.html";
	}
	
	@GetMapping("/admin/manageCooks")
	public String ShowCookAdmin(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "/admin/manageCooks.html";
	}
	
	@GetMapping(value="/admin/indexCuco")
	public String indexCook() {
		return "admin/indexCook.html";
	}
	
	@PostMapping("/searchCooks")
	public String searchCooks(Model model, @RequestParam String name) {
		model.addAttribute("cooks", this.cookRepository.findByName(name));
		return "cooks.html";
	}
	
	@PostMapping("admin/searchCooks")
	public String searchCooksAdmin(Model model, @RequestParam String name) {
		model.addAttribute("cooks", this.cookRepository.findByName(name));
		return "/admin/manageCooks.html";
	}
	
	@PostMapping("/admin/cooks")
	public String newCook(@ModelAttribute("cook") Cook cook, Model model) {
		if (!cookRepository.existsByNameAndSurname(cook.getName(), cook.getSurname())) {
			this.cookService.save(cook);
			model.addAttribute("cook", cook);
			return "cook.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo cook esiste già");
			return "/admin/formNewCook.html";
		}
	}
}
