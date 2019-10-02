/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * Détecte s'il reste des mouvements possibles.
     * <p>
     * Ne s'occupe que de retourner l'information ; l'appel de la méthode `gameOver()` ne se fait pas ici.
     * Ne s'occupe pas de l'attribut `valeurMax`.
     *
     * @return {@code true} si la partie est "finie".
     */
    boolean partieFinie() {
        int size = this.grille.size();

        if (size < Math.pow(Parametres.TAILLE, 2)) {
            return false;
        }

        int[] directions = {
                Parametres.HAUT,
                Parametres.GAUCHE,
                Parametres.DROITE,
                Parametres.BAS
        };

        for (Case _case : this.grille) {
            for (int _direction : directions) {
                Case _voisin = _case.getVoisinDirect(_direction);
                if (_voisin != null && _case.valeurEgale(_voisin)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Multiplie la valeur de la case passée en paramètre par 2.
     * <p>
     * Met la varianble `valeurMax` à jour, le cas échéant.
     *
     * @param _case Case fusionnée.
     */
    private void fusion(Case _case) {
        int newValue = _case.getValue() * 2;
        _case.setValue(newValue);

        if (this.valeurMax < newValue) {
            this.valeurMax = newValue;
        }
    }

    /**
     * Trouve quelles sont les 4 cases les plus proches de l'extremité de la direction choisie.
     *
     * @param _direction Direction choisie pour l'extremité.
     *
     * @return ces 4 cases sous la forme d'un tableau.
     */
    Case[] getCasesExtremites(int _direction) {
        Case[][] tableau = new Case[TAILLE][TAILLE];

        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c;  // Pris de la correction du TP 2048 de L2.
        }

        Case[] res = new Case[Parametres.TAILLE];
        Method usedMethod;
        try {
            switch (_direction) {
                case Parametres.HAUT:
                    usedMethod = Grille.class.getDeclaredMethod("getHighestCase", int.class, Case[][].class);
                    break;
                case Parametres.BAS:
                    usedMethod = Grille.class.getDeclaredMethod("getLowestCase", int.class, Case[][].class);
                    break;
                case Parametres.GAUCHE:
                    usedMethod = Grille.class.getDeclaredMethod("getLeftestCase", int.class, Case[][].class);
                    break;
                case Parametres.DROITE:
                    usedMethod = Grille.class.getDeclaredMethod("getRightestCase", int.class, Case[][].class);
                    break;
                default:
                    return null;
            }
        } catch (NoSuchMethodException _e) {
            _e.printStackTrace();
            return null;
        }

        for (int i = 0; i < 4; i++) {
            try {
                res[i] = (Case) usedMethod.invoke(this, i, tableau);
            } catch (IllegalAccessException | InvocationTargetException _e) {
                _e.printStackTrace();
            }
        }

        return res;
    }

    /**
     * Trouve la case la plus proche de l'extrémité superieure dans une colonne donnée.
     *
     * @param _i       numéro de la colonne donnée.
     * @param _tableau tableau de cases correspondant à la grille.
     *
     * @return la case trouvée.
     */
    private Case getHighestCase(int _i, Case[][] _tableau) {
        Case _cell = null;
        for (int j = 0; j < 4 && _cell == null; j++) {
            _cell = _tableau[j][_i];
        }
        return _cell;
    }

    /**
     * Trouve la case la plus proche de l'extrémité inférieure dans une colonne donnée.
     *
     * @param _i       numéro de la colonne donnée.
     * @param _tableau tableau de cases correspondant à la grille.
     *
     * @return la case trouvée.
     */
    private Case getLowestCase(int _i, Case[][] _tableau) {
        Case _cell = null;
        for (int j = 3; j >= 0 && _cell == null; j--) {
            _cell = _tableau[j][_i];
        }
        return _cell;
    }

    /**
     * Trouve la case la plus proche de l'extrémité de gauche dans une ligne donnée.
     *
     * @param _i       numéro de la ligne donnée.
     * @param _tableau tableau de cases correspondant à la grille.
     *
     * @return la case trouvée.
     */
    private Case getLeftestCase(int _i, Case[][] _tableau) {
        Case _cell = null;
        for (int j = 0; j < 4 && _cell == null; j++) {
            _cell = _tableau[_i][j];
        }
        return _cell;
    }

    /**
     * Trouve la case la plus proche de l'extrémité de droite dans une ligne donnée.
     *
     * @param _i       numéro de la ligne donnée.
     * @param _tableau tableau de cases correspondant à la grille.
     *
     * @return la case trouvée.
     */
    private Case getRightestCase(int _i, Case[][] _tableau) {
        Case _cell = null;
        for (int j = 3; j >= 0 && _cell == null; j--) {
            _cell = _tableau[_i][j];
        }
        return _cell;
    }
}
