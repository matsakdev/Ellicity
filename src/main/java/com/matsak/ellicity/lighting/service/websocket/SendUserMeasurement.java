package com.matsak.ellicity.lighting.service.websocket;

import com.matsak.ellicity.lighting.payload.websocket.response.SendCurrentMeasurement;

public interface SendUserMeasurement {
    SendCurrentMeasurement sendCurrentMeasurementByEmail(String userEmail, Long circuitId);
}
