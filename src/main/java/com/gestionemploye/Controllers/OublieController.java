package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Coordonnees;
import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.Mail.EmailService;
import com.gestionemploye.Repository.CoordonneesRepository;
import com.gestionemploye.Repository.EmployeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Random;

@Controller
public class OublieController {

    private final EmployeRepository employeRepository;
    private final CoordonneesRepository coordonneesRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final int LONGUEUR_MOT_DE_PASSE = 10;
    private final EmailService emailService;

    @Autowired
    public OublieController(EmployeRepository employeRepository, CoordonneesRepository coordonneesRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.employeRepository = employeRepository;
        this.coordonneesRepository = coordonneesRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/oubli/recuperer-mdp")
    public String gestionIdentifiantOublies(@RequestParam("courriel") String courriel, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        Coordonnees coordonnees = (coordonneesRepository.
                findCoordonneesByCourriel(courriel).orElse(null));
        if (coordonnees != null) {
           Employe employe = employeRepository.findByCoordonnees(coordonnees)
                   .orElse(null);
           if (employe != null) {
               String motDePasseRaw = genererMotDePasse();
               //System.out.println("MDP : " + motDePasseRaw);
               String motDePasseCrypte = passwordEncoder.encode(motDePasseRaw);
               employe.setMotDePasse(motDePasseCrypte);
               employeRepository.save(employe);
               // Afficher que le mot de passe a été changé avec succès
               final String MSG_SUCCES = "Le mot de passe a été reinitialisé avec succès.\n" +
                       "Un courriel contenant le mot de passe a été envoyé à l'adresse : " +
                       courriel + ".\nVous devez maintenant vous connecter avec votre nouveau mot de passe en cliquant sur" +
                       " le bouton ci-dessous.";
               redirectAttributes.addFlashAttribute("succes", MSG_SUCCES);
               // Envoyer le mot de passe au courriel récuperé
               String messageCourriel =
                       "Voici votre nom d'utilisateur : " + employe.getNomUtilisateur() +
                       "\nVoici votre nouveau mot de passe : " + motDePasseRaw;
               String objetCourriel = "Récuperation identifiants";
               emailService.envoyerCourriel(courriel, objetCourriel, messageCourriel);
               // Déconnecter l'utilisateur
               Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
               SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
               logoutHandler.logout(request, response, authentication);
           }
        } else {
            final String MSG_ERR = "Aucun utilisateur avec le courriel : " + courriel + " n'a pu être trouvé.";
            redirectAttributes.addFlashAttribute("error", MSG_ERR);
        }
        return "redirect:/oubli";
    }

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    private static String genererMotDePasse() {
        StringBuilder motDePasse = new StringBuilder(LONGUEUR_MOT_DE_PASSE);
        for (int i = 0; i < LONGUEUR_MOT_DE_PASSE; i++) {
            int index = RANDOM.nextInt(CARACTERES.length());
            motDePasse.append(CARACTERES.charAt(index));
        }
        return motDePasse.toString();
    }

}
