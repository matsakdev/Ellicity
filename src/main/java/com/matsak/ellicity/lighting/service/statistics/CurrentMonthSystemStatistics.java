package com.matsak.ellicity.lighting.service.statistics;

import com.matsak.ellicity.lighting.dto.CurrentSystemStatisticsDto;
import com.matsak.ellicity.lighting.dto.SystemStatisticsDto;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.lighting.service.sections.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentMonthSystemStatistics extends SystemStatistics {

    public CurrentMonthSystemStatistics(SystemService systemService, CircuitService circuitService, System system) {
        super(systemService, circuitService, system);
    }

    @Override
    public Integer getDaysCountStatisticsShows() {
        return LocalDateTime.now().getDayOfMonth();
    }

    @Override
    public Map<String, Double> getPowerByTime() {
        Map<MonthDay, Double> powerByDay = new HashMap<>();
        List<Circuit> circuits = systemService.getCircuits(system.getId());
        circuits.forEach(circuit -> addCircuitPower(powerByDay, circuit));
        convertPowerToKwh(powerByDay);
        return convertDayToDate(powerByDay);
    }

    private void convertPowerToKwh(Map<MonthDay, Double> powerByDay) {
        powerByDay.keySet().forEach(key -> {
            powerByDay.put(key, powerByDay.get(key) / 1000);
        });
    }

    private void addCircuitPower(Map<MonthDay, Double> powerByDay, Circuit circuit) {
        List<MeasurementRecord> measurements = circuitService.getMeasurementsOfLastDays(getDaysCountStatisticsShows(), circuit);
        for (MeasurementRecord record : measurements) {
            LocalDateTime dateTime = record.getMeasurement().getTime();
            MonthDay key = MonthDay.of(dateTime.getMonth(), dateTime.getDayOfMonth());
            powerByDay.put(key, powerByDay.getOrDefault(key, 0D) + super.computeMeasurementPower(record));
        }
    }

    private Map<String, Double> convertDayToDate(Map<MonthDay, Double> powerByDay) {
        Map<String, Double> powerByDate = new HashMap<>();
        powerByDay.keySet().forEach(day -> powerByDate
                .put(day.format(DateTimeFormatter
                                .ofPattern("dd")
                        ),
                        powerByDay.get(day)
                )
        );
        return powerByDate;
    }

    @Override
    public SystemStatisticsDto getDto() {
        return new CurrentSystemStatisticsDto(String.format("%.2f", getCost()), getPowerByTime(), getMostPowerfulCircuit().orElse(null), getPredictedCost());
    }

    private Double getPredictedCost() {
        double costPerDay = getCost() / getDaysCountStatisticsShows();
        int daysInThisMonth = LocalDateTime.now().getChronology().dateNow().lengthOfMonth();
        return getCost() + (daysInThisMonth - getDaysCountStatisticsShows()) * costPerDay;
    }
}
