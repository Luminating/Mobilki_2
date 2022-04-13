package com.example.mobilki_2;

public class Filter {
    private String manufacturer;
    private String description;
    private int priceLess;
    private int priceMore;

    public Filter() {
        this.manufacturer = "";
        this.description = "";
        this.priceLess = Integer.MAX_VALUE;
        this.priceMore = 0;
    }

    public Filter(String manufacturer, String description, int priceLess, int priceMore) {
        this.manufacturer = manufacturer;
        this.description = description;
        this.priceLess = priceLess;
        this.priceMore = priceMore;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public int getPriceLess() {
        return priceLess;
    }

    public int getPriceMore() {
        return priceMore;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriceLess(int priceLess) {
        this.priceLess = priceLess;
    }

    public void setPriceMore(int priceMore) {
        this.priceMore = priceMore;
    }
}
