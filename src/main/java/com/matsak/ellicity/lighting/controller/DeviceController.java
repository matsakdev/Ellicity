package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.payload.DeviceActionRequest;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{system}/{circuit}")
public class DeviceController {

    @Autowired
    CircuitService circuitService;

    @PostMapping("/{device}/on")
    public void enableDeviceAction(@PathVariable(name = "system") Long systemId,
                              @PathVariable(name = "circuit") Long circuitId,
                              @PathVariable(name = "device") Long deviceId,
                              @RequestBody DeviceActionRequest request,
                              @AuthenticationPrincipal UserPrincipal principal) {
        circuitService.sendActionToDevice(systemId, circuitId, deviceId, request, principal.getId());
    }

    @PostMapping("/{device}/off")
    public void disableDeviceAction(@PathVariable(name = "system") Long systemId,
                              @PathVariable(name = "circuit") Long circuitId,
                              @PathVariable(name = "device") Long deviceId,
                              @RequestBody DeviceActionRequest request,
                              @AuthenticationPrincipal UserPrincipal principal) {
        circuitService.sendActionToDevice(systemId, circuitId, deviceId, request, principal.getId());
    }
}
