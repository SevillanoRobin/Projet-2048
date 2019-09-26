import java.util.Arrays;
import java.util.HashSet;

public class Grille implements Parametres {
    private int valeurMax;

    private HashSet<Case> grille;

    public Grille() {
        grille = new HashSet<>( TAILLE * 2 );
    }

    public int getValeurMax() {
        return this.valeurMax;
    }

    public HashSet<Case> getGrille() {
        return this.grille;
    }

    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];

        for ( Case c : this.grille ) {
            tableau[c.getY()][c.getX()] = c.getValue();  // Pris de la correction du TP 2048 de L2.
        }
        StringBuilder res = new StringBuilder();
        for ( int i = 0; i < tableau.length; i++ ) {
            res.append( Arrays.toString( tableau[i] ) ); // Pris de la correction du TP 2048 de L2.

            if ( i != tableau.length - 1 ) {
                res.append( "\n" );
            }
        }

        return res.toString();
    }
}
