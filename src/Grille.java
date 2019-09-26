import java.util.HashSet;

public class Grille implements Parametres {
    private int valeurMax;

    private HashSet<Case> grille;

    public Grille() {
        grille = new HashSet<>();
    }

    public int getValeurMax() {
        return this.valeurMax;
    }

    public HashSet<Case> getGrille() {
        return this.grille;
    }
}
