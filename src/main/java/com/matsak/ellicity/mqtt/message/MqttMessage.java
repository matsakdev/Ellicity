package com.matsak.ellicity.mqtt.message;

public class MqttMessage {
    private final String topic;
    private final String payload;

    public MqttMessage(String topic, String payload){
        validateMessage(topic, payload);
        this.topic = topic;
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    public String getPayload() {
        return payload;
    }


    private void validateMessage(String topic, String payload) {
        //todo validate message
    }
}
