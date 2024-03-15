package org.lithium.suggestions.api;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.annotation.Nonnull;

@Schema(
	name = "Suggestions",
	description  = "a set of string values suggested for a single key and locale"
)
public class SuggestionSetDTO {

	@Schema(
		description = "the key that identifies this set of suggestions"
	)
	@Nonnull
	public String key;

	/** a ISO irgendwas locale */
	@Nonnull
	public String locale;

	@Schema(
		description = "list of suggested string values"
	) 
	@Nonnull
	public String[] values;
}
