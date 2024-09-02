package com.seatreservation.userseatReservation.Service;


import com.seatreservation.userseatReservation.Entity.Seat;
import com.seatreservation.userseatReservation.Entity.SeatReservation;
import com.seatreservation.userseatReservation.Entity.User;
import com.seatreservation.userseatReservation.Repositories.SeatRepository;
import com.seatreservation.userseatReservation.Repositories.SeatReservationRepository;
import com.seatreservation.userseatReservation.Repositories.UserRepository;
import com.seatreservation.userseatReservation.dto.SeatReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatReservationService {

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    public void bookSeat(SeatReservationRequest request, String email) {
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.isBooked()) {
            throw new RuntimeException("Seat already booked");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"+ email));

        SeatReservation reservation = new SeatReservation();
        reservation.setUser(user);
        reservation.setSeat(seat);
        reservation.setTraineeId(request.getTraineeId());
        reservation.setTraineeName(request.getTraineeName());
        reservation.setGroupName(request.getGroupName());

        seat.setBooked(true);
        seatRepository.save(seat);
        seatReservationRepository.save(reservation);
    }
}
