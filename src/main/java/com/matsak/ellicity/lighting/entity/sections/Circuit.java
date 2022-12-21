package com.matsak.ellicity.lighting.entity.sections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CIRCUIT")
public class Circuit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "circuit", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Device> devices;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="system_id", nullable=false)
    private System system;

    public Circuit(){}

    public Circuit(System system) {
        this.system = system;
    }

    public Circuit(String name, System system) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circuit)) return false;
        Circuit circuit = (Circuit) o;
        return Objects.equals(id, circuit.id) && Objects.equals(name, circuit.name) && Objects.equals(system, circuit.system);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, system);
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
