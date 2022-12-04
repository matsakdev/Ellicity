package com.matsak.ellicity.lighting.repository.measurements;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends MongoRepository<MeasurementRecord, String> {

}
