package com.matsak.ellicity.lighting.dto;

public class Voltage {
    double value;
    public Voltage(double value) {
        this.value = value;
    }

    public Voltage() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        //for 3 digits after the point
        this.value = Math.round(value * 1000) / 1000.0;
    }
}