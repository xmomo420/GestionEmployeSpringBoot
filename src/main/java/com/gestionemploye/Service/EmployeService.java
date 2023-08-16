package com.gestionemploye.Service;

import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.GestionEmploye.FeuilleTemps;
import com.gestionemploye.GestionEmploye.Horaire;
import com.gestionemploye.GestionEmploye.QuartTravail;
import com.gestionemploye.Repository.EmployeRepository;
import com.gestionemploye.Repository.QuartTravailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeService implements UserDetailsService {

    private final EmployeRepository employeRepository;
    private final QuartTravailRepository quartTravailRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EmployeService(EmployeRepository employeRepository, QuartTravailRepository quartTravailRepository) {
        this.employeRepository = employeRepository;
        this.quartTravailRepository = quartTravailRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String nomUtilisateur) throws UsernameNotFoundException {
        return employeRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun " +
                        "employé possède ce nom d'utilisateur"));
    }

    @Transactional
    public void ajouterEmploye(Employe employe) { // Bien faire attention à l'ordre, c'est important
        FeuilleTemps feuilleTemps = employe.getFeuilleTemps();
        Horaire horaire = employe.getHoraire();
        // Assurez-vous que les entités liées sont sauvegardées
        entityManager.persist(feuilleTemps);
        entityManager.persist(horaire);
        // Associez les quarts de travail à la feuille de temps et à l'horaire
        for (QuartTravail quartTravail : feuilleTemps.getListeQuartsTravailles()) {
            quartTravail.setFeuilleTemps(feuilleTemps);
            entityManager.persist(quartTravail);
        }
        for (QuartTravail quartTravail : horaire.getListeQuartsPrevus()) {
            quartTravail.setHoraire(horaire);
            entityManager.persist(quartTravail);
        }
        entityManager.flush();
        Employe nouvelEmploye = employeRepository.save(employe);
        entityManager.persist(nouvelEmploye.getCoordonnees());
        entityManager.persist(nouvelEmploye.getCoordonnees().getAdresse());
    }

    @Transactional
    public void modifierHoraire(Horaire horaire) {

    }


}
