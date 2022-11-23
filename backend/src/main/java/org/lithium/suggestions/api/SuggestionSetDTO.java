package org.lithium.suggestions.api;

import org.lithium.suggestions.domain.ValueSet;
import org.lithium.suggestions.domain.ValueSetEntryComparator;

public class SuggestionSetDTO {

    public String key;
	public String locale;
	public String[] values;

    public SuggestionSetDTO(ValueSet set) {
		this.key = set.key;
		this.locale = set.locale;
		this.values = set.values.stream()
			.sorted(ValueSetEntryComparator.instance)
			.map(entry -> entry.value)
			.toArray(String[]::new);   
	} 
}
