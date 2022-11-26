package com.matsak.ellicity.lighting.service.sections;

import com.matsak.ellicity.mqtt.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceServiceImpl implements DeviceService{
    @Autowired
    MqttUtils mqtt;
    @Override
    public void turnOn() {
//        mqtt.getBroker().publish();
    }

    @Override
    public void turnOff() {
//        mqtt.getBroker().publish();
    }
}
