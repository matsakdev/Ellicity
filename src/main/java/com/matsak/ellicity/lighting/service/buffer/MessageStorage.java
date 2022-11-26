package com.matsak.ellicity.lighting.service.buffer;

import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.exceptions.NoSuchBufferedCircuitException;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageStorage {
    @Value("${buffer.size}")
    private static int bufferSize;

    @Autowired
    private static CircuitService circuitService;
    private static Map<Circuit, CircuitBuffer> buffers;
    public static void cache(Circuit sender, Measurement measurement) {
        CircuitBuffer buffer = buffers.get(sender);
        if (buffer == null) {
            buffer = new CircuitBuffer(bufferSize, sender);
            buffers.put(sender, buffer);
        }
        buffer.addMeasurement(measurement);
        if (buffer.isFull()) saveBufferedData(sender, buffer.getMeasurements());
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

    private static void saveBufferedData(Circuit sender, List<Measurement> measurements){
        circuitService.saveCircuitBufferedData(sender, measurements);
        buffers.get(sender).clearBuffer();
    }
}
