package it.uniroma3.siw.model;


import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private Integer quantity;
	
	private String unitOfMeasure;

	private String urlImage;
	
	@Column(length = 2000)
	private String description;
	
	

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
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(name, quantity);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ingredient i = (Ingredient) o;
		return Objects.equals(name, i.name) && Objects.equals(quantity, i.quantity);
	}

}
