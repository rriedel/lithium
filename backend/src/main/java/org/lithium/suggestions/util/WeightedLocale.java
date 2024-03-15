package org.lithium.suggestions.util;

import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
final class WeightedLocale {
    private Locale locale;
    private int weight;
}
