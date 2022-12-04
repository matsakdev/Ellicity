package com.matsak.ellicity;

import com.matsak.ellicity.lighting.config.AppProperties;
import com.matsak.ellicity.lighting.util.ArduinoEmulator;
import com.matsak.ellicity.lighting.util.MqttUtils;
import com.matsak.ellicity.mqtt.message.MessageProcessor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class EllicityApplication {
    @Autowired
    ArduinoEmulator arduinoEmulator;

    @Autowired
    MessageProcessor messageProcessor;


    public static void main(String[] args) {
        SpringApplication.run(EllicityApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startupInitialize(){
        try {
            MqttUtils.getInstance().getBroker().connect();
            MqttUtils.getInstance().getBroker().receive(data -> MqttUtils.messageHandler(data, messageProcessor));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void startupArduinoEmulator() throws Exception {
        Map<String, ArduinoEmulator.TopicType> topics = new HashMap<>();
        topics.put("1/1/2", ArduinoEmulator.TopicType.DATA_TOPIC);
        topics.put("2/2/8", ArduinoEmulator.TopicType.DEVICE_TOPIC);
        arduinoEmulator.addAllTopics(topics);
        arduinoEmulator.startEmulator();
    }

}
