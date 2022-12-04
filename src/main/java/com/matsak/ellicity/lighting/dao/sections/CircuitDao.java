package com.matsak.ellicity.lighting.dao.sections;

import com.matsak.ellicity.lighting.dao.EllicityDao;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CircuitDao extends EllicityDao<Circuit> {
    void saveBufferedCircuitData(Circuit sender, List<Measurement> measurements);

    Measurement getLastMeasurement(Circuit circuit);
}
