package com.livingyourdream.controller;

import com.livingyourdream.dto.StayBookingDto;
import com.livingyourdream.model.*;
import com.livingyourdream.service.Stayservice;
import com.livingyourdream.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stay")
public class StayController {

    @Autowired
    private Stayservice  stayservice;

    @Autowired
    private Userservice userservice;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Stay> createstay(@RequestBody Stay stay){
        return ResponseEntity.ok(stayservice.createstay(stay));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/default/{defaultTripId}/stay")
//    public ResponseEntity<Stay> addTransportToDefault(@PathVariable Long defaultTripId, @RequestBody Stay stay){
//        return ResponseEntity.ok(stayservice.addStayToDefaultPlan(defaultTripId,stay));
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/custom/{customTripId}/stay/{stayId}")
//    public ResponseEntity<Customtripplan> addStayToCustom(@PathVariable Long customTripId, @PathVariable Long stayId) {
//        return ResponseEntity.ok(stayservice.addStayToCustomPlan(stayId, customTripId));
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Stay> updatestay(@PathVariable Long id, @RequestBody Stay stay){
        return ResponseEntity.ok(stayservice.updatestay(id,stay));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Stay> deletestay(@PathVariable Long id){
        stayservice.deletestay(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Stay>> getallstays(){
        return ResponseEntity.ok(stayservice.getAllstays());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Stay> getstaybyid(@PathVariable Long id){
        return stayservice.getstaybyid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Stay>> getstaybytype(@PathVariable String type){
        return ResponseEntity.ok(stayservice.getstaybytype(type));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Stay>> getstaybyname(@PathVariable String name){
        return ResponseEntity.ok(stayservice.getstaybyname(name));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Stay>> getstaybycity(@PathVariable String city){
        return ResponseEntity.ok(stayservice.getstaybycity(city));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Stay>> getstaybystate(@PathVariable String state){
        return ResponseEntity.ok(stayservice.getstaybystate(state));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Stay>> getstaybycountry(@PathVariable String country){
        return ResponseEntity.ok(stayservice.getstaybycountry(country));
    }

    @GetMapping("/location/{city}/{state}/{country}")
    public ResponseEntity<List<Stay>> getstaybylocation(@PathVariable String city,@PathVariable String state, @PathVariable String  country){
        return ResponseEntity.ok(stayservice.getstaybycitycountryandstate(city,state,country));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{stayId}/book")
    public ResponseEntity<List<StayBooking>> addMultipleSeats(@PathVariable Long stayId, @RequestBody List<StayBookingDto> stays) {
        return new ResponseEntity<>(stayservice.addstays(stayId, stays), HttpStatus.CREATED);
    }

}
