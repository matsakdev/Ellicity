package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.dao.sections.SystemDao;
import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.repository.SystemRepository;
import com.matsak.ellicity.mqtt.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@Service
public class SystemServiceImpl implements SystemService{

    @Autowired
    MqttUtils mqtt;

    @Autowired
    SystemRepository systemRepository;


    @Override
    public void turnOn() {
//        mqtt.getBroker().publish();
    }

    @Override
    public void turnOff() {
//        mqtt.getBroker().publish();
    }

    @Override
    public Measurement getLastMeasurement(System system) {
        return null;
    }

    @Override
    public Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, System system) {
        return null;
    }

    @Override
    public List<System> getAllSystems() {
        return null;
    }
}
