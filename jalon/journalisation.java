package jalon;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Classe utilitaire pour journaliser les erreurs dans un fichier texte.
 */
public class journalisation {

    private static final String FICHIER_LOG = "log.txt";

    /**
     * Enregistre un message d’erreur dans le fichier de journalisation.
     * @param message le message à enregistrer
     */
    public static void enregistrerErreur(String message) {
        try (FileWriter fw = new FileWriter(FICHIER_LOG, true)) {
            String horodatage = LocalDateTime.now().toString();
            fw.write("[" + horodatage + "] " + message + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Erreur lors de l’écriture dans le journal : " + e.getMessage());
        }
    }

    /**
     * Efface complètement le contenu du fichier de log.
     */
    public static void viderJournal() {
        try (FileWriter fw = new FileWriter(FICHIER_LOG)) {
            fw.write(""); // vide le fichier
            System.out.println("Journal vidé avec succès.");
        } catch (IOException e) {
            System.out.println("Impossible de vider le journal : " + e.getMessage());
        }
    }

    /**
     * Ajoute une ligne de séparation visuelle dans le fichier de log.
     */
    public static void separateur() {
        enregistrerErreur("----------");
    }
}
