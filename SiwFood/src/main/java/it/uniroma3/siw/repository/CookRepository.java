package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cook;

public interface CookRepository extends CrudRepository<Cook, Long>{

		public List<Cook> findByName(String name);
		
		public Optional<Cook> findById(Long Id);
		
		public Cook findByNameAndSurname(String name, String surname);
		
		public boolean existsByNameAndSurname(String name, String surname);

		
}
