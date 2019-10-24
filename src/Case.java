/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

import java.util.Objects;

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
     * Accesseur de l'attribut `X`.
     *
     * @return Retourne la valeur de l'attribut.
     */
    int getX() {
        return this.x;
    }

    /**
     * Modificateur de l'attribut `X`.
     *
     * @param _x Nouvelle valeur de l'attribut.
     */
    void setX(int _x) {
        this.x = _x;
    }

    /**
     * Accesseur de l'attribut `Y`.
     *
     * @return Retourne la valeur de l'attribut.
     */
    int getY() {
        return this.y;
    }

    /**
     * Modificateur de l'attribut `Y`.
     *
     * @param _y Nouvelle valeur de l'attribut.
     */
    void setY(int _y) {
        this.y = _y;
    }

    /**
     * Accesseur de l'attribut `value`.
     *
     * @return Retourne la valeur de l'attribut.
     */
    int getValue() {
        return this.value;
    }

    /**
     * Modificateur de l'attribut `value`.
     *
     * @param _value Nouvelle valeur de l'attribut.
     */
    void setValue(int _value) {
        this.value = _value;
    }

    /**
     * Modificateur de l'attribut `grille`.
     * <p>
     * Modifie la grille associée à la case.
     * Doit être utilisée pour éviter toute {@link NullPointerException} ou autre bug.
     *
     * @param _grille Nouvelle valeur de l'attribut.
     */
    void setGrille(Grille _grille) {
        this.grille = _grille;
    }

    /**
     * Redéfinition de la méthode {@link Object#toString()}.
     * <p>
     * Offre une chaîne d'information contenant les coordonnées et la valeur de la cellule.
     *
     * @return ladite chaîne d'information ({@link String}).
     */
    @Override
    public String toString() {
        return "Case(" + x + "," + y + "," + value + ")";
    }

    /**
     * Redéfinition de la méthode {@link Object#hashCode()}.
     * <p>
     * Permet de fournir un identifiant unique pour chaque instance différente ( !equals ) pour la {@link java.util.HashSet grille}.
     *
     * @return l'identifiant créé.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), grille);
    }

    /**
     * Redéfinition de la méthode {@link Object#equals(Object)} )}.
     * <p>
     * Permet de déterminer si deux cellules sont les mêmes, peu importe si les instances le sont ou non.
     *
     * @param _o Instance à comparer avec l'instance courante.
     *
     * @return {@code true} si les cellules sont les mêmes.
     */
    @Override
    public boolean equals(Object _o) {
        if (this == _o) return true;
        if (!(_o instanceof Case)) return false;
        Case aCase = (Case) _o;
        return getX() == aCase.getX() &&
               getY() == aCase.getY() &&
               grille.equals(aCase.grille);
    }

    /**
     * Détermine si la valeur d'une case est la même que celle de la case courante.
     *
     * @param _case Case à comparer avec l'instance courante.
     *
     * @return {@code true} si les valeurs sont les mêmes.
     */
    boolean valeurEgale(Case _case) {
        return this.value == _case.value;
    }

    /**
     * Donne le voisin direct de la cellule courante dans une direction donnée.
     *
     * @param _direction direction dans laquelle on cherche un voisin.
     *
     * @return la cellule voisine trouvée (peut être nulle).
     */
    Case getVoisinDirect(int _direction) {
        Case cell = null;
        Case[][] tableau = new Case[Parametres.TAILLE][Parametres.TAILLE];

        for (Case c : this.grille.getGrille()) {
            if (!this.equals(c))
                tableau[c.getX()][c.getY()] = c;  // Pris de la correction du TP 2048 de L2.
        }

        switch (_direction) {
            case Parametres.HAUT:
                for (int j = this.y; j >= 0 && cell == null; j--) {
                    cell = tableau[this.x][j];
                }
                break;
            case Parametres.BAS:
                for (int j = this.y; j < Parametres.TAILLE && cell == null; j++) {
                    cell = tableau[this.x][j];
                }
                break;
            case Parametres.GAUCHE:
                for (int i = this.x; i >= 0 && cell == null; i--) {
                    cell = tableau[i][this.y];
                }
                break;
            case Parametres.DROITE:
                for (int i = this.x; i < Parametres.TAILLE && cell == null; i++) {
                    cell = tableau[i][this.y];
                }
                break;
        }

        return cell;
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
        if (_x < 0 || _x >= Parametres.TAILLE) {
            throw new IllegalArgumentException("Case: x has a wrong value.");
        }
        if (_y < 0 || _y >= Parametres.TAILLE) {
            throw new IllegalArgumentException("Case: y has a wrong value.");
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
