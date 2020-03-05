package com.anz.fx;

import java.math.BigDecimal;

public class Pricing {
    private final PriceConfiguration configuration;

    private final String[] crossCurrency = new String[]{"USD", "EUR"};
    private final Table<String, String, BigDecimal> crossingRateCache = new Table();

    public Pricing(PriceConfiguration configuration) {
        this.configuration = configuration;
    }

    BigDecimal getRate(String base, String terms) throws Exception {
        Pair pair = getConfiguration().getPair(base, terms);
        if (pair != null) {
            return pair.getRate();
        } else {
            BigDecimal rate = getCrossingRateCache().get(base, terms);
            if (rate != null) {
                return rate;
            }
            return calCrossRate(base, terms);
        }
    }

    private BigDecimal calCrossRate(String base, String terms) throws Exception {
        for (String crossStr : getCrossCurrency()) {
            Pair baseCross = getConfiguration().getPair(base, crossStr);
            Pair crossTerms = getConfiguration().getPair(crossStr, terms);
            if (baseCross != null && crossTerms != null) {
                BigDecimal rate = baseCross.getRate().multiply(crossTerms.getRate());
                getCrossingRateCache().add(base, terms, rate);
                return rate;
            }
        }
        throw new Exception("Unable to find rate for " + base + "/" + terms);
    }


    public PriceConfiguration getConfiguration() {
        return configuration;
    }

    public String[] getCrossCurrency() {
        return crossCurrency;
    }

    Table<String, String, BigDecimal> getCrossingRateCache() {
        return crossingRateCache;
    }

    public String handleRequest(Request request) throws Exception {
        BigDecimal rate = getRate(request.getBase(), request.getTerms());
        BigDecimal quantity = rate.multiply(request.getSize());
        return getConfiguration().getCurrency(request.getTerms()).format(quantity);
    }
}
