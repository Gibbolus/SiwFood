package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.validator.CookValidator;
import jakarta.persistence.EntityManager;

@Controller
public class CookController {

	private static final String UPLOAD_DIR = "C:\\Users\\gabri\\OneDrive\\Documenti\\SiwFood\\SiwFood\\src\\main\\resources\\static\\images";
	//private static final String UPLOAD_DIR = "C:\\Users\\Gabriele\\git\\SiwFood\\SiwFood\\src\\main\\resources\\static\\images";
	
	@Autowired
	private CookRepository cookRepository;

	@Autowired
	private CookService cookService;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CookValidator cookValidator;

	@GetMapping(value = "/cook/{id}")
	public String getCook(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cook", this.cookService.findById(id));
		return "cook.html";
	}

	@GetMapping(value = "/cooks")
	public String ShowCook(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "cooks.html";
	}

	@GetMapping(value = "/admin/formNewCook")
	public String formNewCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "admin/formNewCook.html";
	}

	@GetMapping(value = "/admin/manageCooks")
	public String ShowCookAdmin(Model model) {
		model.addAttribute("cooks", this.cookService.findAll());
		return "/admin/manageCooks.html";
	}

	@GetMapping(value = "/formSearchCooks")
	public String SearchCooks() {
		return "formSearchCooks.html";
	}

	@PostMapping(value = "/formSearchCooks")
	public String searchCooks(Model model, @RequestParam String name) {
		String query = "SELECT c FROM Cook c WHERE LOWER(c.name) LIKE LOWER('%" + name + "%')";
		List<Cook> cooks = this.entityManager.createQuery(query, Cook.class).getResultList();
		model.addAttribute("cooks", cooks);
		return "cooks.html";
	}

	@PostMapping(value = "admin/formSearchCooks")
	public String searchCooksAdmin(Model model, @RequestParam String name) {
		String query = "SELECT c FROM Cook c WHERE LOWER(c.name) LIKE LOWER('%" + name + "%')";
		List<Cook> cooks = this.entityManager.createQuery(query, Cook.class).getResultList();
		model.addAttribute("cooks", cooks);
		return "/admin/manageCooks.html";
	}

	@PostMapping(value = "/admin/cooks")
	public String newCook(@ModelAttribute("cook") Cook cook, @RequestParam("immagine") MultipartFile file, BindingResult bindingResult, Model model) {
		
		this.cookValidator.validate(cook, bindingResult);
		if (!bindingResult.hasErrors()) {
			if(!file.isEmpty()) {
				try {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					cook.setUrlImage(fileName);
					
					this.cookService.save(cook);
					
					model.addAttribute("cook", cook);
					return "cook";
				} catch (IOException e) {
					e.printStackTrace();
					return "/admin/formNewCook.html";
				}
			}
		}
		return "/admin/formNewCook.html";
	}

	@GetMapping(value = "/admin/deleteCook/{cookId}")
	public String deleteCookAdmin(@PathVariable("cookId") Long cookId, Model model) {
		Cook currentCook = cookService.findById(cookId);
		User currentUser = userRepository.findByNameAndSurname(currentCook.getName(), currentCook.getSurname());

		if (!currentCook.getRecipes().isEmpty()) {
			List<Recipe> recipes = recipeRepository.findByCookId(cookId);
			for (Recipe r : recipes) {
				r.setCook(null);
			}
		}

		if (currentUser != null) {
			Credentials currentCredentials = credentialsRepository.findById(currentUser.getId()).get();
			userRepository.deleteById(currentUser.getId());
			credentialsRepository.deleteById(currentCredentials.getId());
		}
		cookRepository.deleteById(cookId);

		return "redirect:/admin/manageCooks";
	}

}
