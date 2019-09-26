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
}
