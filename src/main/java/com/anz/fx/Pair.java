package com.anz.fx;

import java.math.BigDecimal;
import java.util.Objects;

public class Pair {
    private final Currency base;
    private final Currency terms;
    private BigDecimal rate;

    public Pair(Currency base, Currency terms) throws Exception {
        if (base == null || terms == null) throw new Exception("Base and Terms can't be empty");
        this.base = base;
        this.terms = terms;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(base, pair.base) &&
                Objects.equals(terms, pair.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, terms);
    }
}
