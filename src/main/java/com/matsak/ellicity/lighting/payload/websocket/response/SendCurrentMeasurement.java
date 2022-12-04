package com.matsak.ellicity.lighting.payload.websocket.response;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;

public class SendCurrentMeasurement {
    private System system;
    private Circuit circuit;
    private Measurement measurement;

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public SendCurrentMeasurement(System system, Circuit circuit, Measurement measurement) {
        this.system = system;
        this.circuit = circuit;
        this.measurement = measurement;
    }
}
