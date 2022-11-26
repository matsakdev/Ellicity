package com.matsak.ellicity.mqtt.message;

import com.matsak.ellicity.lighting.entity.measurements.Current;
import com.matsak.ellicity.lighting.entity.measurements.Measurement;
import com.matsak.ellicity.lighting.entity.measurements.Voltage;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.time.LocalTime;

public class DataMessage implements ReceivedMessage {
    MqttMessage message;
    private Time time;
    private Voltage voltage;
    private Current current;

    @Autowired
    CircuitService circuitService;

    public DataMessage(MqttMessage message) {
        this.message = message;
    }

    @Override
    public void process() {
        splitPayload();

        circuitService.saveCircuitData(getSender(), new Measurement(Time.valueOf(LocalTime.now()), voltage, current));
    }

    public Circuit getSender() {
        return topicToSenderConverter(message.getTopic());
    }

    private void splitPayload() {
        String[] subtopic = message.getPayload().split(";");
        double value1 = subtopic[0].transform(this::transformTextMeasurementToDoubleValue);
        double value2 = subtopic[1].transform(this::transformTextMeasurementToDoubleValue);
        String value1Label = subtopic[0].toLowerCase();
        String value2Label = subtopic[1].toLowerCase();
        if (value1Label.startsWith("v:") && value2Label.startsWith("a:")){
            voltage = new Voltage(value1);
            current = new Current(value2);
        }
        else if (value2Label.startsWith("v:") && value1Label.startsWith("a:")){
            voltage = new Voltage(value2);
            current = new Current(value1);
        }
        else throw new RuntimeException("Illegal format of message");
    }

    private double transformTextMeasurementToDoubleValue(String measurement){
        try{
           return Double.parseDouble(measurement.substring(2).trim());
        }
        catch(NumberFormatException e) {
            throw new RuntimeException("CANNOT PARSE STRING TO DOUBLE");
        }
    }

    private Circuit topicToSenderConverter(String topic) {
        String[] subtopics = topic.split("/");
        Circuit circuit = null;
        try{
            circuit = new Circuit(
                    new System(Long.valueOf(subtopics[0]))
            );
        }
        catch (NumberFormatException e) {
            //todo log
        }
        return circuit;
    }
}
