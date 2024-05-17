package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cook;

public interface CookRepository extends CrudRepository<Cook, Long> {
	
	public List<Cook> findByName(String name);

	public boolean existsByNameAndSurname(String name, String surname);
	
}
