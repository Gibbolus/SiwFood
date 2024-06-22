package it.uniroma3.siw.model;

import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.*;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private String unitOfMeasure;

	private String urlImage;

	@Column(length = 2000)
	private String description;

	@ElementCollection
	/*
	 * Questa annotazione viene utilizzata per indicare che la mappa non è una
	 * semplice collezione Java, ma deve essere trattata come una collezione di
	 * elementi incorporati (embedded) nella stessa entità. In altre
	 * parole, @ElementCollection dice a JPA che la mappa deve essere mappata come
	 * una collezione di elementi di valore associati alla classe Ingrediente.
	 */

	@CollectionTable(name = "quantita_ricetta", joinColumns = @JoinColumn(name = "ingredient_id"))
	/*
	 * Questa annotazione specifica il nome della tabella che verrà utilizzata per
	 * memorizzare la collezione di elementi. Nel nostro caso, la tabella si chiama
	 * quantita_per_ricetta. L'elemento joinColumns all'interno di @CollectionTable
	 * specifica la colonna che verrà utilizzata per unire (join) questa tabella con
	 * la tabella della classe che contiene la collezione, ovvero Ingrediente.
	 */

	@Column(name = "quantity")
	private Map<Long, Integer> quantityToRecipe;

	public Map<Long, Integer> getQuantityToRecipe() {
		return quantityToRecipe;
	}

	public void setQuantityToRecipe(Map<Long, Integer> quantityToRecipe) {
		this.quantityToRecipe = quantityToRecipe;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Ingredient i = (Ingredient) o;
		return Objects.equals(name, i.name);
	}

}
