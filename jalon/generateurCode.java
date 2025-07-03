package jalon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class generateurCode {

    public static String genererCodeReference(String prenom, String nom, LocalDateTime dateHeure, String codeConsultation) {
        try {
            String initiales = extraireInitiales(prenom, nom);
            String dateStr = dateHeure.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String heureStr = dateHeure.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm"));
            return initiales + dateStr + heureStr + codeConsultation.toUpperCase();
        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du code de référence : " + e.getMessage());
            return "XX0000000000ERR"; // code de repli en cas d'erreur
        }
    }

    private static String extraireInitiales(String prenom, String nom) {
        StringBuilder initiales = new StringBuilder();

        try {
            for (String part : prenom.trim().toUpperCase().split("\\s+")) {
                if (!part.isEmpty()) initiales.append(part.charAt(0));
            }

            for (String part : nom.trim().toUpperCase().split("\\s+")) {
                if (!part.isEmpty()) initiales.append(part.charAt(0));
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de l'extraction des initiales : " + e.getMessage());
        }

        if (initiales.length() == 0) {
            return "XX"; // valeur de repli
        }

        return initiales.toString();
    }
}
