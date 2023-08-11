package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Coordonnees;
import com.gestionemploye.GestionEmploye.Employe;
import com.gestionemploye.Mail.EmailSender;
import com.gestionemploye.Mail.EmailService;
import com.gestionemploye.Repository.CoordonneesRepository;
import com.gestionemploye.Repository.EmployeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.Random;

@Controller
public class OublieController {

    private final EmployeRepository employeRepository;
    private final CoordonneesRepository coordonneesRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private static final int LONGUEUR_MOT_DE_PASSE = 10;

    @Autowired
    public OublieController(EmployeRepository employeRepository, CoordonneesRepository coordonneesRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.employeRepository = employeRepository;
        this.coordonneesRepository = coordonneesRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/recuperer-mdp")
    public String gestionIdentifiantOublies(@RequestParam("courriel") String courriel) {
        Coordonnees coordonnees = (coordonneesRepository.
                findCoordonneesByCourriel(courriel).orElse(null));
        if (coordonnees != null) {
           Employe employe = employeRepository.findByCoordonnees(coordonnees)
                   .orElse(null);
           if (employe != null) {
               String motDePasseRaw = genererMotDePasse();
               String motDePasseCrypte = passwordEncoder.encode(motDePasseRaw);
               employe.setMotDePasse(motDePasseCrypte);
               employeRepository.save(employe);
               // Ajouter le code pour envoyer le mot de passe au courriel récuperé
               emailService.envoyerCourriel(courriel, motDePasseRaw);
           }
        } else
            System.out.println("ERREUR");

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
