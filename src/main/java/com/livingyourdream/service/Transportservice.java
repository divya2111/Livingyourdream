package com.livingyourdream.service;

//import com.livingyourdream.Exception.ResourceNotFoundException;
import com.livingyourdream.Enum.Mode;
import com.livingyourdream.Exception.ResourceNotFoundException;
import com.livingyourdream.model.Seat;
import com.livingyourdream.model.Transport;
import com.livingyourdream.repository.Customtripplanrepo;
import com.livingyourdream.repository.Defaulttripplanrepo;
import com.livingyourdream.repository.Seatrepo;
import com.livingyourdream.repository.Transportrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class Transportservice {

    @Autowired
    private Transportrepo transportrepo;

    @Autowired
    private Defaulttripplanrepo defaulttripplanrepo;

    @Autowired
    private Customtripplanrepo customtripplanrepo;

    @Autowired
    private Seatrepo seatrepo;

    public Transport addTransport(Transport transport){
       return transportrepo.save(transport);
    }

    public Transport updateTransport(Long id,Transport updatedtransport){
        Transport transport = transportrepo.findById(id).orElseThrow();
        transport.setMode(updatedtransport.getMode());
        transport.setProvider(updatedtransport.getProvider());
        transport.setVehicleNo(updatedtransport.getVehicleNo());
        transport.setDeparturelocation(updatedtransport.getDeparturelocation());
        transport.setArrivallocation(updatedtransport.getArrivallocation());
        transport.setDeparture_time(updatedtransport.getDeparture_time());
        transport.setArrival_time(updatedtransport.getArrival_time());
        transport.setPrice(updatedtransport.getPrice());

        return transportrepo.save(transport);
    }
    public void deleteTransport(Long id){
        transportrepo.deleteById(id);
    }
    public List<Transport> getbylocation(String departurelocation,String arrivallocation){
        return transportrepo.findByDeparturelocationAndArrivallocation(departurelocation,arrivallocation);
    }
    public Optional<Transport> getbyid(Long id){
        return transportrepo.findById(id);
    }

    public List<Transport> gettransportall(){
        return transportrepo.findAll();
    }

    public List<Transport> gettransportmode(Mode mode){return transportrepo.findByMode(mode);}

    public Optional<Transport> getTransportById(String vehicleNo) {
        return transportrepo.findByVehicleNo(vehicleNo);
    }

    public List<Seat> getAvailableSeats(String vehicleNo) {
        return seatrepo.findByVehicleNo(vehicleNo);
    }



    public List<Seat> addseats(Long transportId, List<Seat> seats) {
        Transport transport = transportrepo.findById(transportId)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found"));

        for (Seat seat : seats) {
            seat.setTransport(transport);
        }

        return seatrepo.saveAll(seats);
    }



}
