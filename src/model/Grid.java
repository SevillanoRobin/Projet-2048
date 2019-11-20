/*
 * Copyright (c) 20/11/2019
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

import static model.Parametres.*;

public class Grid {

    private Tile[] grid;
    private boolean fusion;

    /**
     * Constructeur par défaut.
     */
    Grid() {
        this.grid = new Tile[SIZE];
        this.fusion = true;

        this.newTile();
    }

    /**
     * Constructeur par paramètre.
     * <p>
     * Contruit une grille à partir d'un tableau de tuile.
     */
    Grid(Tile[] _tiles) {
        this.grid = _tiles;
        fusion = true;
    }

	/**
	 * Accesseur de l'attribut {@code grid} (tableau de {@link Tile tuile}).
     *
     * @return les tuiles.
	 */
    public Tile[] getGrid() {
		return this.grid;
	}

    /**
	 * Accesseur des éléments de l'attribut {@code grid} (tableau de {@link Tile tuile}).
	 *
	 * @param _ind Indice de la tuile dans le tableau.
	 *
	 * @return la tuile trouvée.
	 */
	public Tile getTile(int _ind) {
		return this.grid[_ind];
	}

	/**
     * Modificateur de l'attribut {@code grid} (tableau de {@link Tile tuile}).
     *
     * @param _grid Une grille contant la nouvelle valeur de l'attribut.
     */
    private void setGrid(Grid _grid) {
        this.grid = _grid.getGrid();
    }

    /**
     * renvoie {@code false} si le joueur a perdu.
     */
    boolean stillPlayeable() {
        Tile[] tampon = copy();

        boolean control0 = left(true, this);
        override(tampon);

        boolean control1 = right(true, this);
        override(tampon);

        boolean control2 = up(true, this);
        override(tampon);

        boolean control3 = down(true, this);
        override(tampon);

        return control0 || control1 || control2 || control3;
    }

    /**
     * Remplace la grille
     */
    private void override(Tile[] _tampon) {
        for (int index = 0; index < SIZE; index++) {
            try {
                if (_tampon[index] != null)
                    grid[index] = (Tile) _tampon[index].clone();
                else
                    grid[index] = null;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Crée une copie de la grille
     *
     * @return cette copie.
     */
    private Tile[] copy() {
        Tile[] tampon = new Tile[SIZE];

        for (int index = 0; index < SIZE; index++) {
            try {
                if (grid[index] != null)
                    tampon[index] = (Tile) grid[index].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return tampon;
    }

    /**
     * Meilleur score
     */
    int best() {
        int score = 0;

        for (Tile t : grid)
            if (t != null && t.getValue() > score)
                score = t.getValue();

        return score;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("|--------------|\n|");

        for (int index = 0; index < SIZE; index++) {
            if (grid[index] == null)
                s.append("    ");
            else if (grid[index].getValue() < 9)
                s.append(" ").append(grid[index].getValue()).append("  ");
            else if (grid[index].getValue() < 99)
                s.append(" ").append(grid[index].getValue()).append(" ");
            else if (grid[index].getValue() < 999)
                s.append(grid[index].getValue());

            s.append("|");

            if (index == 2 || index == 5)
                s.append("\n|--------------|\n|");
            if (index == 8)
                s.append("\n|--------------|\n");
        }

        return s.toString();
    }

    /**
     * Ajoute une nouvelle tuile à la grille
     */
    private void newTile() {
        ArrayList<Integer> emptyTiles = new ArrayList<>();

        for (int index = 0; index < SIZE - 1; index++)
            if (grid[index] == null)
                emptyTiles.add(index);

        if (emptyTiles.size() > 0) {
            int pos = new Random().nextInt(emptyTiles.size());
            grid[emptyTiles.get(pos)] = new Tile(pos);
        }

    }


    /**
     * Gère les mouvement de la grille
     *
     * @param _d direction du mouvement
     */
    boolean move(int _d) {
        switch (_d) {

            case LEFT:
                return left(false, this);

            case RIGHT:
                return right(false, this);

            case UP:
                return up(false, this);

            case DOWN:
                return down(false, this);

            default:
                System.out.println("Erreur de déplacement");
                return true;
        }
    }

    /**
     * Deplacement des tuiles de la grille vers la gauche
     *
     * @return boolean
     */
    private boolean left(boolean _control, Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

        // première ligne
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
                if (!_control) {
                    newTile();
                }
                return true;
            }
        }


        return false;
    }

    /**
     * Deplacement des tuiles de la grille vers la droite
     *
     * @return boolean
     */
    private boolean right(boolean _control, Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

        // première ligne
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
                if (!_control) {
                    newTile();
                }
                return true;
            }
        }

        return false;
    }


    /**
     * Deplacement des tuiles de la grille vers le haut
     *
     * @return boolean
     */
    private boolean up(boolean _control, Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

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
                if (!_control) {
                    newTile();
                }
                return true;
            }
        }

        return false;
    }


    /**
     * Deplacement des tuiles de la grille vers le bas
     *
     * @return boolean
     */
    private boolean down(boolean _control, Grid _g) {

        setGrid(_g);

        // tableau récapitulatif des mouvements
        boolean[] moves = new boolean[9];

        // première ligne
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
                if (!_control) {
                    newTile();
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Effectue un mouvement entre deux tuile
     *
     * @param _a première coordoné
     * @param _b seconde coordoné
     *
     * @return boolean
     */
    private boolean moveTile(int _a, int _b) {

        // Les case sont vide
        if (grid[_a] == null && grid[_b] == null) {
            return false;
        }
        // Mouvement de _b vers _a
        else if (grid[_a] == null && grid[_b] != null) {
            grid[_a] = grid[_b];
            grid[_b] = null;

            grid[_a].setPos(_a);

            return true;
        }

        // Fusion de _a et _b
        else if (grid[_b] != null && grid[_a].getValue() == grid[_b].getValue() && !fusion) {
            grid[_a].doubleValue();
            grid[_b] = null;

            fusion = true;
            return true;
        } else {
            return false;
        }
    }
}