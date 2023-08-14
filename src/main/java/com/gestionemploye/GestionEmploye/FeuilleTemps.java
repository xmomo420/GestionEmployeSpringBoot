package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;

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
}