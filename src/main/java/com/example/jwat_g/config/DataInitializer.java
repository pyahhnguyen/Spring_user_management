package com.example.jwat_g.config;

import com.example.jwat_g.mapper.AccountMapper;
import com.example.jwat_g.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminAccount();
    }

    private void initializeAdminAccount() {
        String adminEmail = "admin@gmail.com";
        String defaultPassword = "admin123";

        if (accountMapper.findByEmail(adminEmail) == null) {
            Account admin = new Account();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(defaultPassword));
            admin.setFullName("System Administrator");
            admin.setRole("ADMIN");

            accountMapper.insertAccount(admin);
            log.info("Admin account created successfully!");
            log.info("  Email: {}", adminEmail);
            log.info("  Password: {}", defaultPassword);
        } else {
            log.info("Admin account already exists.");
        }
    }
}
