package com.anz.fx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceConfigurationTest {

    @Mock
    PriceConfiguration priceConfiguration;
    @Mock
    Utils utils;

    Properties currencyDecimal = new Properties();
    Properties rateProperty = new Properties();
    Table<String, String, Pair> table = new Table<>();
    Map<String, Currency> currencies = new HashMap<>();
    MathContext mathContext = new MathContext(128, RoundingMode.HALF_UP);

    @Before
    public void init() throws Exception {
        when(utils.parseCurrencyPair(anyString())).thenCallRealMethod();
        doCallRealMethod().when(priceConfiguration).init();
        when(priceConfiguration.getUtils()).thenReturn(utils);
        when(priceConfiguration.getPairTable()).thenReturn(table);
        when(priceConfiguration.getCurrencies()).thenReturn(currencies);
        when(priceConfiguration.getMathContext()).thenReturn(mathContext);
        when(utils.readProperties("/Currency.properties")).thenReturn(currencyDecimal);
        when(utils.readProperties("/Rates.properties")).thenReturn(rateProperty);

        currencyDecimal.setProperty("USD", "2");
        currencyDecimal.setProperty("AUD", "2");
        currencyDecimal.setProperty("JPY", "0");

        rateProperty.setProperty("USDJPY", "120.50");
        rateProperty.setProperty("USDAUD", "1.5676");
    }

    @Test
    public void initTest() throws Exception {
        priceConfiguration.init();
        for (Object curStr : currencyDecimal.keySet()) {
            assertEquals(BigDecimal.ONE, table.get(curStr.toString(), curStr.toString()).getRate());
        }
        assertEquals(120.50D, table.get("USD", "JPY").getRate().doubleValue(), 0.000001);
        assertEquals(1 / 120.50D, table.get("JPY", "USD").getRate().doubleValue(), 0.000001);
        assertEquals(1.5676D, table.get("USD", "AUD").getRate().doubleValue(), 0.000001);
        assertEquals(1 / 1.5676D, table.get("AUD", "USD").getRate().doubleValue(), 0.000001);
    }
}