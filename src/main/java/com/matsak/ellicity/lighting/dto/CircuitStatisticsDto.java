package com.matsak.ellicity.lighting.dto;

import com.matsak.ellicity.lighting.entity.sections.Circuit;

public class CircuitStatisticsDto {
    private Circuit circuit;
    private String power;
    private String cost;

    public CircuitStatisticsDto(Circuit circuit, String power, String cost) {
        this.circuit = circuit;
        this.power = power;
        this.cost = cost;
    }

    public CircuitStatisticsDto() {
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
