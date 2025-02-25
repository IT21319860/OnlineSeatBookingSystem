package com.seatreservation.userseatReservation.Repositories;

import com.seatreservation.userseatReservation.Entity.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {
}
