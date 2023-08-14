/*package com.gestionemploye.Config;

import com.gestionemploye.Repository.*;
import com.gestionemploye.Service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmployeConfig {
    private final EmployeRepository employeRepository;
    private final EmployeService employeService;

    @Autowired
    public EmployeConfig(EmployeRepository employeRepository, EmployeService employeService) {
        this.employeRepository = employeRepository;
        this.employeService = employeService;
    }


    @Bean
    CommandLineRunner commandLineRunner(EmployeRepository employeRepository) {

        return args ->
            employeRepository.findAll().forEach(employe ->
            {
            employe.ajouterHoraireFeuilleTempsVide();
            employeService.ajouterEmploye(employe);
            });
    }
}*/
