package com.matsak.ellicity.lighting.entity.sections;

import com.matsak.ellicity.lighting.Activable;
import com.matsak.ellicity.lighting.Electric;
import com.matsak.ellicity.lighting.SmartUsage;
import com.matsak.ellicity.lighting.entity.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "[System]")
public class System implements Electric{
    @Id
    @Column(name="id")
    private Long id;
    @NotNull
    @Column(name="name")
    private String name;
    @NotNull
    @Column(name="passKey")
    private String passKey;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @NotNull
    @ManyToMany
    @JoinTable(name="Users_Systems",
            joinColumns = @JoinColumn(name="system_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> users;
    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    private List<Circuit> electricalCircuits;


    public System(){}

    public System(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String password) {
        this.passKey = password;
    }

    public List<Circuit> getElectricalCircuits() {
        return electricalCircuits;
    }

    public void setElectricalCircuits(List<Circuit> electricalCircuits) {
        this.electricalCircuits = electricalCircuits;
    }

//    @Override
//    public double getInstantVoltage() {
//        return electricalCircuits.stream()
//                .map(Circuit::getInstantVoltage)
//                .reduce((unitVoltage, sum) -> sum += unitVoltage)
//                .orElse((double) 0);
//    }
//
//    @Override
//    public double getInstantCurrent() {
//        return electricalCircuits.stream()
//                .map(Circuit::getInstantCurrent)
//                .reduce((unitCurrent, sum) -> sum += unitCurrent)
//                .orElse((double) 0);
//    }
//
//    @Override
//    public double getInstantResistance() {
//        return electricalCircuits.stream()
//                .map(Circuit::getInstantResistance)
//                .reduce((unitResistance, sum) -> sum += unitResistance)
//                .orElse((double) 0);
//    }
//
//    @Override
//    public double getInstantPower() {
//        return electricalCircuits.stream()
//                .map(Circuit::getInstantPower)
//                .reduce((unitPower, sum) -> sum += unitPower)
//                .orElse((double) 0);
//    }
//
//    @Override
//    public void enablePhotodetector() {
//        electricalCircuits.forEach(Circuit::enablePhotodetector);
//    }
//
//    @Override
//    public void disablePhotodetector() {
//        electricalCircuits.forEach(Circuit::disablePhotodetector);
//    }
//
//    @Override
//    public void enableMotionSensor() {
//        electricalCircuits.forEach(Circuit::enableMotionSensor);
//    }
//
//    @Override
//    public void disableMotionSensor() {
//        electricalCircuits.forEach(Circuit::disableMotionSensor);
//    }
//
//    @Override
//    public void enableLightWithKey(String key) {
//
//    }
//
//    @Override
//    public void disableLightWithKey(String key) {
//
//    }
//
//    @Override
//    public void turnOn() {
//
//    }
//
//    @Override
//    public void turnOff() {
//
//    }
}
