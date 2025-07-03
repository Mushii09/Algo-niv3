package jalon;

public class affichageUtiles {

    public static void afficherRecapitulatifTableau(String prenom, String nom, String date, String heure,
                                                    String type, String code, double prixInitial,
                                                    double prixFinal, String reference) {
        try {
            String[] libelles = {
                "Patient", "Date", "Heure",
                "Type de consultation", "Prix initial", "Prix final", "Code de référence"
            };

            String[] valeurs = {
                prenom + " " + nom,
                date,
                heure,
                type + " (" + code + ")",
                String.format("%.2f EUR", prixInitial),
                String.format("%.2f EUR", prixFinal),
                reference
            };

            int maxLibelle = 0;
            int maxValeur = 0;

            for (String l : libelles) {
                if (l.length() > maxLibelle) maxLibelle = l.length();
            }

            for (String v : valeurs) {
                if (v.length() > maxValeur) maxValeur = v.length();
            }

            int largeurTotale = maxLibelle + maxValeur + 7;
            String bordure = "+" + repeter("-", largeurTotale - 2) + "+";

            System.out.println();
            System.out.println(repeter("=", largeurTotale));
            centrerTexte("RÉCAPITULATIF DU RENDEZ-VOUS", largeurTotale);
            System.out.println(repeter("=", largeurTotale));
            System.out.println(bordure);

            for (int i = 0; i < libelles.length; i++) {
                System.out.printf("| %-" + maxLibelle + "s | %-" + maxValeur + "s |\n", libelles[i], valeurs[i]);
            }

            System.out.println(bordure);

        } catch (Exception e) {
            ExceptionHandler.gestionAutomatique(e);
        }

    }

    public static String repeter(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void centrerTexte(String texte, int largeur) {
        int padding = Math.max((largeur - texte.length()) / 2, 0);
        System.out.println(repeter(" ", padding) + texte);
    }
}
