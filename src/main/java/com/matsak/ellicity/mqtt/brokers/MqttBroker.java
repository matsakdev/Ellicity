package com.matsak.ellicity.mqtt.brokers;

import com.matsak.ellicity.mqtt.message.MqttMessage;

import java.util.function.Consumer;

public interface MqttBroker {
    void connect() throws Exception;
    void disconnect();
    void subscribe(String topic);
    void receive(Consumer<MqttMessage> messageHandler);
    void publish(String topic, String payload);
}
