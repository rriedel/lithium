package org.lithium.suggestions.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocaleComparatorTest {

    @Getter
    @AllArgsConstructor
    class Foo {
        private Locale locale;
    }

    @Test
    public void testLocaleComparator() {
        String[] in = { "jp", "de-at", "en-uk", "en", "de", "de-de", "fr" };
        Locale requestedLocale = Locale.forLanguageTag("en-us");
        Locale expectedMatch = Locale.forLanguageTag("en");
        Function<Foo, Locale> calcLocaleFn = foo -> foo.getLocale();

        List<Foo> foos = Arrays.stream(in).map(l -> new Foo(Locale.forLanguageTag(l))).collect(Collectors.toList());
        var localeComparator = new LocaleComparator<Foo>(requestedLocale, calcLocaleFn);
        Collections.sort(foos, localeComparator);        
        log.debug("sorted: {}", foos.stream().map(foo -> foo.toString()).collect(Collectors.joining(", ")));
        Locale bestMatch = calcLocaleFn.apply(foos.get(0));
        
        assertEquals(bestMatch, expectedMatch);
    }
}
