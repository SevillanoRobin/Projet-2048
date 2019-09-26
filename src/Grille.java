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
}
