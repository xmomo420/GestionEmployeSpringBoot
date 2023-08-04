package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.Coordonnees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordonneesRepository extends JpaRepository<Coordonnees,
        Long> {
}
