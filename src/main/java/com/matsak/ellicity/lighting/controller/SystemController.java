package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.payload.ApiResponse;
import com.matsak.ellicity.lighting.payload.ConnectUserToSystemRequest;
import com.matsak.ellicity.lighting.security.CurrentUser;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import com.matsak.ellicity.lighting.service.sections.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @Autowired
    private CircuitService circuitService;

    @PostMapping("/connect")
    public ApiResponse connectUserWithSystem(@RequestBody ConnectUserToSystemRequest requestBody, @AuthenticationPrincipal UserPrincipal user) {
        if (systemService.isUserConnected(user.getId(), requestBody.getSystemName())) {
            return new ApiResponse(false, "This system is already connected!");
        }
        systemService.connectUser(user.getId(), requestBody.getSystemName(), requestBody.getPassKey());
        //todo check result
        return new ApiResponse(true, "You've successfully connected with the system");
    }

    @GetMapping
    public ResponseEntity<?> getUserSystems(@CurrentUser UserPrincipal principal) {
        List<System> systems = systemService.getUserSystems(principal.getId());
        return ResponseEntity.ok(systems);
    }
    @GetMapping("/{id}/circuits")
    public ResponseEntity<?> getUserCircuitsBySystem(@PathVariable(name = "id") Long systemId, @CurrentUser UserPrincipal principal) {
        List<Circuit> circuits = circuitService.getUserCircuitsBySystemId(systemId, principal.getId());
        return ResponseEntity.ok(circuits);
    }

    @GetMapping("/{id}/checkuser/{userId}")
    public ApiResponse checkIfUserConnectedToSystem(@PathVariable(name = "id") Long systemId,
                                             @PathVariable(name = "userId") Long userId) {
        if (validateUserAccess(systemId, userId)) {
            return new ApiResponse(true, "The system is already connected to the user");
        }
        else return new ApiResponse(false, "This user don't connected to the system");
    }

    @GetMapping("/{id}/checkuser/")
    public ApiResponse checkIfUserConnectedToSystem(@PathVariable(name = "id") Long systemId,
                                             @AuthenticationPrincipal UserPrincipal principal) {
        if (validateUserAccess(systemId, principal.getId())) {
            return new ApiResponse(true, "The system is already connected to the user");
        }
        else return new ApiResponse(false, "This user don't connected to the system");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSystemById(@PathVariable(name = "id") Long systemId, @CurrentUser UserPrincipal principal) {
        validateUserAccess(systemId, principal.getId());
        System circuits = systemService.getSystemById(systemId);
        return ResponseEntity.ok(circuits);
    }

    private boolean validateUserAccess(Long systemId, Long userId) {
        return systemService.isUserConnected(userId, systemId);
    }

    @GetMapping("/all")//todo only moderator
    public ResponseEntity<?> getAllUsersSystems() {
        return ResponseEntity.ok(systemService.getAllUsersSystems());
    }

    @GetMapping("/all_us")
    public ResponseEntity<?> getUsersSystemsByUser(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(systemService.getUserSystemsByUser(principal.getId()));
    }

//    @GetMapping("/{systemId}/instant_consumption")
//    private Double getInstantConsumption(@PathVariable("systemId") Long systemId){
//        return systemService.getInstantConsumption();
//    }
//
//    @GetMapping("/{systemId}/consumption_during_day")
//    private Map<Time, Double> getLastDayConsumption(@PathVariable("systemId") Long systemId){
//        return systemService.getLastDayConsumption();
//    }
//
//    @GetMapping("/{systemId}/consumption_during_week")
//    private Map<Date, Double> getLastWeekConsumption(@PathVariable("systemId") Long systemId){
//        return systemService.getLastWeekConsumption();
//    }
//
//    @GetMapping("/{systemId}/consumption_during_month")
//    private Map<Date, Double> getLastMonthConsumption(@PathVariable("systemId") Long systemId){
//        return systemService.getLastMonthConsumption();
//    }
//
//    @GetMapping("/{systemId}/consumption_during_year")
//    private Map<Date, Double> getLastYearConsumption(@PathVariable("systemId") Long systemId){
//        return systemService.getLastYearConsumption();
//    }







}
