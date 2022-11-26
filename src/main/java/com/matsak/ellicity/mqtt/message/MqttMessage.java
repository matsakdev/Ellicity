package com.matsak.ellicity.mqtt.message;

import java.util.List;

public class MqttMessage {
    private MqttMessage message;
    private final String topic;
    private final String payload;

    public String getPayload() {
        return payload;
    }

    public MqttMessage(String topic, String payload){
        validateMessage(topic, payload);
        this.topic = topic;
        this.payload = payload;

    }

    private void validateMessage(String topic, String payload) {
        //todo validate message
    }

    public String getTopic() {
        return topic;
    }

//    public String getPayload() {
//        return payload;
//    }
}
