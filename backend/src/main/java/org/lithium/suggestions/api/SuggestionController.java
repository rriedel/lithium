package org.lithium.suggestions.api;

import java.util.List;
import java.util.Locale;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.lithium.suggestions.domain.ValueSetRespository;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@Path("/suggestions")
@Slf4j
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
	@GET
	public Uni<List<SuggestionSetDTO>> getAllSuggestions() {
		Locale locale = new Locale("de"); // TODO aus dem Request besorgen
		log.debug("find all suggestions in locale '{}'", locale);
		return repo.listAllForLocale(locale)
			.map(sets -> sets.stream().map(set -> mapper.map(set)).toList());
	}
}
