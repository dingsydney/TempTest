package com.anz.fx;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    @Test
    public void parseFromStrTest() throws Exception {
        String input = "AUD 100.00 in USD";
        Request request = Request.from(input);
        assertEquals("AUD", request.getBase());
        assertEquals("USD", request.getTerms());
        assertEquals(100D, request.getSize().doubleValue(), 0.000001);
    }

    @Test(expected = Exception.class)
    public void parseFromStrExceptiobTest() throws Exception {
        String input = "AUD/USD in 100";
        Request request = Request.from(input);
    }
}