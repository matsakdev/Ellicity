package com.matsak.ellicity;

import com.matsak.ellicity.lighting.config.AppProperties;
import com.matsak.ellicity.mqtt.MqttUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class EllicityApplication {
    MqttUtils mqttUtils = MqttUtils.getInstance();


    public static void main(String[] args) {
        SpringApplication.run(EllicityApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startupInitialize(){
        try {
            mqttUtils.getBroker().connect();
            mqttUtils.getBroker().receive(MqttUtils::messageHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
