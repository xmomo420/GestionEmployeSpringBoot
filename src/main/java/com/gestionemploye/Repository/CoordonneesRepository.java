package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.Coordonnees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordonneesRepository extends JpaRepository<Coordonnees,Long> {
    Optional<Coordonnees> findCoordonneesByCourriel(String courriel);
}
