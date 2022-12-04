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
        this.value = value;
    }
}