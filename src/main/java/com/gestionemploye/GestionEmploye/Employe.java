package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.PrivilegedAction;
import java.util.Collection;

@Entity
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
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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

