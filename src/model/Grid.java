/*
 * Copyright (c) 08/12/2019
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
 * Grille de jeu
 *
 * @author Robin
 */
public class Grid extends Movable {

    private Tile[] grid;

    /**
     * Constructeur
     */
    public Grid() {
        this.grid = new Tile[SIZE];
        newTile();
    }

    /**
     * Contruit une grille a partir d'un tableau de tuile
     *
     * @param _g
     */
    public Grid(Tile[] _g) {
        this.grid = _g;
    }

    /**
     * Setter
     *
     * @param grid
     */
    public void setGrid(Tile[] grid) {
        this.grid = grid;
    }

    /**
     * Ajoute une nouvelle tuile à la grille
     */
    public void newTile() {
        ArrayList<Integer> emptyTiles = new ArrayList<>();

        for (int index = 0; index < SIZE - 1; index++) {
            if (grid[index] == null) {
                emptyTiles.add(index);
            }
        }

        if (emptyTiles.size() > 0) {
            int pos = new Random().nextInt(emptyTiles.size());
            grid[emptyTiles.get(pos)] = new Tile(pos);
        }

    }

    /**
     * Verifie si le joueur a gagné
     *
     * @return
     */
    public boolean victory() {
        for (Tile t : grid) {
            if (t != null && t.getValue() == GOAL) {
                return true;
            }
        }

        return false;
    }

    /**
     * Renvoie vrai si le joueur a perdu
     *
     * @return
     */
    public boolean lose() {
        boolean control[] = {
                false,
                false,
                false,
                false
        };

        Tile[] tampon = copy();

        control[0] = left(this);
        override(tampon);

        control[1] = right(this);
        override(tampon);

        control[2] = up(this);
        override(tampon);

        control[3] = down(this);
        override(tampon);


        for (boolean b : control) {
            if (b) {
                return false;
            }
        }

        return true;
    }

    /**
     * Remplace la grille
     *
     * @param _tampon
     */
    protected void override(Tile[] _tampon) {
        for (int index = 0; index < SIZE; index++) {
            try {
                if (_tampon[index] != null) {
                    grid[index] = (Tile) _tampon[index].clone();
                } else {
                    grid[index] = null;
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Crée une copie de la grille
     *
     * @return
     */
    protected Tile[] copy() {
        Tile[] tampon = new Tile[SIZE];
        for (int index = 0; index < SIZE; index++) {
            try {
                if (grid[index] != null) {
                    tampon[index] = (Tile) grid[index].clone();
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return tampon;
    }

    /**
     * Meilleur score
     *
     * @return
     */
    public int best() {
        int score = 0;

        for (Tile t : grid) {
            if (t != null && t.getValue() > score) {
                score = t.getValue();
            }
        }

        return score;
    }


    /**
     * Gère les mouvement de la grille
     *
     * @param _d
     *         direction du mouvement
     *
     * @return
     */
    public boolean move(int _d) {
        switch (_d) {

            case LEFT:
                return left(this);

            case RIGHT:
                return right(this);

            case UP:
                return up(this);

            case DOWN:
                return down(this);

            default:
                System.out.println("Erreur de déplacement");
                return true;
        }
    }

    /**
     * renvoie l'objet
     *
     * @return
     */
    public Tile[] getGrid() {
        return this.grid;
    }

    /**
     * @return
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < SIDE; i++) {
            if (i == 0) {
                s += "|--------------|";
            } else {
                s += "  |--------------|";
            }
        }

        for (int x = 0; x < SIDE; x++) {
            s = s + "\n|";
            for (int index = 0; index < SIDE; index++) {
                for (int y = 0; y < SIDE; y++) {
                    if (this.grid[x * SIDE + y] == null) {
                        s += "    ";
                    } else if (this.grid[x * SIDE + y].getValue() < 9) {
                        s += " " + this.grid[x * SIDE + y].getValue() + "  ";
                    } else if (this.grid[x * SIDE + y].getValue() < 99) {
                        s += " " + this.grid[x * SIDE + y].getValue() + " ";
                    } else if (this.grid[x * SIDE + y].getValue() < 999) {
                        s += this.grid[x * SIDE + y].getValue();
                    }
                    s += "|";
                }
                if (index + 1 < SIDE) {
                    s += "  |";
                }
            }
        }
        s = s + "\n";
        for (int i = 0; i < SIDE; i++) {
            if (i == 0) {
                s += "|--------------|";
            } else {
                s += "  |--------------|";
            }
        }
        return s;
    }
}
