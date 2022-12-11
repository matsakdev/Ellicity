package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.sections.Device;
import com.matsak.ellicity.lighting.repository.systeminfo.CircuitRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.DeviceRepository;
import com.matsak.ellicity.lighting.repository.systeminfo.SystemRepository;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import com.matsak.ellicity.lighting.util.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService{
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    SystemRepository systemRepository;

    @Autowired
    CircuitRepository circuitRepository;

    @Override
    public Device getValidatedDevice(Long deviceId, UserPrincipal principal) throws IllegalArgumentException {
        Optional<Device> device = deviceRepository.getUserDeviceById(principal.getId(), deviceId);
        if (device.isEmpty()) {
            throw new IllegalArgumentException("Device does not exist @id: " + deviceId + " for user @id: " + principal.getId());
        }
        return device.get();
    }

    @Override
    public void sendActionToDevice(Device device, Action requestedAction) {
            String topic = device.getCircuit().getSystem().getId() + "/" + device.getCircuit().getId() + "/" + device.getId() + "/";
            publishAction(requestedAction, topic);
            updateDeviceAfterAction(device, requestedAction);
            updateDeviceDbState(device);
    }

    private void publishAction(Action action, String topic) {
        MqttUtils
                .getInstance()
                .getBroker()
                .publish(topic, action.toString());
    }

    private void updateDeviceAfterAction(Device device, Action action) {
        switch (action) {
            case ON -> device.setWorking(true);
            case OFF -> device.setWorking(false);
        }
    }

    private void updateDeviceDbState(Device device) {
        deviceRepository.save(device);
    }
}
