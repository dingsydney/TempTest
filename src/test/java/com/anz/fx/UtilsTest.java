package com.anz.fx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {
    @Mock
    Utils utils;

    @Test
    public void readPropertyTest() throws Exception {
        when(utils.readProperties(anyString())).thenCallRealMethod();
        Properties properties = utils.readProperties("/test.properties");
        assertEquals(1, properties.size());
        assertEquals("b", properties.getProperty("a"));
    }

    @Test
    public void parseCurrencyPairTest() throws Exception {
        when(utils.parseCurrencyPair(anyString())).thenCallRealMethod();
        String[] strArray = utils.parseCurrencyPair("ABC123");
        assertEquals("ABC", strArray[0]);
        assertEquals("123", strArray[1]);
    }

    @Test(expected = Exception.class)
    public void parseCurrencyPairExceptionTest() throws Exception {
        when(utils.parseCurrencyPair(anyString())).thenCallRealMethod();
        utils.parseCurrencyPair("ABC12");
    }

}
