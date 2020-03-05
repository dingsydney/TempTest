package com.anz.fx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyTest {

    @Mock
    Currency currency;

    @Before
    public void init() {
    }

    @Test
    public void formatTest() {
        when(currency.format(any(BigDecimal.class))).thenCallRealMethod();
        BigDecimal decimal = new BigDecimal("12.45646");

        when(currency.getDecimals()).thenReturn(0);
        assertEquals("12", currency.format(decimal));

        when(currency.getDecimals()).thenReturn(1);
        assertEquals("12.5", currency.format(decimal));

        when(currency.getDecimals()).thenReturn(2);
        assertEquals("12.46", currency.format(decimal));

    }
}