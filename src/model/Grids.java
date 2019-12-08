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

import java.io.*;
import java.util.Calendar;

/**
 * Ensemble des 3 grilles
 *
 * @author Robin
 */
public class Grids extends Movable implements Serializable {

    private Grid grids[];

    /**
     * Constructeur de la matrice 3D
     */
    public Grids() {

        grids = new Grid[3];

        for (int index = 0; index < 3; index++) {
            grids[index] = new Grid();
        }
    }

    /**
     * Creé un nouvelle ensemble de 3 grilles tout en conservant les propriétés de la grille passée en paramètre
     *
     * @param grid
     */
    public Grids(Grid grid[]) {
        this.grids = new Grid[SIDE];
        for (int index = 0; index < SIDE; index++) {
            this.grids[index] = new Grid(grid[index].copy());
        }
    }

    /**
     * Meilleur score
     *
     * @return
     */
    public int best() {
        int score = 0;

        for (Grid g : grids) {
            if (g.best() > score) {
                score = g.best();
            }
        }

        return score;
    }

    /**
     * Score totale
     *
     * @return
     */
    public int scoreTotalGrille() {
        int score = 0;
        for (Grid g : this.grids) {
            for (Tile t : g.getGrid()) {
                if (t != null) {
                    score = score + t.getValue();
                }

            }

        }
        return score;
    }

    /**
     * Renvoie un score supérieur au score total car le poids des tuiles est différent selon leur valeur
     *
     * @return
     */
    public int scoreTotalGrilleMajore() {
        Grids copy = new Grids(this.grids);
        int score = 0;
        for (Grid g : this.grids) {
            for (Tile t : g.getGrid()) {
                if (t != null) {
                    score = score + (t.getValue() * (t.getValue() / 2));
                }

            }

        }
        return score;
    }

    /**
     * Verifie si les 3 grilles son bloquées
     *
     * @return boolean
     */
    public boolean lose() {

        Grid tamp[] = reorganization(this.grids);
        boolean lose[] = new boolean[SIDE];
        int index = 0;
        for (Grid g : tamp) {
            lose[index] = g.lose();
            index++;
        }
        int nbBoolean = 0;
        for (boolean b : lose) {
            if (b) {
                nbBoolean++;
            }
        }
        //on remet la matrice dans le bon sens
        index = 0;
        for (Grid g : grids) {
            if (g.lose()) {
                lose[index] = g.lose();
                index++;
            }
        }
        for (boolean b : lose) {
            if (b) {
                nbBoolean++;
            }
        }
        if (nbBoolean == lose.length * 2) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Verifie si la valeur de la tuile la plus élevé est égale ou supérieur à la valeur but
     *
     * @return
     */
    public boolean victory() {
        if (best() >= GOAL) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gere les depacements des grilles
     *
     * @param simulation
     * @param _d
     *
     * @return
     */
    public boolean move(boolean simulation, int _d) {
        boolean verif[] = { false, false, false };

        if (_d == FRONT) {
            Grids tamp = new Grids(reorganization(this.grids));
            for (int index = 0; index < SIDE; index++) {
                verif[index] = tamp.getGrids()[index].move(DOWN);

            }
            tamp.setGrids(this.reorganizationInverse(tamp.getGrids())); //on remet la matrice dans le bon sens
            if (this.equals(tamp.grids)) {
                verif[0] = false;
            } else {
                this.grids = tamp.grids;
                verif[0] = true;
            }

        } else if (_d == BACK) {
            Grids tamp = new Grids(reorganization(this.grids));
            for (int index = 0; index < SIDE; index++) {
                verif[index] = tamp.getGrids()[index].move(UP);
            }
            tamp.setGrids(this.reorganizationInverse(tamp.getGrids())); //on remet la matrice dans le bon sens
            if (this.equals(tamp.grids)) {
                verif[0] = false;
            } else {
                this.grids = tamp.grids;
                verif[0] = true;
            } ;
        } else {
            for (int index = 0; index < SIDE; index++) {
                verif[index] = grids[index].move(_d);
            }

        }

        boolean estRentre = false;
        for (boolean b : verif) {
            if (b) {
                estRentre = true;
                boolean ajoutPossible = false;
                int pasPossible = 0;
                while (!ajoutPossible && pasPossible < 100) {
                    int random = (int) (Math.random() * this.grids.length);
                    for (int i = 0; i < this.grids[random].getGrid().length; i++) {
                        if (this.grids[random].getGrid()[i] == null && simulation == false) {
                            this.grids[random].newTile();
                            return true;
                        }
                    }
                    pasPossible++;
                }
            }
        }
        if (estRentre == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Getter
     *
     * @return
     */
    public Grid[] getGrids() {
        return this.grids;
    }


    /**
     * Setter
     *
     * @param _gs
     */
    private void setGrids(Grid[] _gs) {
        this.grids = _gs;
    }

    @Override
    public String toString() {
        String s = "";
        for (Grid g : grids) {
            s += g.toString();
        }
        return s;
    }

    /**
     * Pivote la matrice de jeu
     *
     * @param _gs
     *
     * @return
     */
    public Grid[] reorganization(Grid[] _gs) {
        Tile[] result1 = new Tile[9];
        Tile[] result2 = new Tile[9];
        Tile[] result3 = new Tile[9];

        int index2 = 2, index3 = 0;
        for (int index1 = 0; index1 < SIZE; index1++) {
            if (_gs[index2].getGrid()[index3] != null) {
                result1[index1] = _gs[index2].getGrid()[index3];
            }
            if (_gs[index2].getGrid()[index3 + SIDE] != null) {
                result2[index1] = _gs[index2].getGrid()[index3 + SIDE];
            }
            if (_gs[index2].getGrid()[index3 + (SIDE * 2)] != null) {
                result3[index1] = _gs[index2].getGrid()[index3 + (SIDE * 2)];
            }

            index3++;
            if (index1 == 2 || index1 == 5) {
                index2--;
                index3 = 0;
            }
        }

        Grid[] result = { new Grid(result1), new Grid(result2), new Grid(result3) };
        return result;
    }

    /**
     * Réorganise la grille dans le sens inverse à reorganization(Grids[] _gs)
     *
     * @param _gs
     *
     * @return
     */
    public Grid[] reorganizationInverse(Grid[] _gs) {
        Tile[] result1 = new Tile[9];
        Tile[] result2 = new Tile[9];
        Tile[] result3 = new Tile[9];

        for (int grille = 1; grille < 4; grille++) {
            for (int ligne = 0; ligne < SIDE; ligne++) {
                for (int colonne = 0; colonne < SIDE; colonne++) {
                    if (grille == 1) {
                        if (ligne == 0) {
                            result3[colonne] = _gs[grille - 1].getGrid()[colonne];
                        } else if (ligne == 1) {
                            result2[colonne] = _gs[grille - 1].getGrid()[colonne + SIDE];
                        } else if (ligne == 2) {
                            result1[colonne] = _gs[grille - 1].getGrid()[colonne + SIDE * ligne];
                        }
                    } else if (grille == 2) {
                        if (ligne == 0) {
                            result3[colonne + SIDE] = _gs[grille - 1].getGrid()[colonne];
                        } else if (ligne == 1) {
                            result2[colonne + SIDE] = _gs[grille - 1].getGrid()[SIDE + colonne];
                        } else if (ligne == 2) {
                            result1[SIDE + colonne] = _gs[grille - 1].getGrid()[colonne + SIDE * ligne];
                        }
                    } else if (grille == 3) {
                        if (ligne == 0) {
                            result3[colonne + SIDE * 2] = _gs[grille - 1].getGrid()[colonne];
                        } else if (ligne == 1) {
                            result2[colonne + SIDE * 2] = _gs[grille - 1].getGrid()[colonne + SIDE];
                        } else if (ligne == 2) {
                            result1[ligne * SIDE + colonne] = _gs[grille - 1].getGrid()[colonne + SIDE * ligne];
                        }
                    }

                }
            }
        }
        Grid[] result = { new Grid(result1), new Grid(result2), new Grid(result3) };
        return result;
    }

    /**
     * Remplace les grilles
     *
     * @param tampon
     */
    private void override(Grid[] _tampon) {
        for (int index = 0; index < SIDE; index++) {
            grids[index].override(_tampon[index].getGrid());
        }
    }

    /**
     * Crée une copie des grilles
     *
     * @return
     */
    private Grid[] copy() {
        Grid[] g = { null, null, null };
        for (int index = 0; index < SIDE; index++) {
            g[index] = new Grid(grids[index].copy());
        }

        return g;
    }

    /**
     * Permet l'affichage des grilles en console
     */
    public void affichage() {
        String s = "";
        for (int i = 0; i < SIDE; i++) {
            if (i == 0) {
                s = s + "|--------------------|";
            } else {
                s = s + "  |--------------------|";
            }
        }

        for (int x = 0; x < SIDE; x++) {
            s = s + "\n|";
            for (int index = 0; index < SIDE; index++) {
                for (int y = 0; y < SIDE; y++) {
                    if (grids[index].getGrid()[x * SIDE + y] == null) {
                        s += "      ";
                    } else if (grids[index].getGrid()[x * SIDE + y].getValue() < 9) {
                        s += "  " + grids[index].getGrid()[x * SIDE + y].getValue() + "   ";
                    } else if (grids[index].getGrid()[x * SIDE + y].getValue() < 99) {
                        s += "  " + grids[index].getGrid()[x * SIDE + y].getValue() + "  ";
                    } else if (grids[index].getGrid()[x * SIDE + y].getValue() < 999) {
                        s += "  " + grids[index].getGrid()[x * SIDE + y].getValue() + " ";
                    } else if (grids[index].getGrid()[x * SIDE + y].getValue() < 9999) {
                        s += " " + grids[index].getGrid()[x * SIDE + y].getValue() + " ";
                    }
                    s = s + "|";
                }
                if (index + 1 < SIDE) {
                    s = s + "  |";
                }
            }
        }
        s = s + "\n";
        for (int i = 0; i < SIDE; i++) {
            if (i == 0) {
                s = s + "|--------------------|";
            } else {
                s = s + "  |--------------------|";
            }
        }
        System.out.println(s);
    }

    /**
     * Permet de vérifier si deux ensembles de grilles sont égales
     *
     * @param g
     *
     * @return
     */
    public boolean equals(Grid g[]) {
        for (int grille = 0; grille < SIDE; grille++) {
            for (int t = 0; t < SIZE; t++) {
                if (this.grids[grille].getGrid()[t] != null && g[grille].getGrid()[t] != null) {
                    if (!(this.grids[grille].getGrid()[t].compareValeur(g[grille].getGrid()[t]))) {
                        return false;
                    }
                } else if (this.grids[grille].getGrid()[t] == null && g[grille].getGrid()[t] != null) {
                    return false;
                } else if (this.grids[grille].getGrid()[t] != null && g[grille].getGrid()[t] == null) {
                    return false;
                }

            }
        }
        return true;
    }

    public void save() {
        String strDate = Calendar.getInstance().getTime().toString();

        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "2048_" + strDate + ".xt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.grids);
            oos.close();
            System.out.println("Sauvegarder : " + System.getProperty("user.dir") + "2048_" + strDate + ".xt");
        } catch (IOException _e) {
            _e.printStackTrace();
        }

    }


    public void load(String _path) {
        // Deserialization
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(_path);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            this.grids = (Grid[]) in.readObject();

            in.close();
            file.close();

            System.out.println("Chargement complet");


        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }
}
