package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.CredentialsValidator;
import it.uniroma3.siw.validator.UserValidator;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	private static final String UPLOAD_DIR = "C:\\Users\\gabri\\OneDrive\\Documenti\\SiwFood\\SiwFood\\src\\main\\resources\\static\\images";
	//private static final String UPLOAD_DIR = "C:\\Users\\Gabriele\\git\\SiwFood\\SiwFood\\src\\main\\resources\\static\\images";
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CookService cookService;
	
    @Autowired 
    private CredentialsValidator credentialsValidator;
    
    @Autowired 
    private UserValidator userValidator;
    
	@GetMapping(value = "/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}

	@GetMapping(value = "/login")
	public String showLoginForm(Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/login/error")
	public String showLoginErrorForm(Model model) {
		String messaggioErrore = new String("Username o password incorretti");
		model.addAttribute("messaggioErrore", messaggioErrore);
		return "formLogin.html";
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "index.html";
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
			if (credentials.getRole().equals(Credentials.COOK_ROLE)) {
				return "cookUser/indexCook.html";
			}
		}
		return "index.html";
	}

	@GetMapping(value = "/success")
	public String defaultAfterLogin(Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/indexAdmin.html";
		}
		if (credentials.getRole().equals(Credentials.COOK_ROLE)) {
			return "cookUser/indexCook.html";
		}
		return "index.html";
	}

	@PostMapping(value = { "/register" })
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult userBindingResult,
			@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
			@RequestParam("immagine") MultipartFile file, Model model) {
		
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		this.userValidator.validate(user, userBindingResult);

		if (!credentialsBindingResult.hasErrors() && !userBindingResult.hasErrors()) {
			if (!file.isEmpty())
				try {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					user.setUrlImage(fileName);
				} catch (IOException e) {
					e.printStackTrace();
					return "formRegisterUser";
				}

			credentials.setUser(user);
			userService.saveUser(user);
			
			credentialsService.saveCredentials(credentials);

			Cook newCook = new Cook();
			newCook.name = user.getName();
			newCook.surname = user.getSurname();
			newCook.year = user.getYear();
			newCook.setUrlImage(user.getUrlImage());
			this.cookService.save(newCook);

			model.addAttribute("user", user);
			return "registrationSuccessful.html";
		}
		return "formRegisterUser.html";
	}
}
