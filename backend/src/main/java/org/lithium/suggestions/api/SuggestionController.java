package org.lithium.suggestions.api;

import java.util.List;
import java.util.Locale;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.lithium.suggestions.domain.ValueSetRespository;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

@Path("/suggestions")
@Slf4j
@Tag(name = "Suggestions", description = "operations to deal with suggestions", externalDocs = @ExternalDocumentation(description = "docs desc"))
public class SuggestionController {

	@Inject
	ValueSetRespository repo;

	@Inject
	SuggestionSetMapper mapper;

	/*
	Response:
	[
		{
			"key": "item.label.phone",
			"locale": "de",
			"values": [
				"Zu Hause",
				"Fax",
				"Handy",
				"Geschäftlich"
			]
		},
	]
	 */
	@Operation(
		summary = "Get all Suggestions",
		description = "for a single locale (provided along with the request), return all suggestion sets"
	)
	@GET
	public Uni<List<SuggestionSetDTO>> getAllSuggestions() {
		Locale locale = new Locale("de"); // TODO aus dem Request besorgen
		log.debug("find all suggestions in locale '{}'", locale);
		return repo.listAllForLocale(locale)
			.map(sets -> sets.stream().map(set -> mapper.map(set)).toList());
	}
}
