package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Horaire {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @SequenceGenerator(
            name = "horaire_sequence",
            sequenceName = "horaire_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long id;
    @OneToMany(mappedBy = "horaire")
    private List<QuartTravail> listeQuartsPrevus = new ArrayList<>(7);
    private LocalDate dateAssignation;
    private double nombreHeures;

}
