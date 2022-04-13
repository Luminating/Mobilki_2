package com.example.mobilki_2;

import android.graphics.Bitmap;

public class Product {
    private String category;
    private String manufacturer;
    private String description;
    private String productURL;
    private Bitmap productBmp;
    private int price;
    private boolean isFavorite = false;

    public Product(String category, String manufacturer, String description, String productURL, Bitmap productBmp, int price) {
        this.category = category;
        this.manufacturer = manufacturer;
        this.description = description;
        this.productURL = productURL;
        this.productBmp = productBmp;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public String getProductURL() {
        return productURL;
    }

    public Bitmap getProductBmp() {
        return productBmp;
    }

    public int getPrice() {
        return price;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFavorite(){
        return isFavorite;
    }
}
