package com.anz.fx;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Utils {

    /**
     * @param resourcePath has to start with '/'
     * @return properties
     * @throws Exception
     */
    public Properties readProperties(String resourcePath) throws Exception {
        Properties properties = new Properties();
        File file = new File("./config" + resourcePath);
        if (file.exists()) {
            properties.load(new FileInputStream(file));
        } else {
            properties.load(Utils.class.getResourceAsStream(resourcePath));
        }
        return properties;
    }

    /**
     * Assume all currrencies are in three letters
     *
     * @param pairStr
     * @return an array. The first one is base currency and the second is the terms.
     */
    public String[] parseCurrencyPair(String pairStr) throws Exception {
        if (pairStr == null || pairStr.length() != 6)
            throw new Exception("currency pair has to be six characters");
        return new String[]{pairStr.substring(0, 3), pairStr.substring(3, 6)};
    }


}
