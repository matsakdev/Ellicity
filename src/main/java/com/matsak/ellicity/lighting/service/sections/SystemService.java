package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.entity.sections.UserSystems;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public interface SystemService extends Activable {
    List<System> getAllSystems();
    Measurement getLastMeasurement(System system);
    Map<Time, Measurement> getMeasurementsByTimeForLastDays(int daysAmount, System system);
    void connectUser(Long userId, String systemName, String passKey);
    List<System> getUserSystems(Long userId);
    List<UserSystems> getAllUsersSystems();
    List<UserSystems> getUserSystemsByUser(Long userId);

    System getSystemById(Long systemId);

    List<System> getAllSystemsByUserId(Long userid);

    System getSystem(Long systemId);

    boolean isUserConnected(Long userId, String systemName);

    boolean isUserConnected(Long userId, Long systemId);
}
