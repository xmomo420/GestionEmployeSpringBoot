package com.gestionemploye.Controllers;

import com.gestionemploye.GestionEmploye.Adresse;
import com.gestionemploye.Repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class AjoutEmployeController {

    private final EmployeRepository employeRepository;

    @Autowired
    public AjoutEmployeController(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @GetMapping("/ajouter-employe")
    public String ajouterProvinceFormulaire(Model model) {
        List<String> provinces = Arrays.asList("Québec", "Ontario", "Alberta", "Manitoba",
                "Saskatchewan", "Colombie-Britannique", "Île-du-Prince-Édouard",
                "Nouveau-Brunswick", "Nouvelle-Écosse", "Terre-Neuve-et-Labrador"
                );
        model.addAttribute("provinces", provinces);
        return "/ajouter-employe";
    }

    @PostMapping("ajouter-employe/confirmer")
    public String ajouterEmploye(
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
            @RequestParam("prenom") String prenom,
    ) {

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
}
