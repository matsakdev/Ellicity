package com.matsak.ellicity.mqtt.settings;

import com.matsak.ellicity.mqtt.message.MqttMessage;

import java.util.function.Consumer;

public class MqttSettings {
    private String host;
    private int port;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public MqttSettings(String host, int port,
                        String username, String password){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
