package com.matsak.ellicity.lighting.dto;

import java.util.Map;

public class SystemStatisticsDto {
    private String cost;
    private Map<String, Double> power;
    private CircuitStatisticsDto mostPowerfulCircuit;

    public SystemStatisticsDto(String cost, Map<String, Double> power, CircuitStatisticsDto mostPowerfulCircuit) {
        this.cost = cost;
        this.power = power;
        this.mostPowerfulCircuit = mostPowerfulCircuit;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Map<String, Double> getPower() {
        return power;
    }

    public void setPower(Map<String, Double> power) {
        this.power = power;
    }

    public CircuitStatisticsDto getMostPowerfulCircuit() {
        return mostPowerfulCircuit;
    }

    public void setMostPowerfulCircuit(CircuitStatisticsDto mostPowerfulCircuit) {
        this.mostPowerfulCircuit = mostPowerfulCircuit;
    }
}
