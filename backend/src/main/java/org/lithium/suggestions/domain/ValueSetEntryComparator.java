package org.lithium.suggestions.domain;

import java.util.Comparator;

public class ValueSetEntryComparator implements Comparator<ValueSetEntry> {

    public static final Comparator<ValueSetEntry> instance = new ValueSetEntryComparator();

    @Override
    public int compare(ValueSetEntry o1, ValueSetEntry o2) {
        return o1.rank - o2.rank;
    }
    
}
