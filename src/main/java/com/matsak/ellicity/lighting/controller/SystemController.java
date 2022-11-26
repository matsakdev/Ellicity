package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.service.sections.SystemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class SystemController {
    @Autowired
    private SystemServiceImpl systemService;

    @GetMapping("/all")
    private List<System> getAllSystems(){
        return systemService.getAllSystems();
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
