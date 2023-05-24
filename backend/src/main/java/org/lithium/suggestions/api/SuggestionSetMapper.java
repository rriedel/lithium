package org.lithium.suggestions.api;

import java.util.List;

import org.lithium.suggestions.domain.ValueSet;
import org.lithium.suggestions.domain.ValueSetEntry;
import org.lithium.suggestions.domain.ValueSetEntryComparator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface SuggestionSetMapper {
    
    SuggestionSetDTO map(ValueSet src);
    
    default String[] map(List<ValueSetEntry> src) {
        return src.stream()
			.sorted(ValueSetEntryComparator.instance)
			.map(entry -> entry.value)
			.toArray(String[]::new);
    }
}
