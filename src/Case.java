/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

/**
 * Classe Case.
 * <p>
 * Represente une cellule du jeu 2048, avec une valeur entre 2 et 2048 (voire plus).
 */
public class Case implements Parametres {
    /** Coordonnée en abscisse de la case. */
    private int x;
    /** Coordonnée en ordonnée de la case. */
    private int y;
    /** Valeur de la cellule. */
    private int value;

    /** Grille associée à la case. */
    private Grille grille;
}
