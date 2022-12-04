package com.matsak.ellicity.lighting.controller.websocket;


import com.matsak.ellicity.lighting.payload.websocket.request.ReceiveMeasurementRequest;
import com.matsak.ellicity.lighting.payload.websocket.response.SendCurrentMeasurement;
import com.matsak.ellicity.lighting.repository.measurements.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.text.SimpleDateFormat;

public class MeasurementsController {

    @Autowired
    MeasurementsRepository measurementsRepository;

    @MessageMapping("/measurements")
    @SendTo("/topic/messages")
    public SendCurrentMeasurement send(ReceiveMeasurementRequest message) throws Exception {
        return new SendCurrentMeasurement(null, null, null);
//        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

}
