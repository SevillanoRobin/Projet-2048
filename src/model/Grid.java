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

/**
 * Grille de jeu
 *
 * @author Robin
 */
public class Grid extends Movable {

    private Tile[] grid;
    private int bestValue;

    /**
     * Constructeur
     */
    Grid() {
        this.grid = new Tile[SIZE];
        this.bestValue = newTile();
    }

    /**
     * Contruit une grille a partir d'un tableau de tuile
     *
     * @param _g
     */
    Grid(Tile[] _g) {
        this.grid = _g;
        this.parseBestValue();
    }

    /**
     * Renvoie vrai si le joueur a perdu
     *
     * @return
     */
    boolean lose() {
        Tile[] tampon = copy();

        boolean control0 = left(this);
        override(tampon);

        boolean control1 = right(this);
        override(tampon);

        boolean control2 = up(this);
        override(tampon);

        boolean control3 = down(this);
        override(tampon);

        return !(control0 || control1 || control2 || control3);
    }

    /**
     * Remplace la grille
     *
     * @param _tampon
     */
    private void override(Tile[] _tampon) {
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
    Tile[] copy() {
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
     * Meilleur valeur
     *
     */
    private void parseBestValue() {
        for (Tile t : grid) {
            if (t != null && t.getValue() > bestValue) {
                bestValue = t.getValue();
            }
        }
    }


    /**
     * Gère les mouvement de la grille
     *
     * @param _d
     *         direction du mouvement
     *
     * @return
     */
    boolean move(int _d) {
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
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < SIDE; i++) {
            if (i == 0) {
                s.append("|--------------|");
            } else {
                s.append("  |--------------|");
            }
        }

        for (int x = 0; x < SIDE; x++) {
            s.append("\n|");
            for (int index = 0; index < SIDE; index++) {
                for (int y = 0; y < SIDE; y++) {
                    if (this.grid[x * SIDE + y] == null) {
                        s.append("    ");
                    } else if (this.grid[x * SIDE + y].getValue() < 9) {
                        s.append(" ").append(this.grid[x * SIDE + y].getValue()).append("  ");
                    } else if (this.grid[x * SIDE + y].getValue() < 99) {
                        s.append(" ").append(this.grid[x * SIDE + y].getValue()).append(" ");
                    } else if (this.grid[x * SIDE + y].getValue() < 999) {
                        s.append(this.grid[x * SIDE + y].getValue());
                    }
                    s.append("|");
                }
                if (index + 1 < SIDE) {
                    s.append("  |");
                }
            }
        }
        s.append("\n");
        for (int i = 0; i < SIDE; i++) {
            if (i == 0) {
                s.append("|--------------|");
            } else {
                s.append("  |--------------|");
            }
        }
        return s.toString();
    }

    /// --- ACCESSEURS & MODIFICATEURS --- ///

    /**
     * renvoie l'objet
     *
     * @return
     */
    Tile[] getGrid() {
        return this.grid;
    }

    int getBestValue() {
        return this.bestValue;
    }

    void setBestValue(int _bestValue) {
        this.bestValue = _bestValue;
    }
}
