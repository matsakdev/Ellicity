package com.matsak.ellicity.lighting.service.measurements;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.repository.measurements.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementsServiceImpl implements MeasurementsService {

    @Autowired
    MeasurementsRepository measurementsRepository;

    @Override
    public Optional<MeasurementRecord> getById(String id) {
        return measurementsRepository.findById(id);
    }
}
