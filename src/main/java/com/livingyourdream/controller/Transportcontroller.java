package com.livingyourdream.controller;


import com.livingyourdream.Enum.Mode;
import com.livingyourdream.model.Seat;
import com.livingyourdream.model.Transport;
import com.livingyourdream.service.Transportservice;
import com.livingyourdream.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transport")
public class Transportcontroller {

    @Autowired
    private Transportservice transportservice;

    @Autowired
    private Userservice userservice;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Transport> addTransport(@RequestBody Transport transport){
        return ResponseEntity.ok(transportservice.addTransport(transport));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Transport> updatetransport(@PathVariable Long id, @RequestBody Transport transport){
        return  ResponseEntity.ok(transportservice.updateTransport(id,transport));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Transport> deletetransport(@PathVariable Long id){
        transportservice.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Transport>  getbyid(Long id){
        return transportservice.getbyid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<List<Transport>>getalltransport(){
        return ResponseEntity.ok(transportservice.gettransportall());
    }

    @GetMapping("/location/{departure_location}/{arrival_location}")
    public ResponseEntity<List<Transport>> getbylocation(String departure_location, String arrival_location){
        return ResponseEntity.ok(transportservice.getbylocation(departure_location,arrival_location));
    }


    @GetMapping("mode/{mode}")
    public ResponseEntity<List<Transport>> getByMode(@PathVariable("mode") Mode mode){
        List<Transport> transports = transportservice.gettransportmode(mode);
        return ResponseEntity.ok(transports);
    }

    @GetMapping("/{vehicleNo}")
    public ResponseEntity<Transport> getTransport(@PathVariable String vehicleNo) {
        return transportservice.getTransportById(vehicleNo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{vehicleNo}/seats")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable String vehicleNo) {
        List<Seat> availableSeats = transportservice.getAvailableSeats(vehicleNo);
        return ResponseEntity.ok(availableSeats);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{transportId}/seats/bulk")
    public ResponseEntity<List<Seat>> addMultipleSeats(@PathVariable Long transportId, @RequestBody List<Seat> seats) {
        return new ResponseEntity<>(transportservice.addseats(transportId, seats), HttpStatus.CREATED);
    }

}
