package com.matsak.ellicity.lighting.util;

import com.matsak.ellicity.mqtt.brokers.HiveMQ;
import com.matsak.ellicity.mqtt.brokers.MqttBroker;
import com.matsak.ellicity.mqtt.message.MessageProcessor;
import com.matsak.ellicity.mqtt.message.StatusMessage;
import com.matsak.ellicity.mqtt.message.DataMessage;
import com.matsak.ellicity.mqtt.message.ReceivedMessage;
import com.matsak.ellicity.mqtt.message.MqttMessage;
import com.matsak.ellicity.lighting.config.MqttSettings;

public class MqttUtils {
    private static MqttUtils instance;
    private MqttBroker broker;
    private MqttSettings settings = new MqttSettings("063852c6c19b4248a7e6c9db172d50ff.s1.eu.hivemq.cloud",
            8883,
            "stanisuck",
            "stas0000"); //todo safety
    private static long count = 0;

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

    public static void messageHandler(MqttMessage message, MessageProcessor processor){
        ReceivedMessage messageWithDefinedType = processMessage(message, processor);

        System.out.println("RECEIVED TOPIC:" + message.getTopic() + "\n PAYLOAD: " + message.getPayload());
        count++;
        if (count % 10 == 0) System.out.println("received count: " + count);
        if (message.getTopic().equals("1/1")) System.out.println("          1/1 RECEIVED THE MESSAGE " + message.getPayload());
        messageWithDefinedType.process();
    }

    private static ReceivedMessage processMessage(MqttMessage message, MessageProcessor processor) {
        //todo logic of creating messages
        if (message.getPayload().startsWith("v:")) return new DataMessage(message, processor);
        else if (message.getPayload().startsWith("O")) return new StatusMessage(message);
        else throw new IllegalArgumentException("Message doesn't follow the protocol: " + message.getPayload());
    }
}
