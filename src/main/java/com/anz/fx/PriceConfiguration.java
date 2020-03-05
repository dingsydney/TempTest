package com.anz.fx;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PriceConfiguration {

    private final Map<String, Currency> currencies = new HashMap();
    private final Table<String, String, Pair> pairTable = new Table<>();
    private final Utils utils = new Utils();
    private final MathContext mathContext = new MathContext(128, RoundingMode.HALF_UP);

    public void init() throws Exception {
        loadCurrency();
        loadRate();
    }

    private void loadCurrency() throws Exception {
        Properties properties = getUtils().readProperties("/Currency.properties");
        for (Object str : properties.keySet()) {
            String currStr = str.toString();
            Currency currency = new Currency(currStr, Integer.parseInt(properties.get(str).toString()));
            getCurrencies().put(currStr, currency);

            Pair pair = new Pair(currency, currency);
            pair.setRate(BigDecimal.ONE);
            getPairTable().add(currStr, currStr, pair);
        }
    }

    private void loadRate() throws Exception {
        Properties properties = getUtils().readProperties("/Rates.properties");
        for (Object key : properties.keySet()) {
            String strPair = key.toString();
            String[] currPair = getUtils().parseCurrencyPair(strPair);

            Pair pair = new Pair(getCurrencies().get(currPair[0]), getCurrencies().get(currPair[1]));
            BigDecimal rate = new BigDecimal(properties.getProperty(strPair));
            pair.setRate(rate);
            getPairTable().add(currPair[0], currPair[1], pair);

            Pair reversedPair = new Pair(getCurrencies().get(currPair[1]), getCurrencies().get(currPair[0]));
            BigDecimal reversedRate = reciprocal(rate);
            reversedPair.setRate(reversedRate);
            getPairTable().add(currPair[1], currPair[0], reversedPair);
        }

    }

    private BigDecimal reciprocal(BigDecimal decimal) throws Exception {
        if (decimal.equals(BigDecimal.ZERO)) throw new Exception("can't calculate reciprocal for zero");
        return BigDecimal.ONE.divide(decimal, getMathContext());
    }

    public Pair getPair(String base, String terms) {
        return getPairTable().get(base, terms);
    }

    public Currency getCurrency(String currencyStr) {
        return getCurrencies().get(currencyStr);
    }

    MathContext getMathContext() {
        return mathContext;
    }

    Table<String, String, Pair> getPairTable() {
        return pairTable;
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    Utils getUtils() {
        return utils;
    }

}
