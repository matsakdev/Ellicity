package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.Electric;
import com.matsak.ellicity.lighting.ElectricalUnit;
import com.matsak.ellicity.lighting.entity.measurements.Current;
import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.measurements.Voltage;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@Service
public interface CircuitService extends Activable {

    Measurement getLastMeasurement(Circuit circuit);
    Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, Circuit circuit);
    void saveCircuitData(Circuit sender, Measurement measurement);
    void saveCircuitBufferedData(Circuit sender, List<Measurement> measurements);
}
