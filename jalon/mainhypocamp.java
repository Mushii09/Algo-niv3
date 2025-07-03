package jalon;

import java.util.Scanner;

public class mainhypocamp {
    
        static Scanner scanner = new Scanner(System.in);

        static String[] types = {"Bilan de santé", "Cardiologie", "Vaccinations", "Certification médical", "Général", "Suivi médical"};
        static String[] codes = {"BS", "CD", "VC", "CM", "GN", "SM"};
        static double[] tarifs  = {120, 200, 0, 100, 70, 60};
    public static void main(String[] args) {

        
        

        while (true) { 
            afficherMenu();
            System.out.print("\nSélectionner une option : ");
            String choix = scanner.nextLine();
            try {
                int option = Integer.parseInt(choix);
                 if (choisirOption(option, scanner)) {
                    break; // Sortir de la boucle si l'utilisateur choisit de quitter
                }
                
                
            } catch (Exception e) {
                ExceptionHandler.gestionAutomatique(e);
            }
        }
        System.out.println("\nAu revoir !");
    } 


    public static void afficherMenu() {
        System.out.println("");

        System.out.println("-------------------------------");
        System.out.println("| Gestionnaire de Rendez-Vous |");
        System.out.println("-------------------------------");
        System.out.println("1. Ajouter un rendez-vous");
        System.out.println("2. Supprimer un rendez-vous");
        System.out.println("3. Modifier un rendez-vous");
        System.out.println("4. Rechercher un rendez-vous");
        System.out.println("5. Consulter la liste un rendez-vous");
        System.out.println("0. Quitter");           
        
    }

    public static boolean choisirOption(int option, Scanner scanner){
        switch (option) {
             case 1:
             gestionRendezVous.lancer(scanner, types, codes, tarifs);
                break;
            case 2:
            gestionRendezVous.supprimerRendezVousParReference(scanner);
                break;
            case 3:
            gestionRendezVous.modifierHoraireRendezVous(scanner);
                break;
            case 4:
            gestionRendezVous.rechercherRendezVous(scanner);
                break;
            case 5:
            gestionRendezVous.afficherHistoriqueRendezVous();
                break;
            case 0:
                return true; // Quitter le programme

            default:
                System.out.println("\nVeuillez entrer un nombre entre 0 et 5");
                break;
        }
        return false;
    }

}
