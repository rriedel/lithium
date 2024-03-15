package org.lithium.suggestions.util;

import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_LANGUAGE;
import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_COUNTRY;
import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_SCRIPT;

import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * comparator to support sorting of objects according to their "distance"
 * to a reference locale (provided). Also, a function to calculate the 
 * objects locale value has to be provided. 
 */
@Data
@RequiredArgsConstructor
@Slf4j
public class LocaleComparator<T> implements Comparator<T> {

    /** the locale that is used as reference point */
    @NonNull
    private Locale referenceLocale;

    /** a function that calculates the {@link java.util.Locale} value for an object of type T  */
    @NonNull
    private Function<T, Locale> calcLocaleFn;

    @Override
    public int compare(T t1, T t2) {
        int d1 = distance(calcLocaleFn.apply(t1), referenceLocale);
        int d2 = distance(calcLocaleFn.apply(t2), referenceLocale);
        return d1 - d2;
    }

    /**
     * Calculate the "distance" of two locales.
     * 
     * @param l1
     * @param l2
     * @return
     */
    private int distance(Locale l1, Locale l2) {
        int dist = distance(l1, l2, l -> l.getLanguage(), WEIGHT_LANGUAGE)
            + distance(l1, l2, l -> l.getCountry(), WEIGHT_COUNTRY)
            + distance(l1, l2, l -> l.getVariant(), WEIGHT_SCRIPT);
        //log.info("distance: {} - {}: {}", l1, l2, dist);
        return dist;
    }

    private int distance(Locale l1, Locale l2, Function<Locale, String> discriminatorFn, int weight) {
        String d1 = discriminatorFn.apply(l1);
        String d2 = discriminatorFn.apply(l2);
        if (d1.equalsIgnoreCase(d2)) {
            return 0;
        }
        if (empty(d1) || empty(d2)) {
            return weight;
        }
        return 2 * weight;
    }

    private boolean empty(String s) {
        return s == null ? true : s.length() == 0;
    }

}
