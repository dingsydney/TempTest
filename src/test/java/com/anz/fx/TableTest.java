package com.anz.fx;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableTest {

    Table<String, String, String> table;

    @Before
    public void init() {
        table = new Table<>();
    }

    @Test
    public void tableTest() {
        table.add("1", "1", "5");
        assertEquals("5", table.get("1", "1"));

        table.add("1", "1", "55");
        assertEquals("55", table.get("1", "1"));

    }
}