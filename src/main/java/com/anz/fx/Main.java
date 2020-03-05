package com.anz.fx;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static String sample = "<ccy1> <amount1> in <ccy2>";

    public static void main(String[] args) throws Exception {
        System.out.println("loading price configuration");
        PriceConfiguration priceConfiguration = new PriceConfiguration();
        priceConfiguration.init();
        Pricing pricing = new Pricing(priceConfiguration);
        System.out.println("Price configuration loaded");
        System.out.println("To calculate rate, please enter: " + sample);
        System.out.println("such as: AUD 100.00 in USD");
        System.out.println("supported ccy: AUD, USD, CAD, CNY, EUR, GBP, NZD, JPY, CZK, DKK, NOK");
        System.out.println("Control+C or exit to quit");

        String cmd;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while ((cmd = in.readLine()) != null) {
                if ("quit".equalsIgnoreCase(cmd) || "exit".equalsIgnoreCase(cmd)) {
                    System.out.println("Thanks for using rate calculator. See you next time!");
                    System.exit(0);
                }
                try {
                    Request request = Request.from(cmd);
                    String quantity = pricing.handleRequest(request);
                    System.out.println(request.getBase() + " " + request.getSize() + " = " + request.getTerms() + " " + quantity);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
