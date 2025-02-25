package com.seatreservation.userseatReservation.Controllers;


import com.seatreservation.userseatReservation.Service.SeatReservationService;
import com.seatreservation.userseatReservation.Service.SeatService;
import com.seatreservation.userseatReservation.dto.SeatDto;
import com.seatreservation.userseatReservation.dto.SeatReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatReservationService seatReservationService;

    @GetMapping
    public List<SeatDto> getSeats() {
        return seatService.getAllSeats().stream()
                .map(seat -> {
                    SeatDto seatDto = new SeatDto();
                    seatDto.setSeatId(seat.getId());
                    seatDto.setSeatNumber(seat.getSeatNumber());
                    seatDto.setBooked(seat.isBooked());
                    return seatDto;
                }).collect(Collectors.toList());
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookSeat(@RequestBody SeatReservationRequest request, Principal principal) {
        seatReservationService.bookSeat(request, principal.getName());
        return ResponseEntity.ok().build();
    }
}


