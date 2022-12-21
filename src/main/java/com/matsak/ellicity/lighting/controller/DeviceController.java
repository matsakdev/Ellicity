package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.entity.actions.Action;
import com.matsak.ellicity.lighting.entity.sections.Device;
import com.matsak.ellicity.lighting.payload.ApiResponse;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.lighting.service.sections.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    CircuitService circuitService;

    @Autowired
    DeviceService deviceService;

    @PostMapping("/{id}/{action}")
    public ResponseEntity<?> processDeviceAction(
                                             @PathVariable(name = "id") Long deviceId,
                                             @PathVariable(name = "action") String action,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        Action requestedAction = Action.valueOf(action.toUpperCase()); //todo IllegalArgumentException !!!
        Device device = deviceService.getValidatedDevice(deviceId, principal);
        deviceService.sendActionToDevice(device, requestedAction); //todo Exception handler
        return ResponseEntity.ok(new ApiResponse(true, action.toUpperCase()));
    }
}
