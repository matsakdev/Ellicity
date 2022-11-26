package com.matsak.ellicity.lighting.entity.measurements;

import java.sql.Time;

public class Measurement {

    Time time;
    Voltage voltage;

    Current current;
    public Measurement(Time time, Voltage voltage, Current current) {
        this.time = time;
        this.voltage = voltage;
        this.current = current;
    }
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public void setVoltage(Voltage voltage) {
        this.voltage = voltage;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

}
