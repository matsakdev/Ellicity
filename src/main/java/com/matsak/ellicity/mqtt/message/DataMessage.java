package com.matsak.ellicity.mqtt.message;

import com.matsak.ellicity.lighting.dto.Current;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.dto.Voltage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.*;
//todo dependency injection correction. DataMessage depends on Service
public class DataMessage implements ReceivedMessage {
    MqttMessage message;
    private LocalDateTime time;
    private Voltage voltage;
    private Current current;
    MessageProcessor processor;
    public DataMessage(MqttMessage message, MessageProcessor processor){
        this.message = message;
        this.processor = processor;
    }

    @Override
    public void process() {
        splitPayload();
        processor.processMessage(getSender(), new Measurement(time, voltage, current));
    }

    public Long getSender() {
        return topicToSenderIdConverter(message.getTopic());
    }

    private void splitPayload() {
        String[] subtopic = message.getPayload().split(";");
        double value1 = subtopic[0].transform(this::transformTextMeasurementToDoubleValue);
        double value2 = subtopic[1].transform(this::transformTextMeasurementToDoubleValue);
        String value1Label = subtopic[0].toLowerCase();
        String value2Label = subtopic[1].toLowerCase();
        time = LocalDateTime.now(); //todo get time
        if (value1Label.startsWith("v:") && value2Label.startsWith("a:")) {
            voltage = new Voltage(value1);
            current = new Current(value2);
        }
        else if (value2Label.startsWith("v:") && value1Label.startsWith("a:")) {
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

    private Long topicToSenderIdConverter(String topic) {
        String[] subtopics = topic.split("/");
        //todo dependency injection and normal logic

//        Circuit circuit = circuitService.getCircuitById(Long.parseLong(subtopics[1]));
//        try{
////            circuit = new Circuit(
////                    new System(Long.valueOf(subtopics[0]))
////            );
//            circuit = new Circuit(
//                    new System()
//            );
//        }
//        catch (NumberFormatException e) {
//            //todo log
//        }
        System.out.println("Circuit:" + subtopics[1]);
        return Long.parseLong(subtopics[1]);
    }
}
