package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.dao.sections.CircuitDao;
import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.service.buffer.MessageStorage;
import com.matsak.ellicity.mqtt.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.List;
import java.util.Map;

@Service
public class CircuitServiceImpl implements CircuitService{
    @Autowired
    private CircuitDao circuitDAO;
    @Autowired
    private MqttUtils mqtt;

    @Override
    public void turnOn() {
        mqtt.getBroker().publish("topic", "on light");
    }

    @Override
    public void turnOff() {
        mqtt.getBroker().publish("topic", "off light");
    }
    @Override
    public void saveCircuitData(Circuit sender, Measurement measurement) {
        MessageStorage.cache(sender, measurement);
    }

    @Transactional
    @Override
    public void saveCircuitBufferedData(Circuit sender, List<Measurement> measurements) {
            circuitDAO.saveBufferedCircuitData(sender, measurements);
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
