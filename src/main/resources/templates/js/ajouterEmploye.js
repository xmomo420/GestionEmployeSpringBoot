document.addEventListener('DOMContentLoaded', function() {
    const motDePasse = document.getElementById('motDePasse');
    const confirmationMotDePasse = document.getElementById('confirmationMotDePasse');
    const formulaire = document.getElementById('formulaire');

    confirmationMotDePasse.addEventListener('input', function() {
        if (confirmationMotDePasse.value !== motDePasse.value) {
            confirmationMotDePasse.setCustomValidity("Ne s'affiche jamais");
        } else {
            confirmationMotDePasse.setCustomValidity("");
        }
    });

    formulaire.addEventListener('submit', function(event) {
        if (!formulaire.checkValidity()) {
            event.preventDefault();
            formulaire.classList.add('was-validated');
        }
    });
});
