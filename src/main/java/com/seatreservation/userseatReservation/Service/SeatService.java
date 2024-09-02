package com.seatreservation.userseatReservation.Service;


import com.seatreservation.userseatReservation.Entity.Seat;
import com.seatreservation.userseatReservation.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private static SeatRepository seatRepository;

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    public static void initializeSeats(int numberOfSeats) {
        for (int i = 1; i <= numberOfSeats; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setBooked(false);
            seatRepository.save(seat);
        }
    }
}
