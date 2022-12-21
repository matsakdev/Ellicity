package com.matsak.ellicity.lighting.service.statistics;

import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.lighting.service.sections.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class YearSystemStatistics extends SystemStatistics {

    public YearSystemStatistics(SystemService systemService, CircuitService circuitService, System system) {
        super(systemService, circuitService, system);
    }

    @Override
    public Integer getDaysCountStatisticsShows() {
        Year year = Year.now().minusYears(1);
        return year.length();
    }

    @Override
    public Map<String, Double> getPowerByTime() {
        Map<Month, Double> powerByMonth = new HashMap<>();
        List<Circuit> circuits = systemService.getCircuits(system.getId());
        circuits.forEach(circuit -> {
            addCircuitPower(powerByMonth, circuit);
        });
        return convertDateAndPower(powerByMonth);
    }

    protected Map<String, Double> convertDateAndPower(Map<Month, Double> powerByMonth) {
        convertPowerToKwh(powerByMonth);
        return convertMonthToDate(powerByMonth);
    }

    protected Map<String, Double> convertMonthToDate(Map<Month, Double> powerByMonth) {
        Map<String, Double> powerByDate = new HashMap<>();
        powerByMonth.keySet().forEach(month -> {
            powerByDate.put(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH), powerByMonth.get(month));
        });
        return powerByDate;
    }

    protected void convertPowerToKwh(Map<Month, Double> powerByMonth) {
        powerByMonth.keySet().forEach(key -> {
            powerByMonth.put(key, powerByMonth.get(key) / 1000);
        });
    }

    private void addCircuitPower(Map<Month, Double> powerByTime, Circuit circuit) {
        List<MeasurementRecord> measurements = circuitService.getMeasurementsOfLastDays(getDaysCountStatisticsShows(), circuit);
        for (MeasurementRecord record : measurements) {
            Month key = record.getMeasurement().getTime().getMonth();
            powerByTime.put(key, powerByTime.getOrDefault(key, 0D) + computeMeasurementPower(record));
        }
    }


}
