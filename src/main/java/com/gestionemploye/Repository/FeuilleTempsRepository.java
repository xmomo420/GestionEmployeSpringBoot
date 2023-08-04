package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.FeuilleTemps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeuilleTempsRepository  extends JpaRepository<FeuilleTemps,
        Long> {
}
