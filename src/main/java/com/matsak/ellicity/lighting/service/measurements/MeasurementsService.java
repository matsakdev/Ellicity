package com.matsak.ellicity.lighting.service.measurements;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;

import java.util.Optional;


public interface MeasurementsService {
    Optional<MeasurementRecord> getById(String id);
}
