package com.hustcinema.backend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hustcinema.backend.enums.Role;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.UserRepository;

@Configuration
public class ApplicationInitConfig {
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUserName("admin").isEmpty()) {
                var roles = Role.ADMIN.name();

                User user =  new User();
                user.setUserName("admin");
                user.setPassword(passwordEncoder.encode("admin")); 
                user.setRole(roles);
                userRepository.save(user);

                System.out.println("admin account has been created with default password: admin, please change it");
            }
        };
    }
}
