package com.anz.fx;

import java.math.BigDecimal;

public class Currency {
    private final String name;
    private final int decimals;

    public Currency(String name, int decimals) {
        this.name = name;
        this.decimals = decimals;
    }

    public String getName() {
        return name;
    }

    public int getDecimals() {
        return decimals;
    }

    public String format(BigDecimal quantity) {
        return quantity.setScale(getDecimals(), BigDecimal.ROUND_HALF_UP).toString();
    }
}
