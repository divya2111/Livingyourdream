package com.livingyourdream.service;

import com.livingyourdream.Enum.SeatStatus.SeatStatus;
import com.livingyourdream.Enum.StayStatus;
import com.livingyourdream.model.*;
import com.livingyourdream.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Defaulttripplanservice {

    @Autowired
    private Defaulttripplanrepo defaulttripplanrepo;

    @Autowired
    private PlaceRepo placeRepo;

    @Autowired
    private Transportrepo transportrepo;

    @Autowired
    private Stayrepo stayrepo;

    @Autowired
    private StayBookingrepo stayBookingrepo;

    @Autowired
    private Seatrepo seatrepo;

    @Transactional
    public Defaulttripplan saveplan(Defaulttripplan plan) {
        // 1. Fetch Places
        if (plan.getPlaces() != null && !plan.getPlaces().isEmpty()) {
            List<Place> fullPlaces = plan.getPlaces().stream()
                    .map(p -> placeRepo.findById(p.getPlace_id())
                            .orElseThrow(() -> new RuntimeException("Place not found: " + p.getPlace_id())))
                    .collect(Collectors.toList());
            plan.setPlaces(fullPlaces);
        }

        // 2. Fetch Transport
        List<Transport> fullTransport = new ArrayList<>();
        if (plan.getTransports() != null && !plan.getTransports().isEmpty()) {
            fullTransport = plan.getTransports().stream()
                    .map(t -> transportrepo.findById(t.getTransport_id())
                            .orElseThrow(() -> new RuntimeException("Transport not found: " + t.getTransport_id())))
                    .collect(Collectors.toList());
            plan.setTransports(fullTransport);
        }

        // 3. Validate and confirm Seats
        List<Seat> confirmedSeats = new ArrayList<>();
        double transportCost = 0.0;
        if (plan.getSeats() != null && !plan.getSeats().isEmpty()) {
            for (Seat s : plan.getSeats()) {
                Seat seat = seatrepo.findById(s.getSeatid())
                        .orElseThrow(() -> new RuntimeException("Seat not found"));

                boolean belongsToTransport = fullTransport.stream()
                        .anyMatch(t -> t.getTransport_id().equals(seat.getTransport().getTransport_id()));
                if (!belongsToTransport) throw new RuntimeException("Seat " + seat.getSeatNumber() + " doesn't match selected transport.");

                if (seat.getStatus() == SeatStatus.CONFORMED)
                    throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());

                seat.setStatus(SeatStatus.CONFORMED);
                seatrepo.save(seat);

                confirmedSeats.add(seat);
                transportCost += seat.getTransport().getPrice();
            }
            plan.setSeats(confirmedSeats);
        }

        // 4. Fetch Stay
        List<Stay> fullStay = new ArrayList<>();
        if (plan.getStay() != null && !plan.getStay().isEmpty()) {
            fullStay = plan.getStay().stream()
                    .map(stay -> stayrepo.findById(stay.getStay_id())
                            .orElseThrow(() -> new RuntimeException("Stay not found: " + stay.getStay_id())))
                    .collect(Collectors.toList());
            plan.setStay(fullStay);
        }

        // 5. Validate and confirm StayBookings
        List<StayBooking> confirmedStays = new ArrayList<>();
        double stayCost = 0.0;
        if (plan.getStays() != null && !plan.getStays().isEmpty()) {
            for (StayBooking sb : plan.getStays()) {
                StayBooking stayBooking = stayBookingrepo.findById(sb.getSid())
                        .orElseThrow(() -> new RuntimeException("Stay booking not found"));

                Stay stay = stayBooking.getStay();
                if (stay == null) {
                    throw new RuntimeException("Stay not linked in StayBooking: " + sb.getSid());
                }

                boolean belongsToStay = fullStay.stream()
                        .anyMatch(s -> s.getStay_id().equals(stayBooking.getStay().getStay_id()));
                if (!belongsToStay)
                    throw new RuntimeException("StayBooking " + stayBooking.getRoom_no() + " doesn't belong to selected stay");

                if (stayBooking.getStatus() == StayStatus.CONFORMED)
                    throw new RuntimeException("Room already booked: " + stayBooking.getRoom_no());

                // Cost calculation
                long nights = ChronoUnit.DAYS.between(
                        sb.getCheckInDate(),
                        sb.getCheckOutDate());

                int guests = plan.getPeopleCount();

                int numberOfRooms = stay.getType().equalsIgnoreCase("Resort") ? 1
                        : (int) Math.ceil(guests / 2.0);

                double cost = stay.getPrice() * nights * numberOfRooms;

                stayBooking.setCheckInDate(sb.getCheckInDate());
                stayBooking.setCheckOutDate(sb.getCheckOutDate());
                stayBooking.setNumberOfGuests(guests);
                stayBooking.setNumberOfRooms(numberOfRooms);
                stayBooking.setTotalPrice(cost);
               // stayBooking.setDefaultTripPlan(plan); // Map to this plan
                stayBooking.setStatus(StayStatus.CONFORMED);

                stayBookingrepo.save(stayBooking);
                confirmedStays.add(stayBooking);

                stayCost += cost;
            }
            plan.setStays(confirmedStays);
        }

        plan.setTotalCost(transportCost + stayCost);
        return defaulttripplanrepo.save(plan);
    }


    public List<Defaulttripplan> findallplans() {
        return defaulttripplanrepo.findAll();
    }

    public Defaulttripplan updateplan(Long id, Defaulttripplan updatedplan) {
        Defaulttripplan plan = defaulttripplanrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        plan.setTitle(updatedplan.getTitle());
        plan.setCheckInDate(updatedplan.getCheckInDate());
        plan.setCheckOutDate(updatedplan.getCheckOutDate());
        plan.setPeopleCount(updatedplan.getPeopleCount());

        plan.setPlaces(updatedplan.getPlaces());
        plan.setTransports(updatedplan.getTransports());
        plan.setStay(updatedplan.getStay());

        return saveplan(plan);
    }

    public void deleteplan(Long id) {
        defaulttripplanrepo.deleteById(id);
    }

    public List<Defaulttripplan> findbyplace(Place place) {
        return defaulttripplanrepo.findByPlaces(place);
    }

    public Optional<Defaulttripplan> findbyid(Long id) {
        return defaulttripplanrepo.findById(id);
    }
}
