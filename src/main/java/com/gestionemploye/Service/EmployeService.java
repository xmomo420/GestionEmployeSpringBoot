package com.gestionemploye.Service;

import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.Repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeService implements UserDetailsService {

    private final EmployeRepository employeRepository;

    @Autowired
    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String nomUtilisateur) throws UsernameNotFoundException {
        return employeRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun " +
                        "employé possède ce nom d'utilisateur"));
    }
}
