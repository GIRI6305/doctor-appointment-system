package com.hospital.controller;

import com.hospital.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final AppointmentService appointmentService;

    public DoctorController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> myAppointments(HttpServletRequest httpRequest) {
        String email = (String) httpRequest.getAttribute("userEmail");
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(email));
    }

    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }
}
