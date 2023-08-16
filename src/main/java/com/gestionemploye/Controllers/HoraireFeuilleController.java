package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.GestionEmploye.FeuilleTemps;
import com.gestionemploye.GestionEmploye.Horaire;
import com.gestionemploye.GestionEmploye.QuartTravail;
import com.gestionemploye.Repository.EmployeRepository;
import com.gestionemploye.Repository.FeuilleTempsRepository;
import com.gestionemploye.Repository.HoraireRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HoraireFeuilleController {
    private final EmployeRepository employeRepository;
    private final HoraireRepository horaireRepository;
    private final FeuilleTempsRepository feuilleTempsRepository;

    @Autowired
    public HoraireFeuilleController(EmployeRepository employeRepository, HoraireRepository horaireRepository, FeuilleTempsRepository feuilleTempsRepository) {
        this.employeRepository = employeRepository;
        this.horaireRepository = horaireRepository;
        this.feuilleTempsRepository = feuilleTempsRepository;
    }

    @RequestMapping("/horaire")
    public String afficherHoraire(HttpSession session, Model model) {
        Employe employe = (Employe) session.getAttribute("employe");
        Horaire horaire = horaireRepository.findById(employe.getHoraire().getId()).orElse(null);
        model.addAttribute("horaire", horaire);
        model.addAttribute("nom", employe.getPrenom() + " " + employe.getNom());
        return "html/horaire";
    }

    @GetMapping("feuille-temps")
    public String afficherFeuilleTemps(HttpSession session, Model model) {
        Employe employe = (Employe) session.getAttribute("employe");
        FeuilleTemps feuilleTemps = feuilleTempsRepository.findById(employe.getFeuilleTemps().getFeuilleTempsId()).orElse(null);
        model.addAttribute("feuille", feuilleTemps);
        model.addAttribute("nom", employe.getPrenom() + " " + employe.getNom());
        return "html/feuilleTemps";
    }

    @PostMapping("/feuille-temps")
    public String effectuerAction(@RequestParam("action") int action, Model model, HttpSession session) {
        Employe employe = (Employe) session.getAttribute("employe");
        FeuilleTemps feuilleTemps = feuilleTempsRepository.findById(employe.getFeuilleTemps().getFeuilleTempsId()).orElse(null);
        // Pour tester
        assert feuilleTemps != null;
        switch (action) {
            case 1 -> feuilleTemps.clockIn();
            case 2 -> feuilleTemps.debutRepas();
            case 3 -> feuilleTemps.finRepas();
            case 4 -> feuilleTemps.clockOut();
            default ->
                    throw new IllegalArgumentException("Impossible d'effectuer cette action");
        }
        List<QuartTravail> nouveauxQuarts = new ArrayList<>(feuilleTemps.getListeQuartsTravailles());
        feuilleTemps.setListeQuartsTravailles(nouveauxQuarts);
        feuilleTempsRepository.save(feuilleTemps);
        return "redirect:/feuille-temps";
    }

    // Ajouter mapping pour les dispos


}
