package com.matsak.ellicity.lighting.service.statistics;

import com.matsak.ellicity.lighting.dto.CircuitStatisticsDto;
import com.matsak.ellicity.lighting.dto.SystemStatisticsDto;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.lighting.service.sections.SystemService;
import com.matsak.ellicity.lighting.util.PowerConverter;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public abstract class SystemStatistics {

    SystemService systemService;

    CircuitService circuitService;

    protected System system;

    protected SystemStatistics(SystemService systemService, CircuitService circuitService, System system) {
        this.system = system;
        this.systemService = systemService;
        this.circuitService = circuitService;
        //todo SOLID? Dependency inversion
    }

    public Double getCost() {
        if (system.getPrice() != null) {
            return system.getPrice() * getPowerOfPeriod() / 1000; //because of power is Wh and cost is kWh
        } else {
            throw new IllegalArgumentException("Price is not set");
        }
    }

    public abstract Integer getDaysCountStatisticsShows();

    public Optional<CircuitStatisticsDto> getMostPowerfulCircuit() {
        List<Circuit> circuits = getCircuits();
        Circuit mostPowerful = null;
        double biggestPower = 0;
        for (Circuit circuit : circuits) {
            List<MeasurementRecord> measurements = circuitService.getMeasurementsOfLastDays(getDaysCountStatisticsShows(), circuit);
            Double power = measurements.stream()
                    .map(measurement -> (measurement.getMeasurement().getVoltage().getValue() * measurement.getMeasurement().getCurrent().getValue()))
                    .reduce(0D, Double::sum);
            if (power > biggestPower) {
                mostPowerful = circuit;
                biggestPower = power;
            }
        }
        String powerWithUnits = PowerConverter.of(biggestPower);
        CircuitStatisticsDto dto = new CircuitStatisticsDto(mostPowerful, powerWithUnits, String.format("%.2f", biggestPower / 1000 * system.getPrice()));
        return Optional.of(dto);
    }

    public abstract Map<String, Double> getPowerByTime();

    protected List<Circuit> getCircuits() {
        return systemService.getCircuits(system.getId());
    }

    protected List<MeasurementRecord> getAllMeasurementsOfPeriod() {
        List<MeasurementRecord> measurements = new LinkedList<>();
        getCircuits().forEach(circuit -> {
            List<MeasurementRecord> circuitMeasurements = circuitService.getMeasurementsOfLastDays(getDaysCountStatisticsShows(), circuit);
            measurements.addAll(circuitMeasurements);
        });
        return measurements;
    }

    protected Double getPowerOfPeriod() {
        return getAllMeasurementsOfPeriod()
                .stream()
                .map(this::computeMeasurementPower)
                .reduce(Double::sum)
                .orElse(0D);
    }


    protected Double computeMeasurementPower(MeasurementRecord record) {
        return record.
                getMeasurement().
                getCurrent().getValue() * record.getMeasurement()
                .getVoltage()
                .getValue();
    }

    public SystemStatisticsDto getDto() {
        return new SystemStatisticsDto(String.format("%.2f", getCost()), getPowerByTime(), getMostPowerfulCircuit().orElse(null));
    }


}
