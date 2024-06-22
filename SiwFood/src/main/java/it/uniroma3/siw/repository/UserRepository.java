package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByName(String name);
	
	public User findBySurname(String surname);
		
	public User findByNameAndSurname(String name, String surname);
}

