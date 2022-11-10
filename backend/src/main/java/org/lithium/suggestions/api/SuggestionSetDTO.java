package org.lithium.suggestions.api;

import org.lithium.suggestions.domain.ValueSet;

public class SuggestionSetDTO {

    public String key;
	public String locale;
	public String[] values;

    public SuggestionSetDTO(ValueSet set) {
		this.key = set.key;
		this.locale = set.locale;
		this.values = set.values.stream()
			.map(suggestion -> suggestion.value)
			.toArray(String[]::new);   
	} 
}
