package org.lithium.suggestions.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Getter;

@Entity
@Table(name = "valueset")
@Getter
public class ValueSet extends PanacheEntity {

	/** the key is used by the application to identify the value set */
	public String key;

	/** an IETF BCP 47 language tag string */
	public String locale;

	@XmlTransient
	@OneToMany(mappedBy = "valueset", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("rank DESC")
	public List<ValueSetEntry> values;

}
