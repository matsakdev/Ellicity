package com.matsak.ellicity.lighting.dao.sections;

import com.matsak.ellicity.lighting.dao.EllicityDao;
import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircuitDao extends EllicityDao<Circuit, Long> {
    void saveBufferedCircuitData(Circuit sender, List<Measurement> measurements);

    Measurement getLastMeasurement(Circuit circuit);
}
