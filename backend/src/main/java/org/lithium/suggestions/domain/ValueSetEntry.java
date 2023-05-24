package org.lithium.suggestions.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * a single (localized) entry inside a value set
 */
@Entity
@Table(name = "valuesetentry")
@Getter
public class ValueSetEntry extends PanacheEntity {

	/** the entries textual value */
    public String value;

	/** the entries rank inside the value set */
	public int rank;

    @ManyToOne
	@JoinColumn(name = "set")
	public ValueSet valueset;
}
