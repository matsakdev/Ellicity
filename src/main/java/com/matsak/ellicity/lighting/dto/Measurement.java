package com.matsak.ellicity.lighting.dto;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.*;

import java.time.*;
import java.util.Objects;

public class Measurement {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime time;
    private Voltage voltage;
    private Current current;
    public Measurement(LocalDateTime time, Voltage voltage, Current current) {
        this.time = time;
        this.voltage = voltage;
        this.current = current;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement)) return false;
        Measurement that = (Measurement) o;
        return Objects.equals(time, that.time) && Objects.equals(voltage, that.voltage) && Objects.equals(current, that.current);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, voltage, current);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "time=" + time +
                ", voltage=" + voltage +
                ", current=" + current +
                '}';
    }
}
