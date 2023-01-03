package com.matsak.ellicity.mqtt.message;

import com.matsak.ellicity.lighting.dto.Current;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.dto.Voltage;

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
        time = subtopic[2].transform(this::transformTextMeasurementToTimeValue);
        String value1Label = subtopic[0].toLowerCase();
        String value2Label = subtopic[1].toLowerCase();
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

    private LocalDateTime transformTextMeasurementToTimeValue(String measurement) {
        try {
            String[] parts = measurement.substring(2).trim().split("-");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            int hour = Integer.parseInt(parts[3]);
            int minute = Integer.parseInt(parts[4]);
            int second = Integer.parseInt(parts[5]);
            LocalDate localDate = LocalDate.of(year, month, day);
            LocalTime localTime = LocalTime.of(hour, minute, second);
            return LocalDateTime.of(localDate, localTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Date or Time has incorrect format in string " + measurement);
        }
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

    @Override
    public String toString() {
        return "DataMessage{" +
                "message=" + message +
                ", time=" + time +
                ", voltage=" + voltage +
                ", current=" + current +
                ", processor=" + processor +
                '}';
    }
}
