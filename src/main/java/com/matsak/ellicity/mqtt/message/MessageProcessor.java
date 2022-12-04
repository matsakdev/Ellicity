package com.matsak.ellicity.mqtt.message;


import com.matsak.ellicity.lighting.dto.Measurement;

public interface MessageProcessor {
    void processMessage(Long circuitId, Measurement measurement);
}
