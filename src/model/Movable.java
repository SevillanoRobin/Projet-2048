/*
 * Copyright (c) 09/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */
package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contient les différentes méthodes de déplacments possible des grilles
 */
public abstract class Movable implements Parametres {

    protected Tile[] grid;
    boolean fusion = true;

    /**
     * Initialise la grille avant d'utiliser une methode
     */
    private void setGrid(Grid _g) {
        this.grid = _g.getGrid();
    }

    /**
     * Effectue un mouvement entre deux tuile
     *
     * @param _a
     *         première coordoné
     * @param _b
     *         seconde coordoné
     *
     * @return boolean
     */
    private boolean moveTile(int _a, int _b) {

        // Les case sont vide
        if (grid[_a] == null && grid[_b] == null) {
            return false;
        } // Mouvement de _b vers _a
        else if (grid[_a] == null && grid[_b] != null) {
            grid[_a] = grid[_b];
            grid[_b] = null;

            grid[_a].setX(_a);
            return true;
        } // Fusion de _a et _b
        else if (grid[_b] != null && grid[_a].getValue() == grid[_b].getValue() && !fusion) {
            int newValue = grid[_a].getValue() * 2;
            grid[_a].setValue(newValue);
            grid[_b] = null;

            if (this instanceof Grid && ((Grid) this).getBestValue() < newValue) {
                ((Grid) this).setBestValue(newValue);
            }

            fusion = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Déplace une tuile de la grille g2 emplacment b vers la grille g1 emplacment a
     *
     * @param _a
     * @param _b
     * @param g1
     * @param g2
     *
     * @return
     */
    private boolean moveTileDifferentGrid(int _a, int _b, Grid g1, Grid g2) {
        // Les case sont vide
        if (g1.getGrid()[_a] == null && g2.getGrid()[_b] == null) {
            return false;
        } // Mouvement de _b vers _a
        else if (g1.getGrid()[_a] == null && g2.getGrid()[_b] != null) {
            g1.getGrid()[_a] = g2.getGrid()[_b];
            g2.getGrid()[_b] = null;
            return true;
        } // Fusion de _a et _b
        else if (g2.getGrid()[_b] != null && g1.getGrid()[_a].getValue() == g2.getGrid()[_b].getValue() && !fusion) {
            g1.getGrid()[_a].setValue(g1.getGrid()[_a].getValue() * 2);
            g2.getGrid()[_b] = null;
            fusion = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ajoute une nouvelle tuile à la grille
     * @return
     */
    public int newTile() {
        ArrayList<Integer> emptyTiles = new ArrayList<>();

        for (int index = 0; index < SIZE - 1; index++) {
            if (grid[index] == null) {
                emptyTiles.add(index);
            }
        }

        if (emptyTiles.size() > 0) {
            int pos = new Random().nextInt(emptyTiles.size());
            Tile tile = new Tile(pos);
            grid[emptyTiles.get(pos)] = tile;
            return tile.getValue();
        }

        return 0;
    }

    /**
     * Déplacement des tuiles de la grille vers la gauche
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la gauche
     */
    public boolean left(Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean moves[] = new boolean[9];

        // première ligne
        fusion = false;
        moves[0] = moveTile(0, 1);
        moves[1] = moveTile(1, 2);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[2] = moveTile(0, 1);
        fusion = false; // On donne a nouveau la possibilité de faire une fusion pour prochaine ligne

        // seconde ligne
        moves[3] = moveTile(3, 4);
        moves[4] = moveTile(4, 5);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[5] = moveTile(3, 4);
        fusion = false;

        // troisième ligne
        moves[6] = moveTile(6, 7);
        moves[7] = moveTile(7, 8);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[8] = moveTile(6, 7);
        fusion = false;

        // Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
        for (boolean b : moves) {
            if (b) {
                return true;
            }
        }

        return false;
    }

    /**
     * Déplacement des tuiles de la grille vers la droite
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la droite
     */
    public boolean right(Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

        // première ligne
        fusion = false;
        moves[0] = moveTile(2, 1);
        moves[1] = moveTile(1, 0);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[2] = moveTile(2, 1);
        fusion = false;

        // seconde ligne
        moves[3] = moveTile(5, 4);
        moves[4] = moveTile(4, 3);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[5] = moveTile(5, 4);
        fusion = false;

        // troisième ligne
        moves[6] = moveTile(8, 7);
        moves[7] = moveTile(7, 6);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[8] = moveTile(8, 7);
        fusion = false;

        // Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
        for (boolean b : moves) {
            if (b) {
                return true;
            }
        }

        return false;
    }

    /**
     * Déplacement des tuiles de la grille vers le haut
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le haut
     */
    public boolean up(Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

        fusion = false;
        // première ligne
        moves[0] = moveTile(0, 3);
        moves[1] = moveTile(3, 6);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[2] = moveTile(0, 3);
        fusion = false;

        // seconde ligne
        moves[3] = moveTile(1, 4);
        moves[4] = moveTile(4, 7);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[5] = moveTile(1, 4);
        fusion = false;

        // troisième ligne
        moves[6] = moveTile(2, 5);
        moves[7] = moveTile(5, 8);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[8] = moveTile(2, 5);
        fusion = false;

        // Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
        for (boolean b : moves) {
            if (b) {
                return true;
            }
        }

        return false;
    }

    /**
     * Déplacement des tuiles de la grille vers le bas
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le bas
     */
    public boolean down(Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

        // première ligne
        fusion = false;
        moves[0] = moveTile(6, 3);
        moves[1] = moveTile(3, 0);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[2] = moveTile(6, 3);
        fusion = false;

        // seconde ligne
        moves[3] = moveTile(7, 4);
        moves[4] = moveTile(4, 1);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[5] = moveTile(7, 4);

        fusion = false;

        // troisième ligne
        moves[6] = moveTile(8, 5);
        moves[7] = moveTile(5, 2);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        moves[8] = moveTile(8, 5);

        fusion = false;

        // Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
        for (boolean b : moves) {
            if (b) {
                return true;
            }
        }

        return false;
    }


}
