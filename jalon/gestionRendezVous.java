package jalon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jalon.affichageUtiles;

public class gestionRendezVous {

    private static List<String[]> listRendezVous = new ArrayList<>();

   public static void lancer(Scanner scanner, String[] types, String[] codes, double[] tarifs) {
    System.out.println("=== Prise de rendez-vous ===");

    // 1. Informations du patient
    String prenom = informationsPatients.saisirPrenom(scanner);
    String nom = informationsPatients.saisirNom(scanner);
    int age = informationsPatients.saisirAge(scanner);

    // 2. Choix du type de consultation
    String codeConsultation = informationsConsultations.saisirTypeConsultation(types, codes, tarifs, scanner);
    int index = trouverIndexCode(codes, codeConsultation);
    if (index == -1) {
        System.out.println("Code de consultation non reconnu.");
        return;
    }

    // 3. Saisie date/heure avec boucle de vérification
    LocalDateTime dateHeure = null;
    boolean valide = false;

    while (!valide) {
        dateHeure = validationDate.saisirDateHeure(scanner);

        if (!validationDate.estDateHeureValide(dateHeure)) {
            System.out.println("Horaire invalide. Réessayez.");
            continue;
        }

        if (estDejaReserve(dateHeure)) {
            System.out.println("Un autre rendez-vous est déjà prévu à cette date et heure.");
            System.out.print("Souhaitez-vous essayer 30 minutes plus tard ? (o/n) : ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
                return;
            }

            dateHeure = dateHeure.plusMinutes(30);
            if (!validationDate.estDateHeureValide(dateHeure)) {
                System.out.println("L'horaire suivant est également invalide.");
            } else if (estDejaReserve(dateHeure)) {
                System.out.println("Ce créneau aussi est déjà pris. Nouvelle tentative requise.");
            } else {
                valide = true;
            }
        } else {
            valide = true;
        }
    }

    // 4. Calcul du prix
    double prixInitial = tarifs[index];
    double prixFinal = calculateurPrix.calculerPrixFinal(index, tarifs, age);

    // 5. Génération du code de référence
    String reference = generateurCode.genererCodeReference(prenom, nom, dateHeure, codeConsultation);

    // 6. Confirmation de l'utilisateur
    System.out.print("Confirmez-vous la prise de rendez-vous ? (o/n) : ");
    if (!scanner.nextLine().trim().equalsIgnoreCase("o")) {
        System.out.println("Rendez-vous annulé.");
        return;
    }

    // 7. Affichage récapitulatif
    affichageUtiles.afficherRecapitulatifTableau(
        prenom, nom,
        dateHeure.toLocalDate().toString(),
        dateHeure.toLocalTime().toString(),
        types[index], codes[index],
        prixInitial, prixFinal,
        reference
    );

    // 8. Enregistrement du rendez-vous
    listRendezVous.add(new String[] {
        prenom, nom,
        dateHeure.toLocalDate().toString(),
        dateHeure.toLocalTime().toString(),
        types[index],
        codes[index],
        String.format("%.2f EUR", prixFinal),
        reference
    });
}


public static boolean estDejaReserve(LocalDateTime dateHeure) {
    String date = dateHeure.toLocalDate().toString();
    String heure = dateHeure.toLocalTime().toString();

    for (String[] rv : listRendezVous) {
        if (rv[2].equals(date) && rv[3].equals(heure)) {
            return true;
        }
    }

    return false;
}


    
    private static int trouverIndexCode(String[] codes, String codeSaisi) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equalsIgnoreCase(codeSaisi)) return i;
        }
        return -1;
    }

    public static void afficherHistoriqueRendezVous() {
    if (listRendezVous.isEmpty()) {
        System.out.println("Aucun rendez-vous enregistré.");
        return;
    }

    // Tri par date+heure (conversion à la volée)
    listRendezVous.sort((a, b) -> {
        LocalDateTime d1 = LocalDateTime.parse(a[2] + "T" + a[3]);
        LocalDateTime d2 = LocalDateTime.parse(b[2] + "T" + b[3]);
        return d1.compareTo(d2);
    });

    System.out.println("\n========= HISTORIQUE DES RENDEZ-VOUS =========");

    for (int i = 0; i < listRendezVous.size(); i++) {
        String[] rv = listRendezVous.get(i);
        System.out.printf("%n--- Rendez-vous %d ---\n", i + 1);
        System.out.println("Patient : " + rv[0] + " " + rv[1]);
        System.out.println("Date    : " + rv[2]);
        System.out.println("Heure   : " + rv[3]);
        System.out.println("Type    : " + rv[4] + " (" + rv[5] + ")");
        System.out.println("Prix    : " + rv[6]);
        System.out.println("Code de référence : " + rv[7]);
    }
}


    public static void supprimerRendezVousParReference(Scanner scanner) {
    if (listRendezVous.isEmpty()) {
        System.out.println("Aucun rendez-vous enregistré.");
        return;
    }

    boolean recommencer = true;

    while (recommencer) {
        System.out.print("Entrez les premières lettres du code de référence : ");
        String saisie = scanner.nextLine().trim().toUpperCase();

        // Filtrer les rendez-vous qui commencent par la saisie
        List<String[]> correspondants = new ArrayList<>();
        for (String[] rv : listRendezVous) {
            if (rv[7].startsWith(saisie)) {
                correspondants.add(rv);
            }
        }

        if (correspondants.isEmpty()) {
            System.out.println("Aucun code de référence ne commence par : " + saisie);
            System.out.print("Souhaitez-vous réessayer ? (o/n) : ");
            String rep = scanner.nextLine().trim();
            if (!rep.equalsIgnoreCase("o")) recommencer = false;
        } else {
            System.out.println("\nRendez-vous correspondants :");
            for (int i = 0; i < correspondants.size(); i++) {
                String[] rv = correspondants.get(i);
                System.out.printf("%d. %s - %s %s - %s à %s%n", i + 1, rv[7], rv[0], rv[1], rv[2], rv[3]);
            }

            System.out.print("Voulez-vous en supprimer un ? (o/n) : ");
            String confirm = scanner.nextLine().trim();

            if (confirm.equalsIgnoreCase("o")) {
                System.out.print("Entrez le numéro de la ligne à supprimer : ");
                try {
                    int choix = Integer.parseInt(scanner.nextLine().trim());
                    if (choix >= 1 && choix <= correspondants.size()) {
                        String[] cible = correspondants.get(choix - 1);
                        listRendezVous.remove(cible);
                        System.out.println("Rendez-vous supprimé avec succès.");
                        recommencer = false;
                    } else {
                        System.out.println("Numéro invalide.");
                    }
                } catch (Exception e) {
                    ExceptionHandler.gestionAutomatique(e);
                }

            } else {
                recommencer = false;
            }
        }
    }
}

    public static void modifierHoraireRendezVous(Scanner scanner) {
        if (listRendezVous.isEmpty()) {
            System.out.println("Aucun rendez-vous enregistré.");
            return;
        }

        boolean continuer = true;

        while (continuer) {
            System.out.print("Entrez les premières lettres du code de référence : ");
            String prefixe = scanner.nextLine().trim().toUpperCase();

            List<String[]> correspondants = new ArrayList<>();
            for (String[] rv : listRendezVous) {
                if (rv[7].startsWith(prefixe)) {
                    correspondants.add(rv);
                }
            }

            if (correspondants.isEmpty()) {
                System.out.println("Aucun rendez-vous correspondant.");
                System.out.print("Voulez-vous réessayer ? (o/n) : ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("o")) return;
            } else {
                for (int i = 0; i < correspondants.size(); i++) {
                    String[] rv = correspondants.get(i);
                    System.out.printf("%d. %s - %s %s - %s à %s%n",
                            i + 1, rv[7], rv[0], rv[1], rv[2], rv[3]);
                }

                System.out.print("Sélectionnez le numéro du rendez-vous à modifier : ");
                try {
                    int choix = Integer.parseInt(scanner.nextLine().trim());
                    if (choix < 1 || choix > correspondants.size()) {
                        System.out.println("Numéro invalide.");
                        return;
                    }

                    String[] rendezVous = correspondants.get(choix - 1);

                    // On redemande la date/heure jusqu'à ce qu'elle soit valide
                    LocalDateTime nouvelleDateHeure;
                    while (true) {
                        System.out.print("Nouvelle date (jj/MM/aaaa) : ");
                        String dateStr = scanner.nextLine().trim();
                        System.out.print("Nouvelle heure (HH:mm) : ");
                        String heureStr = scanner.nextLine().trim();

                        try {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                            LocalTime heure = LocalTime.parse(heureStr, heureFormatter);
                            nouvelleDateHeure = LocalDateTime.of(date, heure);

                            if (validationDate.estDateHeureValide(nouvelleDateHeure)) {
                                break;
                            } else {
                                // proposer horaire + 30 minutes
                                System.out.print("Souhaitez-vous essayer 30 minutes plus tard ? (o/n) : ");
                                String rep = scanner.nextLine().trim();
                                if (rep.equalsIgnoreCase("o")) {
                                    nouvelleDateHeure = nouvelleDateHeure.plusMinutes(30);
                                    if (validationDate.estDateHeureValide(nouvelleDateHeure)) {
                                        break;
                                    } else {
                                        System.out.println("L'heure suivante est également invalide.");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ExceptionHandler.gestionAutomatique(e);
                        }
                    }

                    // Mise à jour dans la liste
                    rendezVous[2] = nouvelleDateHeure.toLocalDate().toString(); // date
                    rendezVous[3] = nouvelleDateHeure.toLocalTime().toString(); // heure

                    // Recalcul du code de référence
                    String nouveauCode = generateurCode.genererCodeReference(
                        rendezVous[0],               // prénom
                        rendezVous[1],               // nom
                        nouvelleDateHeure,
                        rendezVous[5]                // code consultation
                    );
                    rendezVous[7] = nouveauCode;


                    System.out.println("Rendez-vous mis à jour avec succès.");
                    continuer = false;

                } catch (NumberFormatException e) {
                    System.out.println("Entrée non valide.");
                }
            }
        }
    }

    public static void rechercherRendezVous(Scanner scanner) {
    if (listRendezVous.isEmpty()) {
        System.out.println("Aucun rendez-vous enregistré.");
        return;
    }

    System.out.println("\n=== Recherche d’un rendez-vous ===");
    System.out.println("1. Par nom de patient");
    System.out.println("2. Par date (format : jj-MM-aaaa)");
    System.out.println("3. Par code de référence (entier ou partiel)");
    System.out.print("Votre choix : ");
    String choix = scanner.nextLine().trim();

    List<String[]> resultats = new ArrayList<>();

    switch (choix) {
        case "1":
            System.out.print("Entrez le nom du patient : ");
            String nomRecherche = scanner.nextLine().trim().toLowerCase();
            for (String[] rv : listRendezVous) {
                if (rv[1].toLowerCase().contains(nomRecherche)) {
                    resultats.add(rv);
                }
            }
            break;

        case "2":
    System.out.print("Entrez la date (jj-MM-aaaa) : ");
    String dateRecherche = scanner.nextLine().trim();

    try {
        // Conversion jj-MM-aaaa → yyyy-MM-dd pour comparer avec les rendez-vous enregistrés
        DateTimeFormatter saisieFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter interneFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(dateRecherche, saisieFormatter);
        String dateFormatStockee = date.format(interneFormatter); // aaaa-MM-jj

        for (String[] rv : listRendezVous) {
            if (rv[2].equals(dateFormatStockee)) {
                resultats.add(rv);
            }
        }
    } catch (Exception e) {
        ExceptionHandler.gestionAutomatique(e);
        return;
    }
    break;

        case "3":
            System.out.print("Entrez le code de référence (ou son début) : ");
            String codeRecherche = scanner.nextLine().trim().toUpperCase();
            for (String[] rv : listRendezVous) {
                if (rv[7].toUpperCase().startsWith(codeRecherche)) {
                    resultats.add(rv);
                }
            }
            break;

        default:
            System.out.println("Choix invalide.");
            return;
    }

    if (resultats.isEmpty()) {
        System.out.println("Aucun rendez-vous correspondant.");
    } else {
        System.out.println("\nRésultats trouvés :");
        for (String[] rv : resultats) {
            System.out.println("-------------------------------");
            System.out.println("Patient   : " + rv[0] + " " + rv[1]);
            System.out.println("Date      : " + rv[2]);
            System.out.println("Heure     : " + rv[3]);
            System.out.println("Type      : " + rv[4] + " (" + rv[5] + ")");
            System.out.println("Prix      : " + rv[6]);
            System.out.println("Référence : " + rv[7]);
        }
    }
}


}