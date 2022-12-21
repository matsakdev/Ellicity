package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.payload.ApiResponse;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.lighting.service.sections.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/circuits")
public class CircuitController {

    @Autowired
    CircuitService circuitService;
    
    @Autowired
    DeviceService deviceService;

    @GetMapping("/{circuitId}/measurements/previous/{amount}")
    public ResponseEntity<List<Measurement>> getLastMeasurements(
            @PathVariable(name = "circuitId") Long circuitId,
            @PathVariable(name = "amount") Integer amount) {
        //todo security
        List<Measurement> measurements = circuitService.getLastMeasurements(amount, circuitId);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PostMapping("/{circuitId}/{action}")
    public ResponseEntity<?> processCircuitAction(
            @PathVariable Long circuitId,
            @PathVariable String action,
            @AuthenticationPrincipal UserPrincipal principal) {
        Action requestedAction = Action.valueOf(action.toUpperCase()); //todo IllegalArgumentException !!!
        Optional<Circuit> circuitOptional = circuitService.getCircuitById(circuitId);
        //todo validate if user have access to the circuit
        if (circuitOptional.isEmpty()) {
            throw new IllegalArgumentException("Circuit does not exist @id: " + circuitId);
            //todo exception handler
        }
        circuitOptional.get().getDevices().forEach(device -> deviceService.sendActionToDevice(device, requestedAction));
        return ResponseEntity.ok(new ApiResponse(true, action.toUpperCase()));
    }
}
