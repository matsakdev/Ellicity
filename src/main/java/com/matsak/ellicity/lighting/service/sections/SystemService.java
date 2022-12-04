package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.System;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public interface SystemService extends Activable {
    List<System> getAllSystems();
    Measurement getLastMeasurement(System system);
    Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, System system);
    void connectUser(Long userId, String systemName, String passKey);
}