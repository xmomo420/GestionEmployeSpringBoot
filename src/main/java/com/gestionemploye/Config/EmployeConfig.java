/*package com.gestionemploye.Config;

import com.gestionemploye.GestionEmploye.Adresse;
import com.gestionemploye.GestionEmploye.Coordonnees;
import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.GestionEmploye.Fonction;
import com.gestionemploye.Repository.*;
import com.gestionemploye.Service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

// Classe pour ajouter un utilisateur

@Configuration
public class EmployeConfig {
    private final EmployeRepository employeRepository;
    private final EmployeService employeService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeConfig(EmployeRepository employeRepository, EmployeService employeService, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.employeRepository = employeRepository;
        this.employeService = employeService;
        this.passwordEncoder = passwordEncoder1;
    }


    @Bean
    CommandLineRunner commandLineRunner(EmployeRepository employeRepository) {

        return args -> {

            // Création des objets pour l'employé
            Employe employe = new Employe();
            Adresse adresse = new Adresse();
            Coordonnees coordonnees = new Coordonnees();
            Adresse.Province _province = Adresse.Province.QUEBEC;
            Fonction _fonction = Fonction.ADJOINT;
            // Modifier les attributs de Employe
            employe.setPrenom("Premier");
            employe.setNom("");
            employe.setFonction(_fonction);
            // Pour créer un nom d'utilisateur unique
            String nomUtilisateur = "Premier";
            employe.setNomUtilisateur(nomUtilisateur);
            // Cryptage du mot de passe
            employe.setMotDePasse(passwordEncoder.encode("123"));
            employe.setTauxHoraire(5d);
            // Modifier l'adresse
            adresse.setRue("sdfsdf");
            adresse.setVille("ffdg");
            adresse.setProvince(_province);
            adresse.setCodePostal("dfgfdg");
            // Modifier les coordonnées
            coordonnees.setAdresse(adresse);
            coordonnees.setCourriel("fer");
            coordonnees.setNoTelephone("noTelephone");
            // Ajouter les coordonnées à l'employé
            employe.setCoordonnees(coordonnees);
            // Ajouter l'horaire et la feuille de temps vide
            employe.ajouterHoraireFeuilleTempsVide();
            // Ajouter l'employé dans la base de données
            employeService.ajouterEmploye(employe);
            // Ajouter un attribut pour indiquer que l'employé a été ajouté avec succès
        };
    }
}*/
