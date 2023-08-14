package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.QuartTravail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartTravailRepository extends JpaRepository<QuartTravail, Long> {

}
