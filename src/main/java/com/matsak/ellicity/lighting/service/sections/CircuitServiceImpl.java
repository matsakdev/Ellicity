package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.dao.sections.CircuitDao;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.repository.measurements.MeasurementsRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.CircuitRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.UserSystemsRepository;
import com.matsak.ellicity.lighting.service.buffer.MessageStorage;
import com.matsak.ellicity.lighting.util.MqttUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CircuitServiceImpl implements CircuitService {
    @Autowired
    private UserSystemsRepository userSystemsRepository;
    @Autowired
    private MeasurementsRepository measurementsRepository;
    @Autowired
    private CircuitDao circuitDAO;
    @Autowired
    private CircuitRepository circuitRepository;

    @Override
    public void saveCircuitData(Circuit sender, Measurement measurement) {
        MessageStorage.cache(sender, measurement);
        if (MessageStorage.isBufferFull(sender)) {
            saveCircuitBufferedData(sender, MessageStorage.getMeasurementsAndClearBuffer(sender));
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
    public void sendActionToDevice(Long systemId, Long circuitId, Long deviceId, Action action, Long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Circuit> getCircuitById(Long id) {
        return circuitRepository
                .findById(id);
    }

    @Override
    public void createCircuit(Circuit circuit) {
        circuitRepository.save(circuit);
    }

    @Override
    public List<Measurement> getLastMeasurements(int amount, Long circuitId) {
        Optional<Circuit> circuit = circuitRepository.findById(circuitId);
        if (circuit.isEmpty()) {
            throw new IllegalArgumentException("Circuit does not exist @id: " + circuitId);
        }
        List<Measurement> measurements = new LinkedList<>();
        fillLastMeasurements(measurements, amount, circuit.get());
        sortLastMeasurementsByTimeAsc(measurements);
        return measurements;
    }

    @Override
    public List<Measurement> getMeasurementsByDate(LocalDateTime date, Long circuitId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Circuit> getUserCircuitsBySystemId(Long userId, Long systemId) {
        List<Circuit> circuits = circuitRepository.findAllCircuitsByUserIdAndSystemId(userId, systemId);
        if (circuits.isEmpty()) {
                throw new IllegalArgumentException("Circuits does not exist for @systemId: " +
                        systemId + " and  @userId: " + userId);
        }
        return circuits;
    }

    private void fillLastMeasurements(List<Measurement> measurements, int amount, Circuit circuit) {
        if (MessageStorage.isStoring(circuit) && !MessageStorage.getBufferedMeasurements(circuit).isEmpty()) {
            measurements.add(MessageStorage.getInstantMeasurement(circuit));
        }
        int storedInDatabase = amount - measurements.size();
        measurements.addAll(getLastMeasurementsFromDatabase(storedInDatabase, circuit));
    }

    private List<? extends Measurement> getLastMeasurementsFromDatabase(int amount, Circuit circuit) {
        return measurementsRepository
                .findLastMeasurements(circuit.getId(), amount)
                .stream()
                .map(MeasurementRecord::getMeasurement).toList();
    }

    private void sortLastMeasurementsByTimeAsc(List<Measurement> measurements) {
        measurements.sort(Comparator.comparing(Measurement::getTime));
    }

    @Override
    public Measurement getLastMeasurement(Circuit circuit) {
        Measurement measurement;
        if (MessageStorage.isStoring(circuit)) {
            measurement = MessageStorage.getInstantMeasurement(circuit);
        } else {
            measurement = circuitDAO.getLastMeasurement(circuit);
        }
        return measurement;
    }

    @Override
    public Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, Circuit circuit) {
        return null;
    }
}
