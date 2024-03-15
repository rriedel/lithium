package org.lithium.suggestions.util;

import java.util.Locale;

import lombok.NonNull;

public class SimpleLocaleComparator extends LocaleComparator<Locale> {

    public SimpleLocaleComparator(@NonNull Locale requestedLocale) {
        super(requestedLocale, locale -> locale);
    }
    
}
