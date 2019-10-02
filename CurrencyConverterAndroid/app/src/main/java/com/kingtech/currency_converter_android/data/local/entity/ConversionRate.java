package com.kingtech.currency_converter_android.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ConversionRate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String country;
    private Double rate;

    public ConversionRate(String country, Double rate) {
        this.country = country;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ConversionRate{" +
                "country='" + country + '\'' +
                ", rate=" + rate +
                '}';
    }
}
