package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.dao.sections.CircuitDao;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.payload.DeviceActionRequest;
import com.matsak.ellicity.lighting.repository.measurements.MeasurementsRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.CircuitRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.DeviceRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.UserSystemsRepository;
import com.matsak.ellicity.lighting.service.buffer.MessageStorage;
import com.matsak.ellicity.lighting.util.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CircuitServiceImpl implements CircuitService{

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserSystemsRepository userSystemsRepository;
    @Autowired
    private MeasurementsRepository measurementsRepository;
    @Autowired
    private CircuitDao circuitDAO;
    @Autowired
    private CircuitRepository circuitRepository;
    @Override
    public void turnOn() {
        MqttUtils.getInstance().getBroker().publish("topic", "on light");
    }

    @Override
    public void turnOff() {
        MqttUtils.getInstance().getBroker().publish("topic", "off light");
    }
    @Override
    public void saveCircuitData(Circuit sender, Measurement measurement) {
        MessageStorage.cache(sender, measurement);
        if (MessageStorage.isBufferFull(sender)) {
            saveCircuitBufferedData(sender, MessageStorage.getMeasurementsAndClearBuffer(sender));
        }
    }

    @Override
    public void sendActionToDevice(Long systemId, Long circuitId, Long deviceId, Action action, Long userId) {
        if (userSystemsRepository
                .findByUserId(userId)
                .stream()
                .map(System::getId)
                .anyMatch(x -> x.equals(systemId))) {
            //todo security
            String topic = systemId + "/" + circuitId + "/" + deviceId + "/";
            MqttUtils.getInstance().getBroker().publish(topic, action.toString());
        }
    }

    private void saveCircuitBufferedData(Circuit sender, List<Measurement> measurements) {
//            circuitDAO.saveBufferedCircuitData(sender, measurements);
        measurements
                .stream()
                .map(measurement ->
                        new MeasurementRecord(
                                sender.getSystem().getId(), sender.getId(),
                                measurement)
                )
                .forEach(msrmnt -> measurementsRepository.save(msrmnt));
        java.lang.System.out.println("SAVED");
    }

    @Override
    public Optional<Circuit> getCircuitById(Long id) {
        return circuitRepository
                .findById(id);
//                .orElseThrow(() -> new
//                        IllegalArgumentException("Cannot find circuit with such @id: " + id));
    }

    @Override
    public void createCircuit(Circuit circuit) {
        circuitRepository.save(circuit);
    }

    @Override
    public Measurement getLastMeasurement(Circuit circuit) {
        Measurement measurement;
        if (MessageStorage.isStoring(circuit)){
            measurement = MessageStorage.getInstantMeasurement(circuit);
        }
        else {
            measurement = circuitDAO.getLastMeasurement(circuit);
        }
        return measurement;
    }

    @Override
    public Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, Circuit circuit) {
        return null;
    }
}
