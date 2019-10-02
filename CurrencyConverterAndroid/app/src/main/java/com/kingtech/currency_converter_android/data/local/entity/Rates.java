package com.kingtech.currency_converter_android.data.local.entity;

import java.util.List;

public class Rates {

    private List<ConversionRate> rates;

    public Rates(List<ConversionRate> rates) {
        this.rates = rates;
    }

    public List<ConversionRate> getRates() {
        return rates;
    }
}
