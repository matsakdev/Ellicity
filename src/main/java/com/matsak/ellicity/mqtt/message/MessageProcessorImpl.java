package com.matsak.ellicity.mqtt.message;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageProcessorImpl implements MessageProcessor {

    public MessageProcessorImpl() {
    }

    @Autowired
    CircuitService circuitService;

    @Override
    public void processMessage(Long circuitId, Measurement measurement) {
        Optional<Circuit> circuit = circuitService.getCircuitById(circuitId);
        if (circuit.isEmpty()) {
            throw new IllegalArgumentException("Cannot find circuit with such @id: " + circuitId);
        }
        circuitService.saveCircuitData(circuit.get(), measurement);
    }
}
