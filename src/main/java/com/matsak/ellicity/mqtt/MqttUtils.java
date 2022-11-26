package com.matsak.ellicity.mqtt;

import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.mqtt.brokers.HiveMQ;
import com.matsak.ellicity.mqtt.brokers.MqttBroker;
import com.matsak.ellicity.mqtt.message.StatusMessage;
import com.matsak.ellicity.mqtt.message.DataMessage;
import com.matsak.ellicity.mqtt.message.ReceivedMessage;
import com.matsak.ellicity.mqtt.message.MqttMessage;
import com.matsak.ellicity.mqtt.settings.MqttSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttUtils {
    @Autowired
    private static CircuitService circuitService;
    private static MqttUtils instance;
    private MqttBroker broker;
    private MqttSettings settings = new MqttSettings("063852c6c19b4248a7e6c9db172d50ff.s1.eu.hivemq.cloud",
            8883,
            "stanisuck",
            "stas0000"); //todo safety

    private MqttUtils(){}

    public static MqttUtils getInstance(){
        if (instance == null) {
            instance = new MqttUtils();
            return instance;
        }
        return instance;
    }

    public MqttBroker getBroker(){
        if (broker == null) {
            broker = new HiveMQ(settings); //todo Dependency Injection
            return broker;
        }
        else return broker;
    }

    public static void messageHandler(MqttMessage message){
        ReceivedMessage messageWithDefinedType = processMessage(message);
        messageWithDefinedType.process();
    }

    private static ReceivedMessage processMessage(MqttMessage message) {
        if (message.getTopic() == "1") return new DataMessage(message);
        if (message.getTopic() == "2") return new StatusMessage(message);
        else throw new IllegalArgumentException("Message doesn't follow the protocol: " + message.getPayload());
    }
}
