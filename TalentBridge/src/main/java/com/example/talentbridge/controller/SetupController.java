package com.example.talentbridge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.example.talentbridge.model.Role;
import com.example.talentbridge.model.User;
import com.example.talentbridge.model.constant.Gender;
import com.example.talentbridge.repository.RoleRepository;
import com.example.talentbridge.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Temporary controller for initial setup - REMOVE AFTER USE
 */
@Tag(name = "Setup")
@RestController
@RequiredArgsConstructor
@RequestMapping("/setup")
public class SetupController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create-admin")
    @Operation(summary = "Create admin account - TEMPORARY")
    @SecurityRequirements()
    public ResponseEntity<Map<String, Object>> createAdmin() {
        Map<String, Object> response = new HashMap<>();

        // Check if admin already exists
        if (userRepository.existsByEmail("testadmin@talentbridge.com")) {
            response.put("success", false);
            response.put("message", "Admin account already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Find ADMIN role
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        if (adminRole == null) {
            response.put("success", false);
            response.put("message", "ADMIN role not found in database");
            return ResponseEntity.badRequest().body(response);
        }

        // Create admin user
        User admin = new User();
        admin.setEmail("testadmin@talentbridge.com");
        admin.setName("Test Admin");
        admin.setPassword(passwordEncoder.encode("Admin@123456"));
        admin.setDob(LocalDate.of(1990, 1, 1));
        admin.setAddress("System");
        admin.setGender(Gender.OTHER);
        admin.setRole(adminRole);

        userRepository.save(admin);

        response.put("success", true);
        response.put("message", "Admin account created successfully");
        response.put("email", "testadmin@talentbridge.com");
        response.put("password", "Admin@123456");

        return ResponseEntity.ok(response);
    }
}
