package com.SeatReservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SeatReservation.Model.Attendance;
import com.SeatReservation.repository.AttendanceRepository; // Assuming you have a repository for Attendance

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void save(Attendance attendance) {
        attendanceRepository.save(attendance);
        System.out.println("Attendance saved: " + attendance);
    }

    public Optional<Attendance> findByBookingId(Long bookingId) {
        return attendanceRepository.findByBookingId(bookingId);
    }


    public List<Attendance> findByUserId(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }





}