package com.matsak.ellicity.lighting.service.buffer;

import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CircuitBuffer {
    private int bufferSize;
    private Circuit circuit;
    private List<Measurement> measurements;

    public Circuit getCircuit() {
        return circuit;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public CircuitBuffer(int bufferSize, Circuit circuit) {
        this.bufferSize = bufferSize;
        this.circuit = circuit;
    }

    public void addMeasurement(Measurement measurement){
        if (isFull()) throw new IllegalStateException("Buffer of measurements is full and cannot accommodate more elements.");
        measurements.add(measurement);
    }

    public boolean isFull(){
        return (measurements.size() >= bufferSize);
    }

    public void clearBuffer(){
        measurements.clear();
    }
}
