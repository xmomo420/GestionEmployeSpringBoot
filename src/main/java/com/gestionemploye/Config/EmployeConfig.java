package com.gestionemploye.Config;

import com.gestionemploye.GestionEmploye.*;
import com.gestionemploye.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class EmployeConfig {

    /**
     * Pour créer le premier Employe manuellement
     * @param employeRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(EmployeRepository employeRepository,
                                        AdresseRepository adresseRepository,
                                        CoordonneesRepository coordonneesRepository,
                                        FeuilleTempsRepository feuilleTempsRepository,
                                        HoraireRepository horaireRepository) {
        return args -> {
            // Adresse
            Adresse adresse = new Adresse();
            adresse.setRue("564 Rue Orioles");
            adresse.setVille("Longueuil");
            adresse.setProvince(Adresse.Province.QUEBEC);
            adresse.setCodePostal("J4J1T3");
            adresse = adresseRepository.save(adresse);

            // Coordonnees
            Coordonnees coordonnees = new Coordonnees();
            coordonnees.setCourriel("davidvilla75@gmail.com");
            coordonnees.setNoTelephone("5149950723");
            coordonnees.setAdresse(adresse);
            coordonnees = coordonneesRepository.save(coordonnees);

            // Infos de base de l'employé
            Employe employeDeBase = new Employe();
            employeDeBase.setPrenom("Jean");
            employeDeBase.setNom("Bonin");
            employeDeBase.setFonction(Employe.Fonction.ADJOINT);
            employeDeBase.setMotDePasse("abc123");
            employeDeBase.setNomUtilisateur("Premier");
            employeDeBase.setTauxHoraire(0d);
            employeDeBase.setCoordonnees(coordonnees);

            // Horaire de la semaine
            Horaire horaire = new Horaire();
            horaire.setDateAssignation(LocalDate.now());
            horaire.setNombreHeures(25d);
            List<QuartTravail> horaireList = new ArrayList<>(7);
            // Quart de travail pour l'horaire
            QuartTravail quartTravail = new QuartTravail();
            quartTravail.setJour(DayOfWeek.MONDAY);
            quartTravail.setNombreHeures(7);
            quartTravail.setHeureDebut(LocalTime.of(8, 0));
            quartTravail.setDebutRepas(LocalTime.of(12, 0));
            quartTravail.setFinRepas(LocalTime.of(13, 0));
            quartTravail.setHeureFin(LocalTime.of(16, 0));
            horaireList.add(quartTravail);
            horaire.setListeQuartsPrevus(horaireList);
            horaire = horaireRepository.save(horaire);
            // Feuille de temps
            FeuilleTemps feuilleTemps = new FeuilleTemps();
            // Quart de travail pour la feuille de temps
            List<QuartTravail> feuilleTempsList = new ArrayList<>(7);
            QuartTravail quartTravaille = new QuartTravail();
            quartTravaille.setJour(DayOfWeek.MONDAY);
            quartTravaille.setNombreHeures(7);
            quartTravaille.setHeureDebut(LocalTime.of(8, 0));
            quartTravaille.setDebutRepas(LocalTime.of(12, 0));
            quartTravaille.setFinRepas(LocalTime.of(13, 0));
            quartTravaille.setHeureFin(LocalTime.of(16, 0));
            feuilleTempsList.add(quartTravail);
            feuilleTemps.setNombreHeuresTravaillees(7);
            feuilleTemps.setNombreHeureSupplementaire(0);
            feuilleTemps.setListeQuartsTravailles(feuilleTempsList);
            feuilleTemps = feuilleTempsRepository.save(feuilleTemps);
            // Ajouter à l'employé
            employeDeBase.setHoraire(horaire);
            employeDeBase.setFeuilleTemps(feuilleTemps);
            // Sauvegarder
            employeRepository.save(employeDeBase);
        };
    }
}
