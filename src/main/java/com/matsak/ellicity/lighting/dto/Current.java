package com.matsak.ellicity.lighting.dto;

public class Current {
    double value;

    public Current(double value) {
        this.value = value;
    }

    public Current() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        //for 3 digits after the point
        this.value = value;
    }
}
