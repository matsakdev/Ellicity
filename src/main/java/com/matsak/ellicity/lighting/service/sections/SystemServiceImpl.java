package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.entity.sections.UserSystems;
import com.matsak.ellicity.lighting.repository.systeminfo.SystemRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.UserSystemsRepository;
import com.matsak.ellicity.lighting.util.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SystemServiceImpl implements SystemService{

    @Autowired
    SystemRepository systemRepository;

    @Autowired
    UserSystemsRepository userSystemsRepository;

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

    @Override
    public void connectUser(Long userId, String systemName, String passKey) {
        Optional<System> systemOptional = systemSearch(systemName);
        if (systemOptional.isPresent() && systemValidated(systemOptional.get(), passKey)) {
            userSystemsRepository.save(new UserSystems(userId, systemOptional.get()));
        }
    }

    @Override
    public List<System> getUserSystems(Long id) {
        return userSystemsRepository.findSystemsByUser(id);
    }

    @Override
    public List<UserSystems> getAllUsersSystems() {
        return userSystemsRepository.findAll();
    }

    @Override
    public List<UserSystems> getUserSystemsByUser(Long userId) {
        return userSystemsRepository.findUserSystemsByUser(userId);
    }

    @Override
    public System getSystemById(Long systemId, Long userId) {
        return systemRepository.findByUserId(systemId, userId)
                .orElseThrow(() -> new IllegalArgumentException("System @id: " + systemId + " does not connected with @userId: " + userId));
    }


    private Optional<System> systemSearch(String systemName) {
        return systemRepository.findByName(systemName);
    }

    private boolean systemValidated(System system, String passKey) {
        return system.getPassKey().equals(passKey);
    }
}
