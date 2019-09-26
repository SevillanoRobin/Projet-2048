/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Classe Grille.
 * <p>
 * Represente une grille de jeu 2048, composée de {@link Case cellules}.
 */
public class Grille implements Parametres {
    /** La plus grande valeur au sein de la grille. */
    private int valeurMax;

    /** Ensemble des {@link Case cellules} de la grille. */
    private HashSet<Case> grille;

    /**
     * Constructeur.
     * <p>
     * Initialise les attributs comme convenable.
     * <p>
     * Le tableau bénéficie d'une initialisation avec une taille initiale correspondante à sa taille maximale.
     */
    Grille() {
        this.valeurMax = 0;
        this.grille = new HashSet<>(TAILLE * 2);
    }

    /**
     * Accesseur de l'attribut `valeurMax`.
     *
     * @return Retourne la valeur de l'attribut.
     */
    int getValeurMax() {
        return this.valeurMax;
    }

    /**
     * Accesseur de l'attribut `grille`.
     *
     * @return Retourne la valeur de l'attribut.
     */
    HashSet<Case> getGrille() {
        return this.grille;
    }

    /**
     * Redéfinition de la méthode {@link Object#toString()}.
     * <p>
     * Offre une chaîne d'information représentant la grille ainsi que ses différentes cellules.
     * <p>
     * La chaîne contient plusieurs lignes.
     *
     * @return ladite chaîne d'information ({@link String}).
     */
    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];

        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValue();  // Pris de la correction du TP 2048 de L2.
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tableau.length; i++) {
            res.append(Arrays.toString(tableau[i]));

            if (i != tableau.length - 1) {
                res.append("\n");
            }
        }

        return res.toString();
    }

    /**
     * Méthode invoquée quand le jeu a été résolu.
     * <p>
     * Pour le moment, n'envoit qu'un message et arrête l'application.
     */
    private void victory() {
        System.out.println("Congratulation! You won.\nScore: " + this.valeurMax);

        System.exit(0);
    }

    /**
     * Méthode invoquée quand le jeu a été perdu.
     * <p>
     * Pour le moment, n'envoit qu'un message et arrête l'application.
     */
    private void gameOver() {
        System.out.println("Game over. The grid is full.\nScore: " + this.valeurMax);

        System.exit(0);
    }

    /**
     * Crée une nouvelle case dans la grille si possible.
     * <p>
     * Utilise des coordonnées et des valeurs aléatoires.
     *
     * @return {@code true} si la case a pu être créé.
     */
    boolean nouvelleCase() {
        if (this.grille.size() >= Math.pow(Parametres.TAILLE, 2)) {
            return false;
        }

        Case cell;
        {
            Random random = new Random();

            int x;
            int y;
            int valeur = (int) Math.floor(random.nextDouble() * 2 + 2);

            if (valeur == 3) valeur = 4;

            do {
                x = (int) random.nextDouble() * Parametres.TAILLE;
                y = (int) random.nextDouble() * Parametres.TAILLE;
                cell = Case.verifyThenCreateCase(x, y, valeur);
                cell.setGrille(this);
            } while (!this.grille.add(cell));

            if (this.valeurMax < valeur) {
                this.valeurMax = valeur;
            }
        }

        cell.setGrille(this);
        return true;
    }
}
