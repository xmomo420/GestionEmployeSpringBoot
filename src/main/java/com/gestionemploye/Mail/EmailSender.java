package com.gestionemploye.Mail;

public interface EmailSender {
    void envoyerCourriel(String destinataire, String message);
}
