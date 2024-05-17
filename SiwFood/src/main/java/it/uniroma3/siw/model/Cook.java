package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String surname;
	
	private String urlImage;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate year;

	@OneToMany(mappedBy = "cook")
	private List<Recipe> cook;

	public Cook(){
		this.cook = new LinkedList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getYear() {
		return year;
	}

	public void setYear(LocalDate year) {
		this.year = year;
	}
	
	public List<Recipe> getCookOf() {
		return cook;
	}

	public void setCookOf(List<Recipe> cookedRecepie) {
		this.cook = cookedRecepie;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(name, surname);
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Cook artist = (Cook) o;
	    return Objects.equals(name, artist.name) &&
	            Objects.equals(surname, artist.surname);
	}



	
	
}
