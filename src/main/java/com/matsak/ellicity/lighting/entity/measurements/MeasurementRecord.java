package com.matsak.ellicity.lighting.entity.measurements;

import com.matsak.ellicity.lighting.dto.Measurement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "measurements")
public class MeasurementRecord {
    @Id
    String id;
    Long systemId;
    Long circuitId;
    Measurement measurement;

    public MeasurementRecord(Long systemId, Long circuitId, Measurement measurement) {
        this.systemId = systemId;
        this.circuitId = circuitId;
        this.measurement = measurement;
    }

    public String getId() {
        return id;
    }

    public Long getCircuitId() {
        return circuitId;
    }

    public void setCircuitId(Long circuitId) {
        this.circuitId = circuitId;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeasurementRecord)) return false;
        MeasurementRecord that = (MeasurementRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(systemId, that.systemId) && Objects.equals(circuitId, that.circuitId) && Objects.equals(measurement, that.measurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, systemId, circuitId, measurement);
    }

    @Override
    public String toString() {
        return "MeasurementRecord{" +
                "id='" + id + '\'' +
                ", systemId=" + systemId +
                ", circuitId=" + circuitId +
                ", measurement=" + measurement +
                '}';
    }
}
