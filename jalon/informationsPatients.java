package jalon;
import java.util.Scanner;


public class informationsPatients {

    public Scanner scanner = new Scanner(System.in);
    public static String saisirPrenom(Scanner scanner) {
        System.out.print("Prénom du patient : ");
        return scanner.nextLine().trim();
    }

    public static String saisirNom(Scanner scanner) {
        System.out.print("Nom du patient : ");
        return scanner.nextLine().trim().toUpperCase();
    }
    public static int saisirAge(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Âge du patient : ");
                int age = Integer.parseInt(scanner.nextLine().trim());
                if (age > 0) return age;
                System.out.println("L'âge doit être positif.");
            } catch (Exception e) {
                ExceptionHandler.gestionAutomatique(e);
            }
        }
    }
}

