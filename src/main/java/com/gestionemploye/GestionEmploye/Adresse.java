package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Adresse {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @SequenceGenerator(
            name = "adresse_sequence",
            sequenceName = "adresse_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long id;
    private String rue;
    private String ville;
    @Enumerated(EnumType.STRING)
    private Province province;
    private String codePostal;

    public enum Province {
        QUEBEC,
        ONTARIO,
        ALBERTA,
        MANITOBA,
        SASKATCHEWAN,
        COLOMBIE_BRITANNIQUE,
        ILE_PRINCE_EDOUARD,
        NOUVELLE_ECOSSE,
        TERRE_NEUVE_LABRADOR,
        NOUVEAU_BRUNSWICK
    }
}
