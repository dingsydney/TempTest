package com.anz.fx;

import java.util.HashMap;
import java.util.Map;

public class Table<K1, K2, V> {
    /**
     * simulate a table with two level map.
     */
    private final Map<K1, Map<K2, V>> mapTable = new HashMap<>();

    public void add(K1 key1, K2 key2, V value) {
        Map<K2, V> k2Map = mapTable.get(key1);
        if (k2Map == null) {
            k2Map = new HashMap<>();
            mapTable.put(key1, k2Map);
        }
        k2Map.put(key2, value);
    }

    public V get(K1 key1, K2 key2) {
        Map<K2, V> termsMap = mapTable.get(key1);
        return termsMap == null ? null : termsMap.get(key2);
    }
}
