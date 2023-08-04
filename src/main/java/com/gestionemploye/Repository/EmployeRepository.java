package com.gestionemploye.Repository;

import com.gestionemploye.GestionEmploye.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, String> {

}
