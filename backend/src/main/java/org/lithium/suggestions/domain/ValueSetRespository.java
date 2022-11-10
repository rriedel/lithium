package org.lithium.suggestions.domain;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.lithium.suggestions.util.LocaleComparator;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ValueSetRespository implements PanacheRepository<ValueSet> {

    public Uni<List<ValueSet>> listAllForLocale(Locale locale) {
        log.debug("get all valuesets for locale: {}", locale);
        return listAll().map(sets -> filterByLocale(sets, locale));
    }

    private List<ValueSet> filterByLocale(List<ValueSet> valuesets, Locale requestedLocale) {
        var localeComparator = new LocaleComparator<ValueSet>(requestedLocale, set -> new Locale(set.getLocale()));
        return valuesets
                // create map of valuesets grouped by "key"
                .stream().collect(Collectors.groupingBy(ValueSet::getKey)) 
                // for each group (the maps "values"), select only the closest match to requestedLocale 
                .values().stream().map(sets -> sets.stream().sorted(localeComparator).findFirst())
                // collect only non-empty valuesets
                .flatMap(Optional::stream).toList();

    }

}
