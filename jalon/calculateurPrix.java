package jalon;

public class calculateurPrix {

    /**
     * Calcule le prix final avec réduction en fonction de l'âge du patient.
     * - Moins de 18 ou plus de 60 ans → 80% de réduction
     * - Sinon → remboursement de 60% (reste à charge 40%)
     */
    public static double calculerPrixFinal(int index, double[] tarifs, int age) {
        double montantFinal = 0.0;

        try {
            double montantInitial = tarifs[index];

            if (age < 18 || age > 60) {
                montantFinal = montantInitial * 0.20;
                System.out.printf("Réduction mineur/senior appliquée (-80%%). Montant réduit : %.2f EUR%n", montantFinal);
            } else {
                double remboursement = montantInitial * 0.60;
                montantFinal = montantInitial * 0.40;
                System.out.printf("Remboursement assurance maladie (60%%) : %.2f EUR%n", remboursement);
            }

        } catch (Exception e) {
            ExceptionHandler.gestionAutomatique(e);
        } 

        return montantFinal;
    }
}
