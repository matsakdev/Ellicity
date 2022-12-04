package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.payload.GetLastMeasurementsRequest;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/{system}/{circuit}")
public class CircuitController {

    @Autowired
    CircuitService circuitService;

    @GetMapping("/measurements")
    public ResponseEntity<List<Measurement>> getLastMeasurements(
            @PathVariable(name = "circuit") Long circuitId,
            @RequestBody GetLastMeasurementsRequest request) {
        //todo security
        List<Measurement> measurements = circuitService.getLastMeasurements(request.getAmount(), circuitId);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }
}
