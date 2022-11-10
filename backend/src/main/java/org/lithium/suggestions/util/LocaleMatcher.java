package org.lithium.suggestions.util;

import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_COUNTRY;
import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_LANGUAGE;
import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_PERFECT;
import static org.lithium.suggestions.util.LocaleConstants.WEIGHT_SCRIPT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;


public class LocaleMatcher {

    private static final Comparator<WeightedLocale> WEIGHTEDLOCALE_COMPARATOR = new Comparator<WeightedLocale>() {
        @Override
        public int compare(WeightedLocale l1, WeightedLocale l2) {
            return l2.getWeight() - l1.getWeight();
        }
    };

    public static List<Locale> match(Collection<Locale> supportedLocales, List<Locale> requestedLocales) {
        SortedSet<WeightedLocale> candidates = new TreeSet<WeightedLocale>(WEIGHTEDLOCALE_COMPARATOR);

        for (int i = 0; i < requestedLocales.size(); i++) {
            Locale requestedLocale = requestedLocales.get(i);
            doMatch(supportedLocales, requestedLocale, requestedLocales.size() - i, candidates);
        }

        return createResult(candidates);
    }

    public static List<Locale> match(Collection<Locale> supportedLocales, Locale requestedLocale) {
        SortedSet<WeightedLocale> candidates = new TreeSet<WeightedLocale>(WEIGHTEDLOCALE_COMPARATOR);

        doMatch(supportedLocales, requestedLocale, 1, candidates);
        return createResult(candidates);
    }

    public static Locale singleMatch(Collection<Locale> supportedLocales, Locale requestedLocale, Locale defaultLocale) {
        List<Locale> matches = match(supportedLocales, requestedLocale);
        return matches.size() > 0 ? matches.get(0) : defaultLocale;
    }

    public static Locale singleMatch(Collection<Locale> supportedLocales, Locale requestedLocale) {
        return singleMatch(supportedLocales, requestedLocale, null);
    }

    private static void doMatch(Collection<Locale> supportedLocales, Locale requestedLocale, int requestedLocaleWeightFactor,
            SortedSet<WeightedLocale> candidates) {

        for (Locale supportedLocale : supportedLocales) {
            int weight = 0;
            if (requestedLocale.equals(supportedLocale)) {
                // perfect match
                candidates.add(new WeightedLocale(requestedLocale, WEIGHT_PERFECT * requestedLocaleWeightFactor));
                continue;
            }

            if (requestedLocale.getLanguage().equalsIgnoreCase(supportedLocale.getLanguage())) {
                weight += WEIGHT_LANGUAGE;
            }
            if (requestedLocale.getCountry().equalsIgnoreCase(supportedLocale.getCountry())) {
                weight += WEIGHT_COUNTRY;
            }

            if (requestedLocale.getScript().equalsIgnoreCase(supportedLocale.getScript())) {
                weight += WEIGHT_SCRIPT;
            }

            weight *= requestedLocaleWeightFactor;

            if (weight > 0) {
                candidates.add(new WeightedLocale(supportedLocale, weight));
            }
        }

        /*
         * StringBuilder b = new
         * StringBuilder("candidates for requested locale ").append(requestedLocale.
         * toLanguageTag()).append(": "); for(WeightedLocale candidate : candidates) {
         * b.append("[").append(candidate.locale.toLanguageTag()).append(", ").append(
         * candidate.weight).append("], "); } LOG.debug(b.toString());
         */
    }

    private static List<Locale> createResult(SortedSet<WeightedLocale> candidates) {
        List<Locale> result = new ArrayList<Locale>();
        for (WeightedLocale candidate : candidates) {
            if (!result.contains(candidate.getLocale())) {
                // LOG.debug("add candidate locale {} with weight {}",
                // candidate.locale.toLanguageTag(), candidate.weight);
                result.add(candidate.getLocale());
            }
        }

        // LOG.debug("locales matched: {}", formatLocales(result));
        return result;
    }

    public String formatLocales(Collection<Locale> locales) {
        StringBuilder b = new StringBuilder();
        for (Locale l : locales) {
            b.append(l.toLanguageTag()).append(", ");
        }
        return b.toString();
    }

}
