package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.sections.Device;
import com.matsak.ellicity.lighting.security.UserPrincipal;

public interface DeviceService{

    Device getValidatedDevice(Long systemId, Long circuitId, Long deviceId, UserPrincipal principal);

    void sendActionToDevice(Device device, Action requestedAction);
}
