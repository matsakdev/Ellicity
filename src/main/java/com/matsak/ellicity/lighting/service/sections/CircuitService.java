package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.dto.Durations;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.Device;
import com.matsak.ellicity.lighting.payload.DeviceActionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CircuitService{

    Measurement getLastMeasurement(Circuit circuit);
    List<MeasurementRecord> getMeasurementsOfLastDays(int daysAmount, Circuit circuit);
    List<MeasurementRecord> getMeasurementsInDateRange(Circuit circuit, LocalDateTime dateFrom, LocalDateTime dateTo);
    void saveCircuitData(Circuit sender, Measurement measurement);
    void sendActionToDevice(Long systemId, Long circuitId, Long deviceId, Action action, Long userId);
    Optional<Circuit> getCircuitById(Long id);
    void createCircuit(Circuit circuit);
    List<Measurement> getLastMeasurements(int amount, Long circuitId);
    List<Measurement> getMeasurementsByDate(LocalDateTime date, Long circuitId);

    List<Circuit> getUserCircuitsBySystemId(Long userId, Long systemId);

    List<Device> getDevicesStates(Long circuitId);
}
