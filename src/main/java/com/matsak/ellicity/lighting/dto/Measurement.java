package com.matsak.ellicity.lighting.dto;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.*;

import java.time.*;

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
}
