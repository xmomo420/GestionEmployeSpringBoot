package com.gestionemploye.Config;

import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EmployeConfig {

    private final BCryptPasswordEncoder  passwordEncoder;
    @Autowired
    public EmployeConfig(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    CommandLineRunner commandLineRunner(EmployeRepository employeRepository) {
        return args -> {
            Employe employe = employeRepository.findByNomUtilisateur("Premier")
                    .orElse(null);
            assert employe != null;
            System.out.println(employe.getMotDePasse());
            employe.setMotDePasse(passwordEncoder.encode("abc123"));
            employeRepository.save(employe);
        };
    }
}
