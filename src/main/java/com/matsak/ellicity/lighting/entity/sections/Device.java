package com.matsak.ellicity.lighting.entity.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.Electric;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DEVICE")
public class Device{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="circuit_id", nullable=false)
    private Circuit circuit;

    @Column(name="state", columnDefinition = "boolean default false")
    private boolean isWorking;

    public Device() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return isWorking == device.isWorking && Objects.equals(id, device.id) && Objects.equals(name, device.name) && Objects.equals(circuit, device.circuit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, circuit, isWorking);
    }
}
