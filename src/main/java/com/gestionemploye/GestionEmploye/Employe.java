package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(
        name = "employe",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "nom_utilisateur_unique",
                        columnNames = "nom_utilisateur"
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employe implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "employe_sequence",
            sequenceName = "employe_sequence",
            allocationSize = 1
    )
    @Column(updatable = false)
    private Long identifiant;
    @Column(
            name = "nom_utilisateur",
            updatable = false,
            nullable = false
    )
    private String nomUtilisateur;
    @Column(nullable = false)
    private String motDePasse;
    @Enumerated(EnumType.STRING)
    private Fonction fonction;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;
    @OneToOne
    @JoinColumn(
            name = "coordonnees_id",
            foreignKey = @ForeignKey(name = "reference_table_coordonnees")
    )
    private Coordonnees coordonnees;
    @Column(nullable = false)
    private double tauxHoraire;

    @OneToOne
    @JoinColumn(
            name = "horaire_id",
            foreignKey = @ForeignKey(name = "reference_table_horaire")
    )
    private Horaire horaire;
    @OneToOne
    @JoinColumn(
            name = "feuille_temps_id",
            foreignKey = @ForeignKey(name = "reference_table_feuille_temps")
    )
    private FeuilleTemps feuilleTemps;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + fonction.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return nomUtilisateur;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public enum Fonction {
        ADJOINT,
        ASSOCIE,
        RH,
        GERANT
    }


        // Opérations sur la feuille de temps

    public boolean clockIn() {
            return false;
        }

    public boolean clockOut() {
            return false;
        }

    public boolean debutRepas() {
            return false;
        }

    public boolean finRepas() {
            return false;
        }


}

