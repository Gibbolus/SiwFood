package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.repository.CookRepository;

@Service
public class CookService {

		@Autowired CookRepository cookRepository;
		public Cook findById(Long id) {
			return cookRepository.findById(id).get();
		}
		
		public Iterable<Cook> findAll() {
			return cookRepository.findAll();
		}
		
		public Cook save(Cook cook) {
			return cookRepository.save(cook);
		}
		
		
		public Object findByName(String name) {
			return cookRepository.findByName(name);
		}
		
		public Object findByNameAndSurname(String name, String surname) {
			return cookRepository.findByName(name);
		}
		
		public void deleteById(Long id) {
	        cookRepository.deleteById(id);
	    }
		
}
