package com.matsak.ellicity.lighting.controller.websocket;


import com.matsak.ellicity.lighting.payload.websocket.request.ReceiveMeasurementRequest;
import com.matsak.ellicity.lighting.payload.websocket.response.SendCurrentMeasurement;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;

public class MeasurementsController {

    @MessageMapping("/measurements")
    @SendTo("/topic/messages")
    public SendCurrentMeasurement send(ReceiveMeasurementRequest message) throws Exception {
        return new
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

}
