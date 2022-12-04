package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.repository.measurements.*;
import com.matsak.ellicity.lighting.repository.systeminfo.CircuitRepository;
import com.matsak.ellicity.lighting.service.buffer.CircuitBuffer;
import com.matsak.ellicity.lighting.service.buffer.MessageStorage;
import com.matsak.ellicity.lighting.service.measurements.MeasurementsService;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/measurements")
public class MeasurementsController {

    @Autowired
    MeasurementsService measurementsService;

    @PostMapping
    public Measurement createMeasurement(@RequestBody Measurement measurement) {
//        circuitService.saveCircuitBufferedData();
        return measurement;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementRecord> getRecord(@PathVariable String id) {
        return ResponseEntity.of(measurementsService.getById(id));
    }
}
