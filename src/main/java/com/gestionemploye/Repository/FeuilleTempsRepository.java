package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.FeuilleTemps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeuilleTempsRepository  extends JpaRepository<FeuilleTemps,
        Long> {
}
