package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public String name;

	public String surname;

	public String urlImage;

	@Column(length = 2000)
	public String description;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	public LocalDate year;

	@OneToMany(mappedBy = "cook", fetch = FetchType.EAGER)
	private List<Recipe> recipes;

	public Cook() {
		this.recipes = new LinkedList<>();
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getYear() {
		return year;
	}

	public void setYear(LocalDate year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, surname);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cook cook = (Cook) o;
		return Objects.equals(name, cook.name) && Objects.equals(surname, cook.surname);
	}
}
