package com.example.cellphones;

import com.example.cellphones.model.parking.Employee;
import com.example.cellphones.model.user.Gender;
import com.example.cellphones.model.user.Role;
import com.example.cellphones.model.user.User;
import com.example.cellphones.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class ParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (userRepository.findAll().isEmpty()) {
                Employee employee = Employee.builder()
                        .fullName("Manager System")
                        .position("Manager")
                        .dateOfBirth(formatter.parse("19/06/2002"))
                        .build();
                var admin = User.builder()
                        .username("manager")
                        .password(passwordEncoder.encode("1"))
                        .role(Role.MANAGER)
                        .employee(employee)
                        .gender(Gender.MALE)
                        .enabled(true)
                        .build();
                employee.setUser(admin);
                userRepository.save(admin);
            } else {
                System.out.println("System...");
            }
        };
    }
}
