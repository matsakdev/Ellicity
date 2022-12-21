package com.matsak.ellicity.lighting.dto;

import com.matsak.ellicity.lighting.entity.sections.Circuit;

import java.util.Map;

public class CurrentSystemStatisticsDto extends SystemStatisticsDto {

    private double predictedCost;

    public CurrentSystemStatisticsDto(String cost, Map<String, Double> power, CircuitStatisticsDto mostPowerfulCircuit, double predictedCost) {
        super(cost, power, mostPowerfulCircuit);
        this.predictedCost = predictedCost;
    }

    public double getPredictedCost() {
        return predictedCost;
    }

    public void setPredictedCost(double predictedCost) {
        this.predictedCost = predictedCost;
    }
}
