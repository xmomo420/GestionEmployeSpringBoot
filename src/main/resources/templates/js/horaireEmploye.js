function afficherFormulaire() {
    //
    const modifyButton = document.getElementById('modifyButton');
    const formulaire = document.getElementById('formulaire');
    const boutonConfirmer = document.getElementById('btnConfirmer');
    const boutonReset = document.getElementById('btnReset');
    const boutonAnnuler = document.getElementById('btnAnnuler');
    const titreForm = document.getElementById('titreForm');

    modifyButton.style.display = 'none'; // Cacher le bouton "Modifier"
    titreForm.style.display = 'block';
    formulaire.style.display = 'flex'; // Afficher le formulaire
    boutonConfirmer.style.display = 'block'; // Afficher le bouton submit
    boutonReset.style.display = 'block'; // Afficher le bouton reset
    boutonAnnuler.style.display = 'block'; // Afficher le bouton reset
}

function resetForm() {
    // Récupérer tous les champs du formulaire
    var form = document.getElementById("formulaire");
    var fields = form.getElementsByTagName("input");

    // Parcourir les champs et réinitialiser uniquement ceux qui ne sont pas cachés
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        if (field.style.display !== "none" && field.name !== "_csrf") {
            // Utiliser l'attribut data-initial-value pour réinitialiser le champ
            var initialValue = field.getAttribute("data-initial-value");
            console.log("Réinitialisation du champ:", field.name, "Valeur initiale:", initialValue);
            field.value = initialValue;
        }
    }
}

function annulerForm() {
    // Récupérer tous les champs du formulaire
    var form = document.getElementById("formulaire");
    var fields = form.getElementsByTagName("input");

    // Parcourir les champs et réinitialiser uniquement ceux qui ne sont pas cachés
    for (var i = 0; i < fields.length; i++) {
        var field = fields[i];
        if (field.style.display !== "none" && field.name !== "_csrf") {
            // Utiliser l'attribut data-initial-value pour réinitialiser le champ
            field.value = field.defaultValue;
        }
    }
}
