package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.Coordonnees;
import com.gestionemploye.GestionEmploye.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional<Employe> findByNomUtilisateur(String nomUtilisateur);
    Optional<Employe> findByCoordonnees(Coordonnees coordonnees);
}
