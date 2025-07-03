package jalon;

/**
 * Classe utilitaire pour la gestion centralisée des exceptions
 * Fournit des méthodes pour afficher des messages d'erreur standardisés
 */
public class ExceptionHandler {

    /**
     * Affiche un message d'erreur avec le type d'exception et un contexte numérique
     * @param e l'exception capturée
     * @param contexte le nombre maximum autorisé (0 si aucun)
     */
    public static void afficherErreur(Exception e, int contexte) {
        System.out.println("Erreur : " + e.getClass().getSimpleName() + " - Entrée invalide.");
        if (contexte > 0) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + contexte + ".");
        }
        journalisation.enregistrerErreur("Erreur : " + e.getClass().getSimpleName() + " | Contexte max : " + contexte);
    }

    /**
     * Affiche un message d'erreur avec contexte personnalisé
     * @param e l'exception capturée
     * @param messageContexte message complémentaire expliquant le contexte
     */
    public static void afficherErreur(Exception e, String messageContexte) {
        System.out.println("Erreur : " + e.getClass().getSimpleName());
        System.out.println(messageContexte);
        journalisation.enregistrerErreur("Erreur : " + e.getClass().getSimpleName() + " | " + messageContexte);
    }

    /**
     * Affiche une erreur générique avec son message
     * @param e l'exception capturée
     */
    public static void afficherErreur(Exception e) {
        System.out.println("Erreur : " + e.getClass().getSimpleName() + " - " + e.getMessage());
        journalisation.enregistrerErreur("Erreur : " + e.getClass().getSimpleName() + " - " + e.getMessage());
    }

    /**
     * Affichage spécialisé pour les erreurs de format de date/heure
     */
    public static void afficherErreurFormatDate() {
        String message = "Format de date/heure invalide. Utilisez le format : jj/MM/aaaa pour la date et HH:mm pour l'heure.";
        System.out.println(message);
        journalisation.enregistrerErreur("Erreur de format date/heure : " + message);
    }

    /**
     * Affichage spécialisé pour les erreurs de saisie d'âge
     */
    public static void afficherErreurAge() {
        String message = "Âge invalide. Veuillez entrer un âge valide (nombre entier positif).";
        System.out.println(message);
        journalisation.enregistrerErreur("Erreur d'âge : " + message);
    }

    /**
     * Affichage spécialisé pour les codes de consultation invalides
     */
    public static void afficherErreurCodeConsultation() {
        String message = "Code de consultation invalide. Veuillez choisir un code parmi ceux proposés.";
        System.out.println(message);
        journalisation.enregistrerErreur("Erreur code consultation : " + message);
    }
    public static void gestionAutomatique(Exception e) {
    String type = e.getClass().getSimpleName();

    switch (type) {
        case "NumberFormatException":
            System.out.println("Entrée numérique invalide. Veuillez saisir un nombre entier valide.");
            break;
        case "ArrayIndexOutOfBoundsException":
            System.out.println("Indice hors limites dans un tableau. Vérifiez les tailles des structures.");
            break;
        case "NullPointerException":
            System.out.println("Une valeur attendue est vide. Assurez-vous que toutes les données sont renseignées.");
            break;
        case "DateTimeParseException":
            afficherErreurFormatDate();
            break;
        case "InputMismatchException":
            System.out.println("Le type de donnée saisi ne correspond pas. Vérifiez vos entrées.");
            break;
        case "IOException":
            System.out.println("Erreur d’entrée/sortie. Impossible d’accéder au fichier ou flux.");
            break;
        default:
            System.out.println("Une erreur est survenue : " + type + " - " + e.getMessage());
    }

    // On journalise toujours
    journalisation.enregistrerErreur("Erreur détectée automatiquement (" + type + ") : " + e.getMessage());
}

}
