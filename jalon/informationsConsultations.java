package jalon;

import java.util.Scanner;

public class informationsConsultations {
    public static String saisirTypeConsultation(String[] types, String[] codes, double[] prix, Scanner scanner) {
        while (true) {
            try {
                afficherListeConsultations(types, codes, prix);

                System.out.print("\nEntrez le code du type de consultation souhaité : ");
                String saisie = scanner.nextLine().trim().toUpperCase();

                int index = trouverIndexCode(codes, saisie);
                if (index != -1) {
                    System.out.println("\nVous avez sélectionné : " + types[index]);
                    return codes[index];
                }

                System.out.println("Code invalide. Veuillez réessayer.");
            } catch (Exception e) {
                ExceptionHandler.gestionAutomatique(e);
                scanner.nextLine(); // Nettoyage de buffer
            }
        }
    }

    private static void afficherListeConsultations(String[] types, String[] codes, double[] prix) {
        System.out.println("\nType de consultation : ");
        for (int i = 0; i < types.length && i < codes.length && i < prix.length; i++) {
            System.out.printf("%s | Code : %s | %.2f EUR%n", types[i], codes[i], prix[i]);
        }
    }

    private static int trouverIndexCode(String[] codes, String saisie) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equalsIgnoreCase(saisie)) {
                return i;
            }
        }
        return -1;
    }
}
