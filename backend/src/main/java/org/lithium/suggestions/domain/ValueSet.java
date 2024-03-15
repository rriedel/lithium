package org.lithium.suggestions.domain;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "valueset")
@Getter
public class ValueSet extends PanacheEntity {

	/** the key is used by the application to identify the value set */
	public String key;

	/** an IETF BCP 47 language tag string */
	public String locale;

	@OneToMany(mappedBy = "valueset", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("rank DESC")
	public List<ValueSetEntry> values;

}
