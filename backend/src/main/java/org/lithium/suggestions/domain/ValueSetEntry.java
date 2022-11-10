package org.lithium.suggestions.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
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
	@XmlTransient
	public ValueSet valueset;
}
