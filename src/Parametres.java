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
 * Interface Parametres.
 * <p>
 * Utilisée pour stocker des paramètres globaux de l'application, ainsi les directions (pseudo-énumération).
 */
public interface Parametres {
    // ---- Directions ---- //
    /**
     *
     */
    int HAUT = 1;
    /**
     *
     */
    int DROITE = 2;
    /**
     *
     */
    int BAS = -1;
    /**
     *
     */
    int GAUCHE = -2;

    // ---- Paramètres ---- //
    /** Taille en cellule d'un côté de la grille (carrée). */
    int TAILLE = 4;
    /** L'objectif à accomplir pour obtenir la victoire. */
    int OBJECTIF = 2048;
}