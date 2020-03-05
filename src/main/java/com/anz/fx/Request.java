package com.anz.fx;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private final String base;
    private final String terms;
    private final BigDecimal size;

    public Request(String base, String terms, BigDecimal size) {
        this.base = base;
        this.terms = terms;
        this.size = size;
    }

    public String getBase() {
        return base;
    }

    public String getTerms() {
        return terms;
    }

    public BigDecimal getSize() {
        return size;
    }


    private static Pattern pattern = Pattern.compile("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)");

    public static Request from(String input) throws Exception {
        try {
            input = input.trim().toUpperCase();
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return new Request(matcher.group(1), matcher.group(4), new BigDecimal(matcher.group(2)));
            } else
                throw new Exception("Unrecognised format.");
        } catch (Exception e) {
            throw new Exception("Unrecognised format.");
        }
    }
}
