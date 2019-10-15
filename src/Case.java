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

    /**
     * Constructeur.
     * <p>
     * Instancie la case ainsi que sa valeur basé sur les paramètres.
     *
     * @param _x     Coordonnée en abscisse de la case (doit être dans la grille : [0; TAILLE[).
     * @param _y     Coordonnée en ordonné de la case (doit être dans la grille : [0; TAILLE[)..
     * @param _value Valeur initiale de la cellule (doit être 2 ou 4).
     */
    Case(int _x, int _y, int _value) {
        this.x = _x;
        this.y = _y;
        this.value = _value;
    }

    /**
     * Accesseur du contructeur.
     * <p>
     * Cette méthode vérifie que les paramètres convienne pour la création d'une case.
     * Ensuite, elle crée une instance {@link Case} ou lance une erreur {@link IllegalArgumentException}.
     *
     * @param _x     Coordonnée en abscisse de la case (doit être dans la grille : [0; TAILLE[).
     * @param _y     Coordonnée en ordonné de la case (doit être dans la grille : [0; TAILLE[)..
     * @param _value Valeur initiale de la cellule (doit être 2 ou 4).
     *
     * @throws IllegalArgumentException si les paramètres ne respectent pas les contraintes.
     */
    static Case verifyThenCreateCase(int _x, int _y, int _value) throws IllegalArgumentException {
        // Vérification des coordonnées.
        if ( _x < 0 || _x >= Parametres.TAILLE ) {
            throw new IllegalArgumentException( "Case: x has a wrong value." );
        }
        if ( _y < 0 || _y >= Parametres.TAILLE ) {
            throw new IllegalArgumentException( "Case: y has a wrong value." );
        }

        // Vérification de la valeur.
        if (_value < 2) {
            throw new IllegalArgumentException("La valeur initiale d'un carré a été initialisée à moins de 2.");
        } else if (_value > 4) {
            throw new IllegalArgumentException("La valeur initiale d'un carré a été initialisée à plus de 4.");
        } else if (_value % 2 != 0) {
            throw new IllegalArgumentException("La valeur initiale d'un carré a été initialisée à 3 (invalide).");
        }

        return new Case(_x, _y, _value);
    }
}
