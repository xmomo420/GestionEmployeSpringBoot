package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.GestionEmploye.Fonction;
import com.gestionemploye.GestionEmploye.Horaire;
import com.gestionemploye.GestionEmploye.QuartTravail;
import com.gestionemploye.Repository.EmployeRepository;
import com.gestionemploye.Repository.HoraireRepository;
import com.gestionemploye.Repository.QuartTravailRepository;
import com.gestionemploye.Service.EmployeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class AfficherEmployesController {

    private final EmployeRepository employeRepository;
    private final EmployeService employeService;
    private final HoraireRepository horaireRepository;
    private final QuartTravailRepository quartTravailRepository;



    @Autowired
    public AfficherEmployesController(EmployeRepository employeRepository, EmployeService employeService, HoraireRepository horaireRepository, QuartTravailRepository quartTravailRepository) {
        this.employeRepository = employeRepository;
        this.employeService = employeService;
        this.horaireRepository = horaireRepository;
        this.quartTravailRepository = quartTravailRepository;
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

    @RequestMapping("/gestion-employe/afficher-employes/horaire-employe")
    public String voirHoraireEmploye(@RequestParam("idHoraire") Long idHoraire,
                                     @RequestParam("nomEmploye") String nom ,Model model) {
        Horaire horaireVue = horaireRepository.findById(idHoraire).orElse(null);
        assert horaireVue != null;
        List<QuartTravail> quartTries = horaireVue.getListeQuartsPrevus() // Trier au cas où
                .stream()
                .sorted(Comparator.comparing(QuartTravail::getJour))
                .collect(Collectors.toList());
        horaireVue.setListeQuartsPrevus(quartTries);
        horaireRepository.save(horaireVue);
        final String MSG_HORAIRE_NULL = "Un problème est survenu lors de la récupération de l'horaire";
        if (nom != null) {
            model.addAttribute("horaire", horaireVue);
            model.addAttribute("nom", nom);
        }
        else {
            model.addAttribute("horaire_null", MSG_HORAIRE_NULL);
        }
        return "html/adjoint/horaireEmploye";
    }

    private static List<QuartTravail> copierInfosQuartModifies(List<QuartTravail> quartsFormulaire, List<QuartTravail> quartsBD) {
        ArrayList<QuartTravail> quartsBDMAJ = new ArrayList<>(quartsBD);
        for (int i = 0; i < quartsFormulaire.size(); i++) {
            QuartTravail quartTravail = quartsBD.get(i);
            quartTravail.setHeureDebut(quartsFormulaire.get(i).getHeureDebut());
            quartTravail.setDebutRepas(quartsFormulaire.get(i).getDebutRepas());
            quartTravail.setFinRepas(quartsFormulaire.get(i).getFinRepas());
            quartTravail.setHeureFin(quartsFormulaire.get(i).getHeureFin());
            // Ajouter une fonction pour calculer le nombre d'heure dans le quart -> quartTravail.setNombreHeure(quartTravail.calculerHures())
            quartsBDMAJ.set(i, quartTravail);
        }
        return quartsBDMAJ;
    }

    @PostMapping("/gestion-employe/afficher-employes/horaire-employe/modifier")
    public String modifierHoraire(@ModelAttribute("horaire") Horaire horaire,
                                  @RequestParam("idHoraire")Long idHoraire,
                                  RedirectAttributes attributes) {
        // Sauvegarder dans la BD
        Horaire nouvelHoraire = horaireRepository.findById(idHoraire).orElse(null);
        assert nouvelHoraire != null;
        // Trier les quarts de travail, car ils sont mélangés dans le formulaire.
        List<QuartTravail> quartsFormulaire = horaire.getListeQuartsPrevus()
                .stream()
                .sorted(Comparator.comparing(QuartTravail::getJour))
                .collect(Collectors.toList());
        List<QuartTravail> nouveauxQuarts = nouvelHoraire.getListeQuartsPrevus()
                .stream()
                .sorted(Comparator.comparing(QuartTravail::getJour))
                .collect(Collectors.toList());
        nouveauxQuarts = copierInfosQuartModifies(quartsFormulaire, nouveauxQuarts);
        nouvelHoraire.setListeQuartsPrevus(nouveauxQuarts);
        // Sauvegarder l'horaire dans la BD
        horaireRepository.save(nouvelHoraire);
        // Ajouter les paramètres
        attributes.addAttribute("idHoraire", idHoraire); // idHoraire
        // Récuperer le nom de l'employé
        Employe employe = employeRepository.findByHoraire(nouvelHoraire).orElse(null);
        assert employe != null;
        String nomEmploye = employe.getPrenom() + " " + employe.getNom();
        attributes.addAttribute("nomEmploye", nomEmploye);
        return "redirect:/gestion-employe/afficher-employes/horaire-employe";
    }



}
