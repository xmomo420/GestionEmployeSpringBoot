package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional<Employe> findByNomUtilisateur(String nomUtilisateur);
    Optional<Employe> findByCoordonnees(Coordonnees coordonnees);
    Optional<List<Employe>> findAllByFonction(Fonction fonction);
    Optional<Employe> findByHoraire(Horaire horaire);
    Optional<Employe> findByFeuilleTemps(FeuilleTemps feuilleTemps);
}
