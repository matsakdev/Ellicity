package com.matsak.ellicity.lighting;

import com.matsak.ellicity.lighting.dto.Measurement;

import java.sql.Time;
import java.util.Map;

public interface ElectricalUnit {
    Measurement getInstantMeasurement(Electric unit);
    Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, Electric unit);
}
