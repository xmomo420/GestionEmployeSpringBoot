package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Adresse;
import com.gestionemploye.GestionEmploye.Coordonnees;
import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.GestionEmploye.Fonction;
import com.gestionemploye.Repository.CoordonneesRepository;
import com.gestionemploye.Repository.EmployeRepository;
import com.gestionemploye.Service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class AjoutEmployeController {

    private final EmployeRepository employeRepository;
    private final CoordonneesRepository coordonneesRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeService employeService;
    public static final String MSG_FORMAT_MDP =
            "Le mot de passe doit contenir au moins 8 caractères, y compris au moins une lettre majuscule, une lettre minuscule et un chiffre.";

    @Autowired
    public AjoutEmployeController(EmployeRepository employeRepository,
                                  CoordonneesRepository coordonneesRepository,
                                  PasswordEncoder passwordEncoder,
                                  EmployeService employeService) {
        this.employeRepository = employeRepository;
        this.coordonneesRepository = coordonneesRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeService = employeService;
    }

    @GetMapping("/gestion-employe/ajouter-employe")
    public String ajouterAttributs(Model model) {
        // Provinces
        List<String> provinces = Arrays.asList("Québec", "Ontario", "Alberta", "Manitoba",
                "Saskatchewan", "Colombie-Britannique", "Île-du-Prince-Édouard",
                "Nouveau-Brunswick", "Nouvelle-Écosse", "Terre-Neuve-et-Labrador"
                );
        model.addAttribute("provinces", provinces);
        // Fonctions
        List<String> fonctions = Arrays.asList("Adjoint", "Associé", "Gérant", "Ressources-humaines");
        model.addAttribute("fonctions", fonctions);

        return "html/adjoint/ajouterEmploye";
    }

    @PostMapping("/gestion-employe/ajouter-employe/confirmer")
    public String ajouterEmploye(
            @RequestParam("prenom") String prenom,
            @RequestParam("nom") String nom,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam("courriel") String courriel,
            @RequestParam("noTelephone") String noTelephone,
            @RequestParam("rue") String rue,
            @RequestParam("ville") String ville,
            @RequestParam("province") String province,
            @RequestParam("codePostal") String codePostal,
            @RequestParam("fonction") String fonction,
            @RequestParam("tauxHoraire") double tauxHoraire,
            RedirectAttributes redirectAttributes
    ) {
        // Confirmation courriel déja utilisé
        if (coordonneesRepository.findCoordonneesByCourriel(courriel).isPresent()) {
            final String MSG_COURRIEL = "L'adresse courriel : " + courriel + " est déja utilisée.";
            redirectAttributes.addFlashAttribute("erreur_courriel", MSG_COURRIEL);
        } else {
            // Création des objets pour l'employé
            Employe employe = new Employe();
            Adresse adresse = new Adresse();
            Coordonnees coordonnees = new Coordonnees();
            Adresse.Province _province = associerProvince(province);
            Fonction _fonction = associerFonction(fonction);
            // Modifier les attributs de Employe
            employe.setPrenom(prenom);
            employe.setNom(nom);
            employe.setFonction(_fonction);
            // Pour créer un nom d'utilisateur unique
            String nomUtilisateur = "";
            boolean nomUtilisateurValide = false;
            while (!nomUtilisateurValide) {
                nomUtilisateur = employe.genererNomUtilisateur();
                nomUtilisateurValide = employeRepository.findByNomUtilisateur(nomUtilisateur).isEmpty();
            }
            employe.setNomUtilisateur(nomUtilisateur);
            // Cryptage du mot de passe
            employe.setMotDePasse(passwordEncoder.encode(motDePasse));
            employe.setTauxHoraire(tauxHoraire);
            // Modifier l'adresse
            adresse.setRue(rue);
            adresse.setVille(ville);
            adresse.setProvince(_province);
            adresse.setCodePostal(codePostal);
            // Modifier les coordonnées
            coordonnees.setAdresse(adresse);
            coordonnees.setCourriel(courriel);
            coordonnees.setNoTelephone(noTelephone);
            // Ajouter les coordonnées à l'employé
            employe.setCoordonnees(coordonnees);
            // Ajouter l'horaire et la feuille de temps vide
            employe.ajouterHoraireFeuilleTempsVide();
            // Ajouter l'employé dans la base de données
            employeService.ajouterEmploye(employe);
            // Ajouter un attribut pour indiquer que l'employé a été ajouté avec succès
            final String MSG_SUCCES = "L'employé "  + prenom + " " + nom + " a été ajouté avec succès";
            redirectAttributes.addFlashAttribute("succes", MSG_SUCCES);
        }
        return "redirect:/gestion-employe/ajouter-employe";
    }

    private static Adresse.Province associerProvince(String provinceForm) {
        if (provinceForm.equals("Québec"))
            return Adresse.Province.QUEBEC;
        if (provinceForm.equals("Ontario"))
            return Adresse.Province.ONTARIO;
        if (provinceForm.equals("Alberta"))
            return Adresse.Province.ALBERTA;
        if (provinceForm.equals("Manitoba"))
            return Adresse.Province.MANITOBA;
        if (provinceForm.equals("Saskatchewan"))
            return Adresse.Province.SASKATCHEWAN;
        if (provinceForm.equals("Colombie-Britannique"))
            return Adresse.Province.COLOMBIE_BRITANNIQUE;
        if (provinceForm.equals("Île-du-Prince-Édouard"))
            return Adresse.Province.ILE_PRINCE_EDOUARD;
        if (provinceForm.equals("Nouveau-Brunswick"))
            return Adresse.Province.NOUVEAU_BRUNSWICK;
        if (provinceForm.equals("Nouvelle-Écosse"))
            return Adresse.Province.NOUVELLE_ECOSSE;
        if (provinceForm.equals("Terre-Neuve-et-Labrador"))
            return Adresse.Province.TERRE_NEUVE_LABRADOR;
        else
            return null;
    }

    private static Fonction associerFonction(String fonctionForm) {
        if (fonctionForm.equals("Adjoint"))
            return Fonction.ADJOINT;
        if (fonctionForm.equals("Associé"))
            return Fonction.ASSOCIE;
        if (fonctionForm.equals("Gérant"))
            return Fonction.GERANT;
        if (fonctionForm.equals("Ressources-humaines"))
            return Fonction.RH;
        else
            return null;
    }

}
