package com.exo.ecommerce.infrastructure.http.presentation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormatter {
    private static DecimalFormat numberFormatInstance = null;

    public static String formatCurrency(double amount) {
        if (numberFormatInstance == null) {
            DecimalFormat numberFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.FRANCE);
            numberFormat.applyPattern("###,###.## ¤");
            DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');
            symbols.setCurrency(Currency.getInstance("EUR"));
            numberFormat.setDecimalFormatSymbols(symbols);
            numberFormatInstance = numberFormat;
        }

        return numberFormatInstance.format(amount);
    }
}
