package com.example.talentbridge.init;

import lombok.RequiredArgsConstructor;
import com.example.talentbridge.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataValidator implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        checkRoleTable();
    }

    private void checkRoleTable() {
        if (
                !roleRepository.existsByName("ADMIN")
                        || !roleRepository.existsByName("USER")
                        || !roleRepository.existsByName("RECRUITER")
        )
            throw new IllegalStateException("Invalid Role table data");
    }
}
