package com.hospital.controller;

import com.hospital.dto.AppointmentRequest;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    public PatientController(AppointmentService appointmentService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request,
                                              HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("userEmail");
        return ResponseEntity.ok(appointmentService.bookAppointment(email, request));
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> myAppointments(HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("userEmail");
        return ResponseEntity.ok(appointmentService.getPatientAppointments(email));
    }
}
