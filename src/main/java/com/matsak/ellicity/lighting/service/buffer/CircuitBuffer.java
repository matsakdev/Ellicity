package com.matsak.ellicity.lighting.service.buffer;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CircuitBuffer {
    
    @Value("${buffer.size}")
    private static final int BUFFER_DEFAULT_SIZE = 2;
    
    private int bufferSize;
    private Circuit circuit;
    private List<Measurement> measurements;

    public Circuit getCircuit() {
        return circuit;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public CircuitBuffer(Circuit circuit, int bufferSize) {
        this.bufferSize = bufferSize;
        this.circuit = circuit;
        this.measurements = new ArrayList<>();
    }

    public CircuitBuffer(Circuit circuit) {
        this.bufferSize = BUFFER_DEFAULT_SIZE;
        this.circuit = circuit;
        this.measurements = new ArrayList<>();
    }

    public void addMeasurement(Measurement measurement){
        if (isFull()) throw new IllegalStateException("Buffer of measurements is full and cannot accommodate more elements.");
        measurements.add(measurement);
    }

    public boolean isFull(){
        System.out.println("BUFFER IS " + measurements.size());
        return (measurements.size() >= bufferSize);
    }

    public void clear(){
        measurements.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CircuitBuffer)) return false;
        CircuitBuffer buffer = (CircuitBuffer) o;
        return bufferSize == buffer.bufferSize && Objects.equals(circuit, buffer.circuit) && Objects.equals(measurements, buffer.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bufferSize, circuit, measurements);
    }

    @Override
    public String toString() {
        return "CircuitBuffer{" +
                "bufferSize=" + bufferSize +
                ", circuit=" + circuit +
                ", measurements=" + measurements +
                '}';
    }
}
