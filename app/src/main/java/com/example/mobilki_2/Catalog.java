package com.example.mobilki_2;

import androidx.core.util.Pair;

import java.util.ArrayList;

public class Catalog {
    private static Catalog instance;
    private ArrayList<Product> catalog = new ArrayList<>();
    private ArrayList<Pair<String, String>> manufacturers = new ArrayList<>();
    private Filter filter = new Filter();
    private int currentCategoryIndex = -1;
    private Product selectedProduct = null;

    public static synchronized Catalog getInstance(){
        if (instance == null) {
            instance = new Catalog();
        }
        return instance;
    }

    private Catalog(){
    }

    public ArrayList<Product> getCatalog() {
        return catalog;
    }


    public ArrayList<Product> getCatalogByCategory(String category) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : catalog){
            if (product.getCategory().equals(category)){
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> getCatalogByCategoryWithFilter(String category) {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : catalog){
            if (product.getCategory().equals(category)){
                if ((!filter.getManufacturer().equals("")) && (!product.getManufacturer().toLowerCase().contains(filter.getManufacturer().toLowerCase()))){
                    continue;
                }
                if ((!filter.getDescription().equals("")) && (!product.getDescription().toLowerCase().contains(filter.getDescription().toLowerCase()))){
                    continue;
                }
                if (product.getPrice() < filter.getPriceMore()){
                    continue;
                }
                if (product.getPrice() > filter.getPriceLess()) {
                    continue;
                }
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Product> getCatalogByFavorites() {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : catalog){
            if (product.isFavorite()){
                result.add(product);
            }
        }
        return result;
    }

    public ArrayList<Pair<String, String>> getManufacturers() { return manufacturers; }

    public ArrayList<String> getManufacturersByCategory(String category) {
        ArrayList<String> result = new ArrayList<>();
        for (Pair<String, String> pair: manufacturers) {
            if (pair.first.equals(category)){
                result.add(pair.second);
            }
        }
        return result;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public int getCurrentCategoryIndex() {
        return currentCategoryIndex;
    }

    public void setCurrentCategoryIndex(int currentCategoryIndex) {
        this.currentCategoryIndex = currentCategoryIndex;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}
