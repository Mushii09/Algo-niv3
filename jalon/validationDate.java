package jalon;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class validationDate {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HEURE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static LocalDateTime saisirDateHeure(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\nDate du rendez-vous (jj/MM/aaaa) : ");
                LocalDate date = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);

                System.out.print("Heure du rendez-vous (HH:mm) : ");
                LocalTime heure = LocalTime.parse(scanner.nextLine().trim(), HEURE_FORMATTER);

                LocalDateTime dateHeure = LocalDateTime.of(date, heure);

                if (estDateHeureValide(dateHeure)) {
                    System.out.println("Rendez-vous confirmé pour le " +
                            date.format(DATE_FORMATTER) + " à " + heure.format(HEURE_FORMATTER));
                    return dateHeure;
                }

            } catch (Exception e) {
                ExceptionHandler.gestionAutomatique(e);
            }
        }
    }
    public static boolean estDateHeureValide(LocalDateTime dateHeure) {
        LocalDate date = dateHeure.toLocalDate();
        LocalTime heure = dateHeure.toLocalTime();

        if (dateHeure.isBefore(LocalDateTime.now())) {
            System.out.println("La date doit être dans le futur.");
            return false;
        }

        if (estWeekend(date)) {
            System.out.println("es rendez-vous sont uniquement du lundi au vendredi.");
            return false;
        }

        if (!estDansPlageHoraire(heure)) {
            System.out.println("Heures valides : 08:00–12:00 et 14:00–17:00.");
            return false;
        }

        if (estJourFerie(date)) {
            System.out.println("Ce jour est férié en France.");
            return false;
        }

        return true;
    }

    private static boolean estWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private static boolean estDansPlageHoraire(LocalTime heure) {
        return (heure.isAfter(LocalTime.of(7, 59)) && heure.isBefore(LocalTime.of(12, 1)))
            || (heure.isAfter(LocalTime.of(13, 59)) && heure.isBefore(LocalTime.of(17, 1)));
    }

    private static boolean estJourFerie(LocalDate date) {
        int year = date.getYear();

        // Jours fixes 🇫🇷
        LocalDate[] fixes = {
            LocalDate.of(year, 1, 1),    // Jour de l'an
            LocalDate.of(year, 5, 1),    // Fête du travail
            LocalDate.of(year, 5, 8),    // Victoire 1945
            LocalDate.of(year, 7, 14),   // Fête nationale
            LocalDate.of(year, 8, 15),   // Assomption
            LocalDate.of(year, 11, 1),   // Toussaint
            LocalDate.of(year, 11, 11),  // Armistice
            LocalDate.of(year, 12, 25)   // Noël
        };

        // Jours mobiles
        LocalDate paques = calculerPaques(year);
        LocalDate[] mobiles = {
            paques.plusDays(1),    // Lundi de Pâques
            paques.plusDays(39),   // Ascension
            paques.plusDays(50)    // Pentecôte
        };

        for (LocalDate jour : fixes) {
            if (jour.equals(date)) return true;
        }
        for (LocalDate jour : mobiles) {
            if (jour.equals(date)) return true;
        }
        return false;
    }

    private static LocalDate calculerPaques(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(year, month, day);
    }

    
}

