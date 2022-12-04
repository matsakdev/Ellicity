package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.payload.DeviceActionRequest;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CircuitService extends Activable {

    Measurement getLastMeasurement(Circuit circuit);
    Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, Circuit circuit);
    void saveCircuitData(Circuit sender, Measurement measurement);
    void sendActionToDevice(Long systemId, Long circuitId, Long deviceId, Action action, Long userId);
    Optional<Circuit> getCircuitById(Long id);
    void createCircuit(Circuit circuit);
}
