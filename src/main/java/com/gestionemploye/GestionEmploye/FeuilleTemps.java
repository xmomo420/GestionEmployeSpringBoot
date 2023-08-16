package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeuilleTemps {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @SequenceGenerator(
            name = "feuille_temps-sequence",
            sequenceName = "feuille_temps_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long feuilleTempsId;
    @OneToMany(
            mappedBy = "feuilleTemps"
    )
    private List<QuartTravail> listeQuartsTravailles = new ArrayList<>(7);
    private double nombreHeuresTravaillees;
    // Propore à FeuilleTemps
    private double nombreHeureSupplementaire;

    // Opérations sur la feuille de temps
    public void clockIn() {
        LocalDateTime dateTime = LocalDateTime.now();
        DayOfWeek jour = dateTime.getDayOfWeek();
        // Récuperer le bon jour de la semaine
        for (QuartTravail quart : listeQuartsTravailles)
            if (quart.getJour() == jour)
                quart.setHeureDebut(dateTime.toLocalTime());
    }

    public void clockOut() {
        LocalDateTime dateTime = LocalDateTime.now();
        DayOfWeek jour = dateTime.getDayOfWeek();
        // Récuperer le bon jour de la semaine
        for (QuartTravail quart : listeQuartsTravailles)
            if (quart.getJour() == jour)
                quart.setHeureFin(dateTime.toLocalTime());
    }

    public void debutRepas() {
        LocalDateTime dateTime = LocalDateTime.now();
        DayOfWeek jour = dateTime.getDayOfWeek();
        // Récuperer le bon jour de la semaine
        for (QuartTravail quart : listeQuartsTravailles)
            if (quart.getJour() == jour)
                quart.setDebutRepas(dateTime.toLocalTime());
    }

    public void finRepas() {
        LocalDateTime dateTime = LocalDateTime.now();
        DayOfWeek jour = dateTime.getDayOfWeek();
        // Récuperer le bon jour de la semaine
        for (QuartTravail quart : listeQuartsTravailles)
            if (quart.getJour() == jour)
                quart.setFinRepas(dateTime.toLocalTime());
    }
}