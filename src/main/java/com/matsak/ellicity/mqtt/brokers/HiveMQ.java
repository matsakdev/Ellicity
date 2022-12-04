package com.matsak.ellicity.mqtt.brokers;


import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.matsak.ellicity.mqtt.message.MqttMessage;
import com.matsak.ellicity.lighting.config.MqttSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;
@Component
public class HiveMQ implements MqttBroker {
    Mqtt5BlockingClient client;
    @Autowired
    private final MqttSettings settings;

    public HiveMQ(MqttSettings settings){
        this.settings = settings;
    }

    @Override
    public void connect() throws Exception {
        try{
            client = buildHostConnection(settings);
            connectToClient(client, settings);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void disconnect() {
        client.disconnect();
    }

    private Mqtt5BlockingClient buildHostConnection(MqttSettings settings) {
        return MqttClient.builder()
                .useMqttVersion5()
                .serverHost(settings.getHost())
                .serverPort(settings.getPort())
                .sslWithDefaultConfig()
                .buildBlocking();
    }

    private void connectToClient(Mqtt5BlockingClient client, MqttSettings settings) {
        client.connectWith()
                .simpleAuth()
                .username(settings.getUsername())
                .password(UTF_8.encode(settings.getPassword()))
                .applySimpleAuth()
                .send();
    }

    @Override
    public void subscribe(String topic) {
        client.subscribeWith()
                .topicFilter(topic)
                .send();
    }

    @Override
    public void receive(Consumer<MqttMessage> messageHandler) {
        client.toAsync().publishes(ALL, publish -> {
            messageHandler.accept(new MqttMessage(
                    publish.getTopic().toString(),
                    UTF_8.decode(publish.getPayload().get()).toString()));
//            System.out.println("Received message: " +
//                    publish.getTopic() + " -> " +
//                    UTF_8.decode(publish.getPayload().get()));
//        }
        });
    }

    @Override
    public void publish(String topic, String payload) {
        client.publishWith()
                .topic(topic)
                .payload(UTF_8.encode(payload))
                .send();
    }
}
