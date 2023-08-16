package com.gestionemploye.GestionEmploye;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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
    @Column(nullable = false)
    private Fonction fonction;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "coordonnees_id",
            foreignKey = @ForeignKey(name = "reference_table_coordonnees")
    )
    private Coordonnees coordonnees;
    @Column(nullable = false)
    private double tauxHoraire;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "horaire_id",
            foreignKey = @ForeignKey(name = "reference_table_horaire")
    )
    private Horaire horaire;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "feuille_temps_id",
            foreignKey = @ForeignKey(name = "reference_table_feuille_temps")
    )
    private FeuilleTemps feuilleTemps;

    /*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "dispos_id",
            foreignKey = @ForeignKey(name = "reference_table_dispos")
    )
    private Disponibilités dispos;




     */

    // Implémentation de l'interface UserDetails
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

    public String genererNomUtilisateur() {
        Random random = new Random();
        String prefix;
        if (nom.length() >= 3)
            prefix = nom.substring(0, 2);
        else
            prefix = nom.charAt(0) + random.nextInt(100) + "";
        return prefix + prenom.charAt(0) + random.nextInt(1000);
    }

    public void ajouterHoraireFeuilleTempsVide() {
        // Horaire
        horaire = new Horaire();
        horaire.setListeQuartsPrevus(creerQuartVides());
        horaire.setNombreHeures(0d);
        horaire.setDateAssignation(LocalDate.now());
        // Feuille de temps
        feuilleTemps = new FeuilleTemps();
        feuilleTemps.setListeQuartsTravailles(creerQuartVides());
        feuilleTemps.setNombreHeureSupplementaire(0);
        feuilleTemps.setNombreHeuresTravaillees(0);
    }

    private static List<QuartTravail> creerQuartVides() {
        List<QuartTravail> quartTravails = new ArrayList<>(7);
        QuartTravail quartTravailLundi = new QuartTravail();
        quartTravailLundi.setJour(DayOfWeek.MONDAY);
        QuartTravail quartTravailMardi = new QuartTravail();
        quartTravailMardi.setJour(DayOfWeek.TUESDAY);
        QuartTravail quartTravailMercredi = new QuartTravail();
        quartTravailMercredi.setJour(DayOfWeek.WEDNESDAY);
        QuartTravail quartTravailJeudi = new QuartTravail();
        quartTravailJeudi.setJour(DayOfWeek.THURSDAY);
        QuartTravail quartTravailVendredi = new QuartTravail();
        quartTravailVendredi.setJour(DayOfWeek.FRIDAY);
        QuartTravail quartTravailSamedi = new QuartTravail();
        quartTravailSamedi.setJour(DayOfWeek.SATURDAY);
        QuartTravail quartTravailDimanche = new QuartTravail();
        quartTravailDimanche.setJour(DayOfWeek.SUNDAY);
        quartTravails.add(quartTravailLundi);
        quartTravails.add(quartTravailMardi);
        quartTravails.add(quartTravailMercredi);
        quartTravails.add(quartTravailJeudi);
        quartTravails.add(quartTravailVendredi);
        quartTravails.add(quartTravailSamedi);
        quartTravails.add(quartTravailDimanche);
        return quartTravails;
    }

}

