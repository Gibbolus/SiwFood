package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private String urlImage;

	@Column(length = 2000)
	private String description;

	private Integer cookTime;

	private String difficulty;

	private String recipeType;

	@ManyToOne
	private Cook cook;

	@ManyToMany
	private Set<Ingredient> ingredientsUtilizzati;

	public Set<Ingredient> getIngredientsUtilizzati() {
		return ingredientsUtilizzati;
	}

	public void setIngredientsUtilizzati(Set<Ingredient> ingredientsUtilizzati) {
		this.ingredientsUtilizzati = ingredientsUtilizzati;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getCookTime() {
		return cookTime;
	}

	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, cook);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Recipe r = (Recipe) o;
		return Objects.equals(name, r.name) && Objects.equals(cook, r.cook);
	}

}
