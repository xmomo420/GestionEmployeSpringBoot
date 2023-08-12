package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(
        name = "coordonnees",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "employe_courriel_unique",
                columnNames = "courriel"
        )
    }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Coordonnees {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @SequenceGenerator(
            name = "coordonnees_sequence",
            sequenceName = "coordonnees_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long id;
    private String noTelephone;
    @Column(name = "courriel")
    private String courriel;
    @OneToOne
    @JoinColumn(
            name = "adresse_id",
            foreignKey = @ForeignKey(name = "reference_table_adresse")
    )
    private Adresse adresse;

}