package com.livingyourdream.service;


import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import com.livingyourdream.Enum.StayStatus;
import com.livingyourdream.model.*;
import com.livingyourdream.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Customtripplanservice {

    @Autowired
    private Customtripplanrepo customtripplanrepo;

    @Autowired
    private PlaceRepo placeRepo;

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Transportrepo transportrepo;

    @Autowired
    private Seatrepo seatrepo;

    @Autowired
    private Stayrepo stayrepo;

    @Autowired
    private StayBookingrepo stayBookingrepo;

    @Transactional
    public Customtripplan savePlan(Customtripplan plan, Long userId) {
        if (plan.getUser() != null && plan.getUser().getUser_id() != null) {
            User user = userrepo.findById(plan.getUser().getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            plan.setUser(user);
        }

        // Fetch full Places from DB
        if (plan.getPlaces() != null && !plan.getPlaces().isEmpty()) {
            List<Place> fullPlaces = plan.getPlaces().stream()
                    .map(place -> placeRepo.findById(place.getPlace_id())
                            .orElseThrow(() -> new RuntimeException("Place not found: " + place.getPlace_id())))
                    .collect(Collectors.toList());
            plan.setPlaces(fullPlaces);
        }
        // Transport
        List<Transport> fullTransport = new ArrayList<>();
        if (plan.getTransports() != null && !plan.getTransports().isEmpty()) {
            fullTransport = plan.getTransports().stream()
                    .map(transport -> transportrepo.findById(transport.getTransport_id())
                            .orElseThrow(() -> new RuntimeException("Transport not found: " + transport.getTransport_id())))
                    .collect(Collectors.toList());
            plan.setTransports(fullTransport);
        }

        List<Seat> confirmedseats = new ArrayList<>();
        double transportcost = 0.0;
        if(plan.getSeats() != null && !plan.getSeats().isEmpty()) {

            for (Seat s : plan.getSeats()) {
                Seat seat = seatrepo.findById(s.getSeatid())
                        .orElseThrow(() -> new RuntimeException("Seat not Found"));

                boolean isVaildTransport = fullTransport.stream()
                        .anyMatch(t -> t.getTransport_id().equals(seat.getTransport().getTransport_id()));

                if(!isVaildTransport){
                    throw new RuntimeException("Seat "+ seat.getSeatNumber() + " does not belong to selected transport.");
                }
                if(seat.getStatus() == SeatStatus.CONFORMED ){
                    throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already booked.");
                }

                seat.setStatus(SeatStatus.CONFORMED);
                seatrepo.save(seat);

                confirmedseats.add(seat);

                transportcost += seat.getTransport().getPrice();
            }
            plan.setSeats(confirmedseats);
        }
        // Stay
        List<Stay> fullStay = new ArrayList<>();
        if (plan.getStay() != null && !plan.getStay().isEmpty()) {
            fullStay = plan.getStay().stream()
                    .map(stay -> stayrepo.findById(stay.getStay_id())
                            .orElseThrow(() -> new RuntimeException("Stay not found: " + stay.getStay_id())))
                    .collect(Collectors.toList());
            plan.setStay(fullStay);
        }

        List<StayBooking> confirmedStay = new ArrayList<>();
        double stayCost = 0.0;
        if(plan.getStays() != null && !plan.getStays().isEmpty()) {
            for (StayBooking sb : plan.getStays()) {
                StayBooking staybooking = stayBookingrepo.findById(sb.getSid())
                        .orElseThrow(() -> new RuntimeException("Seat not Found"));

                Stay stay = staybooking.getStay();
                if (stay == null) {
                    throw new RuntimeException("Stay not linked to StayBooking: " + sb.getSid());
                }

                boolean isVaildStay = fullStay.stream()
                        .anyMatch(st -> st.getStay_id().equals(staybooking.getStay().getStay_id()));

                if (!isVaildStay) {
                    throw new RuntimeException("Stay " + staybooking.getRoom_no() + " does not belong to selected Stay.");
                }
                if (staybooking.getStatus() == StayStatus.CONFORMED) {
                    throw new RuntimeException("Stay " + staybooking.getRoom_no() + " is already booked.");
                }

                // Calculate cost
                long nights = ChronoUnit.DAYS.between(
                        sb.getCheckInDate(),
                        sb.getCheckOutDate());

                int guests = plan.getPeopleCount();

                int numberOfRooms = stay.getType().equalsIgnoreCase("Resort") ? 1
                        : (int) Math.ceil(guests / 2.0);

                double cost = stay.getPrice() * nights * numberOfRooms;

                staybooking.setCheckInDate(sb.getCheckInDate());
                staybooking.setCheckOutDate(sb.getCheckOutDate());
                staybooking.setNumberOfGuests(guests);
                staybooking.setNumberOfRooms(numberOfRooms);
                staybooking.setTotalPrice(cost);
                staybooking.setStatus(StayStatus.CONFORMED);



                stayBookingrepo.save(staybooking);
                confirmedStay.add(staybooking);

                stayCost += cost;
            }
            plan.setStays(confirmedStay);
        }

        plan.setTotalCost(transportcost + stayCost);
        return customtripplanrepo.save(plan);
    }

    public List<Customtripplan> getAllPlans() {
        return customtripplanrepo.findAll();
    }

    public  Customtripplan updatecustomplan(Long id, Customtripplan updatedcustomtripplan){
        Customtripplan customplan = customtripplanrepo.findById(id).orElseThrow();
        customplan.setTitle(updatedcustomtripplan.getTitle());
        customplan.setDuration_days(updatedcustomtripplan.getDuration_days());
        customplan.setCreate_date(updatedcustomtripplan.getCreate_date());

        return customtripplanrepo.save(customplan);
    }

    public void deletecustomplan(Long id){
        customtripplanrepo.deleteById(id);
    }

}
