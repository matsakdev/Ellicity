package com.matsak.ellicity.lighting.util;

import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.mqtt.brokers.MqttBroker;
import com.matsak.ellicity.mqtt.message.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public final class ArduinoEmulator {
    @Autowired
    MqttBroker mqttBroker;

    @Autowired
    CircuitService circuitService;

    @Autowired
    MessageProcessor messageProcessor;

    private static long pushedCount = 0;

    private LocalDate date = LocalDate.of(2022, 11, 9);

    private CompletableFuture<Void> changeDate = CompletableFuture.runAsync(() -> {
        try {
            while (true) {
                Thread.sleep(15000);
                long daysToAdd = Math.round(Math.random() * 2 + 1);
                date = date.plusDays(daysToAdd);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    });

    private Map<String, TopicType> topics = new HashMap<>();

    private boolean isWorking = false;

    public ArduinoEmulator() {
    }

    public ArduinoEmulator(Map<String, TopicType> topics) {
        try {
            this.topics = topics;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startEmulator() throws Exception {
        mqttBroker.connect();
        subscribeTopics();
        isWorking = true;
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(this::sendMessages);
        completableFuture.get();
    }

//    private void createCircuitsIfNeeds() {
//        topics.forEach((topic, type) -> {
//            //todo validate topics
//            Long circuitId = (Long.parseLong(topic.split("/")[1]));
//            if ( Optional.of(circuitService.getCircuitById(circuitId)).isEmpty() {
//                circuitService.createCircuit(new Circuit("testing id " +));
//            }
//
//
//        });
//    }

    private void sendMessages() {
        long counter = 0;
        while (true) {
            try {
                topics.forEach((topic, type) -> {
                    processSending(topic, type);
                    if (!isWorking) return;
                });
                counter += topics.size();
                System.out.println("count added: " + counter);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processSending(String topic, TopicType type) {
        String message = "";

        if (type.equals(TopicType.DATA_TOPIC)) {
            String date = getDate();
            message = "v:" + (Math.random() * 10 + 215) + ";a:" + (Math.random() * 5 + 9 + ";t:" + date + "-" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss")));
            mqttBroker.publish(topic, message);
            pushedCount++;
            System.out.println(pushedCount);
        } else if (type.equals(TopicType.DEVICE_TOPIC)) {
            sendMessageToDevice(topic, Action.values()[(int) Math.floor(Math.random() * Action.values().length)]);
        } else {
            throw new IllegalArgumentException("TYPE DOES NOT EXIST " + type);
        }
    }

    private String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }


    private void sendMessageToDevice(String topic, Action actionType) {
        mqttBroker.publish(topic, actionType.toString());
    }

    public void stop() {
        isWorking = false;
    }

    public void addTopic(String topic, TopicType type) {
        topics.put(topic, type);
    }

    public void addAllTopics(Map<String, TopicType> topics) {
        topics.forEach(this::addTopic);
    }

    public void subscribeTopics() {
        topics.forEach((topic, type) -> mqttBroker.subscribe(topic));
        mqttBroker.receive(data -> MqttUtils.messageHandler(data, messageProcessor));
    }

    public enum TopicType {
        DATA_TOPIC, DEVICE_TOPIC
    }


}

