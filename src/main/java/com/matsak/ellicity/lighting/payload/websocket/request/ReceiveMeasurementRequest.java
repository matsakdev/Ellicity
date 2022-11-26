package com.matsak.ellicity.lighting.payload.websocket.request;

public class ReceiveMeasurementRequest {
    private String userEmail;

    private Long circuitId;

    public Long getCircuitId() {
        return circuitId;
    }

    public void setCircuitId(Long curcuitId) {
        this.circuitId = curcuitId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
