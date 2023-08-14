package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.GestionEmploye.Fonction;
import com.gestionemploye.Repository.EmployeRepository;
import com.gestionemploye.Service.EmployeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AfficherEmployesController {

    private final EmployeRepository employeRepository;
    private final EmployeService employeService;



    @Autowired
    public AfficherEmployesController(EmployeRepository employeRepository, EmployeService employeService) {
        this.employeRepository = employeRepository;
        this.employeService = employeService;
    }

    @GetMapping("/gestion-employe/afficher-employes")
    public String ajouterEmployes(HttpSession session, Model model) {
        Employe employe = (Employe) session.getAttribute("employe");
        if (employe != null) {
            List<Employe> employes = employeRepository.findAllByFonction(Fonction.ASSOCIE).orElse(new ArrayList<>()); // Ajouter tous les associés
            if (employe.getFonction() == Fonction.ADJOINT) { // Si la fonction de l'employé est ADJOINT
                employes.addAll(employeRepository.findAllByFonction(Fonction.GERANT).orElse(new ArrayList<>())); // Ajouter tous les gérants
            }
            model.addAttribute("employes", employes);
        } else
            model.addAttribute("erreur", "session.employe == null");
        return "html/adjoint/afficherEmployes";
    }


}
