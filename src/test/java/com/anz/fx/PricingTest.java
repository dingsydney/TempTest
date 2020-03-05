package com.anz.fx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PricingTest {

    @Mock
    PriceConfiguration priceConfiguration;

    @Mock
    Pricing pricing;

    String base = "AUD";
    String terms = "JPY";
    String cross = "USD";
    Pair baseCross;
    Pair crossTerms;
    Table<String, String, BigDecimal> crossingRateCache = new Table();
    BigDecimal baseCrossRate = new BigDecimal(0.8D);

    @Before
    public void init() throws Exception {
        Currency baseCur = new Currency(base, 2);
        Currency termsCur = new Currency(terms, 0);
        Currency crossCur = new Currency(cross, 2);
        baseCross = new Pair(baseCur, crossCur);
        baseCross.setRate(baseCrossRate);
        crossTerms = new Pair(crossCur, termsCur);
        crossTerms.setRate(new BigDecimal(0.01D));
        when(priceConfiguration.getPair(base, cross)).thenReturn(baseCross);
        when(priceConfiguration.getPair(cross, terms)).thenReturn(crossTerms);
        when(pricing.getConfiguration()).thenReturn(priceConfiguration);
        when(pricing.getCrossCurrency()).thenReturn(new String[]{cross});
        when(pricing.getCrossingRateCache()).thenReturn(crossingRateCache);
        when(priceConfiguration.getCurrency("USD")).thenReturn(crossCur);
        when(priceConfiguration.getCurrency("JPY")).thenReturn(termsCur);
        when(priceConfiguration.getCurrency("AUD")).thenReturn(baseCur);

        when(pricing.getRate(anyString(), anyString())).thenCallRealMethod();
        when(pricing.handleRequest(any(Request.class))).thenCallRealMethod();
    }

    @Test
    public void getRate_crossRate_Test() throws Exception {
        double expected = 0.8 * 0.01D;
        assertEquals(expected, pricing.getRate("AUD", "JPY").doubleValue(), 0.00001);
        //also check cache
        assertEquals(expected, pricing.getCrossingRateCache().get("AUD", "JPY").doubleValue(), 0.000001);
    }

    @Test
    public void getRate_pairNotExist_Test() throws Exception {
        try {
            pricing.getRate("AUD", "ABC");
        } catch (Exception e) {
            assertEquals("Unable to find rate for AUD/ABC", e.getMessage());
        }
    }

    @Test
    public void handleRequestTest() throws Exception {
        BigDecimal size = new BigDecimal("1000");
        Request request = mock(Request.class);
        when(request.getBase()).thenReturn("AUD");
        when(request.getTerms()).thenReturn("USD");
        when(request.getSize()).thenReturn(size);
        assertEquals("800.00", pricing.handleRequest(request));

        when(request.getBase()).thenReturn("AUD");
        when(request.getTerms()).thenReturn("JPY");
        when(request.getSize()).thenReturn(size);
        assertEquals("8", pricing.handleRequest(request));

    }

}