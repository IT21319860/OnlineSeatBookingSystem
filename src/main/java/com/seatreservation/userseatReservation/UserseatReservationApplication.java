package com.seatreservation.userseatReservation;

import com.seatreservation.userseatReservation.Service.SeatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserseatReservationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserseatReservationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SeatService.initializeSeats(50);
	}
}
