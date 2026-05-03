package com.hospital.service;

import com.hospital.config.JwtUtil;
import com.hospital.dto.LoginRequest;
import com.hospital.dto.RegisterRequest;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.model.User;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRole(User.Role.valueOf(request.getRole().toUpperCase()));
        userRepository.save(user);

        if (user.getRole() == User.Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setName(request.getName());
            doctor.setSpecialization(request.getSpecialization());
            doctor.setPhone(request.getPhone());
            doctor.setAvailableDays(request.getAvailableDays());
            doctor.setAvailableTime(request.getAvailableTime());
            doctor.setUser(user);
            doctorRepository.save(doctor);
        }

        if (user.getRole() == User.Role.PATIENT) {
            Patient patient = new Patient();
            patient.setName(request.getName());
            patient.setPhone(request.getPhone());
            patient.setAge(request.getAge());
            patient.setAddress(request.getAddress());
            patient.setUser(user);
            patientRepository.save(patient);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Registered successfully");
        return response;
    }

    public Map<String, String> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().name());
        response.put("name", user.getName());
        return response;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
