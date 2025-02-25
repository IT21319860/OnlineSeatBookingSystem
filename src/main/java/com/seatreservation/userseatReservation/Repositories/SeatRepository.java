package com.seatreservation.userseatReservation.Repositories;

import com.seatreservation.userseatReservation.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByBookedFalse();
}
