package com.matsak.ellicity.lighting.payload;

public class SetSystemPrice {
    private Double price;

    public SetSystemPrice(Double price) {
        this.price = price;
    }

    public SetSystemPrice() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
