public class tableau {


    public static int premierValuerTableau(int[] tableau) {
        return tableau[0]; // Retourne la première valeur du tableau
    }

    public static int dernierValuerTableau(int[] tableau) {
        return tableau[tableau.length - 1]; // Retourne la dernière valeur du tableau
    }

    public static void AfficherTouteValeurTableau(int[] tableau) {

        for (int i = 0; i < tableau.length; i++) {
            System.out.println("Valeur à l'index " + i + " : " + tableau[i]); // Affiche chaque valeur du tableau
        }
    }



    public static void main(String[] args) {

        int[] tableau = {40, 20, 10, 40, 50, 60,70,90}; // Initialisation du tableau

        System.out.println("Affiche moi la première valeur du tableau : " + premierValuerTableau(tableau)); // Affiche la première valeur du tableau
        System.out.println("Affiche moi la dernière valeur du tableau : " + dernierValuerTableau(tableau)); // Affiche la dernière valeur du tableau
        AfficherTouteValeurTableau(tableau);
    }
}