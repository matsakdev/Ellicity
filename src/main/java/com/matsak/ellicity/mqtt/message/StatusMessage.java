package com.matsak.ellicity.mqtt.message;

public class StatusMessage implements ReceivedMessage {
    MqttMessage message;
    public StatusMessage(MqttMessage message) {
        this.message = message;
    }

    @Override
    public void process() {

    }
}
