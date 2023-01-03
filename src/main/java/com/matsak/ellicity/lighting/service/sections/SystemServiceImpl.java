package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.service.statistics.CurrentMonthSystemStatistics;
import com.matsak.ellicity.lighting.service.statistics.PreviousMonthSystemStatistics;
import com.matsak.ellicity.lighting.dto.Durations;
import com.matsak.ellicity.lighting.service.statistics.SystemStatistics;
import com.matsak.ellicity.lighting.service.statistics.YearSystemStatistics;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.entity.sections.UserSystems;
import com.matsak.ellicity.lighting.repository.systeminfo.SystemRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.UserSystemsRepository;
import org.apache.hc.core5.http.NotImplementedException;
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

    @Autowired
    CircuitService circuitService; //todo dependency inversion?

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
    public System getSystemById(Long systemId) {
        return systemRepository.findById(systemId)
                .orElseThrow(() -> new IllegalArgumentException("System does not exist @id: " + systemId));
    }

    @Override
    public List<System> getAllSystemsByUserId(Long userId) {
        return systemRepository.findAllSystemsByUserId(userId);
    }

    @Override
    public System getSystem(Long systemId) {
        return systemRepository.findById(systemId)
                .orElseThrow(() -> new IllegalArgumentException("System @id: " + systemId + " does not exists"));
    }

    @Override
    public boolean isUserConnected(Long userId, String systemName) {
        return userSystemsRepository.isSystemConnectedWithUser(systemName, userId);
    }

    @Override
    public boolean isUserConnected(Long userId, Long systemId) {
        return userSystemsRepository.isSystemConnectedWithUser(systemId, userId);
    }

    @Override
    public void updateSystem(System system) {
        if (system.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
        systemRepository.save(system);
    }

    @Override
    public SystemStatistics getSystemStatistics(Long systemId, Durations duration) {
        System system = getSystem(systemId);
        if (duration.equals(Durations.PREV_MONTH)) {
            return new PreviousMonthSystemStatistics(this, circuitService, system);
        }
        else if (duration.equals(Durations.PREV_YEAR)) {
            return new YearSystemStatistics(this, circuitService, system);
        }
        else if (duration.equals(Durations.CURR_MONTH)) {
            return new CurrentMonthSystemStatistics(this, circuitService, system);
        }
        throw new RuntimeException("This duration is not implemented yet");
    }

    @Override
    public Double getSystemCost(Long systemId, Durations duration) {
        System system = getSystem(systemId);
        return getSystemStatistics(systemId, duration).getCost();
    }

    @Override
    public List<Circuit> getCircuits(Long systemId) {
        return systemRepository.findCircuitsBySystemId(systemId);
    }


    private Optional<System> systemSearch(String systemName) {
        return systemRepository.findByName(systemName);
    }

    private boolean systemValidated(System system, String passKey) {
        return system.getPassKey().equals(passKey);
    }
}
