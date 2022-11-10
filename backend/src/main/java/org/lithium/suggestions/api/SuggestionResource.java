package org.lithium.suggestions.api;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.lithium.suggestions.domain.ValueSetRespository;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@Path("/suggestions")
@Slf4j
public class SuggestionResource {

	@Inject
	ValueSetRespository repo;

	/*
	 * Response:
	 * 	[
	 * 		{
	 * 			"key": "phone-label",
	 * 			"values": [
	 * 				"Geschäftlich",
	 * 				"Privat",
	 * 				"Fax",
	 * 				"Zu Hause"
	 * 			]
	 * 		},
	 * 		{
	 * 			...
	 * 		}
	 * ]
	 */
	@GET
	public Uni<List<SuggestionSetDTO>> getAllSuggestions() {
		Locale locale = new Locale("de"); // TODO aus dem Request besorgen
		log.debug("find all suggestions in locale '{}'", locale);
		return repo.listAllForLocale(locale)
			.map(sets -> sets.stream().map(set -> new SuggestionSetDTO(set)).toList());
	}
}
