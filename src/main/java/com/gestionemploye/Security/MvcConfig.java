package com.gestionemploye.Security;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("html/login");
        registry.addViewController("/oubli").setViewName("html/oubli");
        registry.addViewController("/").setViewName("html/menu");
        registry.addViewController("/gestion-employe/ajouter-employe").setViewName("html/adjoint/ajouterEmploye");
        registry.addViewController("/gestion-employe/afficher-employes").setViewName("html/adjoint/afficherEmployes");
        registry.addViewController("/gestion-employe/afficher-employes/horaire-employe").setViewName("html/adjoint/horaireEmploye");
        registry.addViewController("/gestion-employe").setViewName("html/adjoint/gestionEmploye");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**")
                .addResourceLocations("classpath:/templates/html/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/templates/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/templates/js/");
    }

}
