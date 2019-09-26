/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

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
}
