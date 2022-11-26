package com.matsak.ellicity.lighting.entity.sections;

import com.matsak.ellicity.lighting.Electric;
import com.matsak.ellicity.lighting.SmartUsage;
import com.matsak.ellicity.mqtt.MqttUtils;
import com.matsak.ellicity.mqtt.brokers.MqttBroker;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
@Entity
public class Circuit{
    @Id
    private Long id;

    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "circuit")
    private List<Device> devices;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="system_id", nullable=false)
    private System system;

    public Circuit(){}

    public Circuit(System system) {
        this.system = system;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "Circuit{" +
                "id=" + id +
                ", system=" + system +
                '}';
    }

    public Long getId() {
        return id;
    }
}
