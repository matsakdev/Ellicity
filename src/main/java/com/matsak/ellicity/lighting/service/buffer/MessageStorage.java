package com.matsak.ellicity.lighting.service.buffer;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.exceptions.NoSuchBufferedCircuitException;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MessageStorage {

    @Autowired
    private static CircuitService circuitService;
    private static Map<Circuit, CircuitBuffer> buffers = new HashMap<>();
    public static void cache(Circuit sender, Measurement measurement) {
        CircuitBuffer buffer = buffers.get(sender);
        if (buffer == null) {
            buffer = new CircuitBuffer(sender);
            buffers.put(sender, buffer);
        }
        buffer.addMeasurement(measurement);

        System.out.println(buffers);
    }

    public static boolean isStoring(Circuit circuit){
        return buffers.containsKey(circuit);
    }

    public static Measurement getInstantMeasurement(Circuit circuit){
        if (!isStoring(circuit))
            throw new NoSuchBufferedCircuitException("The circuit "+ circuit + "was not buffered. " +
                    "Try to check isStored() method before calling.");
        else {
            List<Measurement> measurements = buffers.get(circuit).getMeasurements();
            return measurements.get(measurements.size() - 1);
        }
    }

    public static List<Measurement> getMeasurementsAndClearBuffer(Circuit circuit) {
        CircuitBuffer buffer = Optional
                .ofNullable(buffers.get(circuit))
                .orElseThrow(
                        () -> new IllegalArgumentException
                                ("Circuit @id: " + circuit.getId() + " wasn't buffered")
                );
        List<Measurement> measurements = List.copyOf(buffer.getMeasurements());
        buffer.clear();
        return measurements;
    }

    public static boolean isBufferFull(Circuit sender) {
         CircuitBuffer buffer = Optional.ofNullable(buffers.get(sender))
                 .orElseThrow(() ->
                         new IllegalArgumentException(
                                 "This circuit was not buffered @id:"
                                         + sender.getId()));
         return buffer.isFull();
    }
}
