package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuartTravail implements Comparable<QuartTravail>{
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @SequenceGenerator(
            name = "quart_travail_sequence",
            sequenceName = "quart_travail_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private DayOfWeek jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private LocalTime debutRepas;
    private LocalTime finRepas;
    private double nombreHeures;

    // Gerer la relation avec FeuilleTemps
    @ManyToOne
    @JoinColumn(
            name = "feuille_temps_id",
            foreignKey = @ForeignKey(name = "reference_table_feuille_temps")
    )
    private FeuilleTemps feuilleTemps;

    // Gerer la relation avec Horaire
    @ManyToOne
    @JoinColumn(
            name = "horaire_id",
            foreignKey = @ForeignKey(name = "reference_table_horaire")
    )
    private Horaire horaire;

    @Override
    public int compareTo(QuartTravail autreQuart) {
        return this.jour.compareTo(autreQuart.jour);
    }

    /*
    // Gerer la relation avec Disponibilites
    @ManyToOne
    @JoinColumn(
            name = "dispos_id",
            foreignKey = @ForeignKey(name = "reference_table_dispos")
    )
    private Disponibilites dispos;
     */
}
