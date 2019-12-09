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
 * Grille de jeu
 *
 * @author Robin
 */
public class Grid implements Parametres {

    private final Tile[] grid;
    private int bestValue;
    private boolean fusion = true;

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

        boolean control0 = left();
        override(tampon);

        boolean control1 = right();
        override(tampon);

        boolean control2 = up();
        override(tampon);

        boolean control3 = down();
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
                return left();
            case RIGHT:
                return right();
            case UP:
                return up();
            case DOWN:
                return down();
            default:
                System.out.println("Erreur de déplacement");
                return true;
        }
    }


    /**
     * Déplacement des tuiles de la grille vers la gauche
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la gauche
     */
    boolean left() {
        // première ligne
        fusion = false;
        boolean move0 = moveTile(0, 1);
        boolean move1 = moveTile(1, 2);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(0, 1);
        fusion = false; // On donne a nouveau la possibilité de faire une fusion pour prochaine ligne

        // seconde ligne
        boolean move3 = moveTile(3, 4);
        boolean move4 = moveTile(4, 5);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(3, 4);
        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(6, 7);
        boolean move7 = moveTile(7, 8);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(6, 7);
        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * Déplacement des tuiles de la grille vers la droite
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la droite
     */
    boolean right() {
        // première ligne
        fusion = false;
        boolean move0 = moveTile(2, 1);
        boolean move1 = moveTile(1, 0);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(2, 1);
        fusion = false;

        // seconde ligne
        boolean move3 = moveTile(5, 4);
        boolean move4 = moveTile(4, 3);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(5, 4);
        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(8, 7);
        boolean move7 = moveTile(7, 6);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(8, 7);
        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * Déplacement des tuiles de la grille vers le haut
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le haut
     */
    boolean up() {
        fusion = false;
        // première ligne
        boolean move0 = moveTile(0, 3);
        boolean move1 = moveTile(3, 6);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(0, 3);
        fusion = false;

        // seconde ligne
        boolean move3 = moveTile(1, 4);
        boolean move4 = moveTile(4, 7);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(1, 4);
        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(2, 5);
        boolean move7 = moveTile(5, 8);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(2, 5);
        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * Déplacement des tuiles de la grille vers le bas
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le bas
     */
    boolean down() {
        // première ligne
        fusion = false;
        boolean move0 = moveTile(6, 3);
        boolean move1 = moveTile(3, 0);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(6, 3);
        fusion = false;

        // seconde ligne
        boolean move3 = moveTile(7, 4);
        boolean move4 = moveTile(4, 1);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(7, 4);

        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(8, 5);
        boolean move7 = moveTile(5, 2);
        // si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(8, 5);

        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
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

    /**
     * Effectue un mouvement entre deux tuiles
     *
     * @param _a
     *         première coordonnée
     * @param _b
     *         seconde coordonnée
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

            if (this.bestValue < newValue) {
                this.bestValue = newValue;
            }

            fusion = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ajoute une nouvelle tuile à la grille
     *
     * @return
     */
    int newTile() {
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
}
