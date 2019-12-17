/*
 * Copyright (c) 16/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */
package model;

import javafx.event.EventHandler;
import javafx.util.Callback;
import model.events.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Grids extends AbstractModelEventEmitter implements Serializable, ModelEventEmitter {
    private Grid[] grids;

    // TODO: Repasser à faux lors d'un chargement de grille.
    private boolean didFireStartEvent;

    /**
     * Constructeur de la matrice 3D
     */
    public Grids() {
        grids = new Grid[3];

        for (int index = 0; index < 3; index++) {
            grids[index] = new Grid();
        }
        this.didFireStartEvent = false;
    }

    public Grids(Grids grids) {
        this.grids = new Grid[Parameters.SIDE];

        for (int index = 0; index < Parameters.SIDE; index++) {
            this.grids[index] = new Grid(grids.grids[index].copy());
        }

        // Flux des événements.
        this.addListeners(grids.getListeners());
    }

    /**
     * Creé un nouvelle ensemble de 3 grilles tout en conservant les propriétés de la grille passée en paramètre
     *
     * @param grid
     */
    public Grids(Grid[] grid) {
        this.grids = new Grid[Parameters.SIDE];
        for (int index = 0; index < Parameters.SIDE; index++) {
            this.grids[index] = new Grid(grid[index].copy());
        }
    }

    /**
     * Constructeur par argument.
     *
     * @param _listeners
     *         {@link EventHandler EventHandlers} associés à cette instance.
     */
    public Grids(ArrayList<EventHandler<ModelEvent>> _listeners) {
        // Appel au constructeur par défaut.
        this();

        // Flux des événements.
        this.addListeners(_listeners);
    }

    /**
     * Meilleur score
     *
     * @return
     */
    public int best() {
        int score = 0;

        for (Grid g : grids) {
            if (g.getBestValue() > score) {
                score = g.getBestValue();

                if (this.hasListeners()) { // !b
                    this.fireScoreEvent(ModelEventSubtype.BEST_VALUE_CHANGE_EVENT, score);
                }
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
        return scoreTotalGrille_Core(1);
    }

    /**
     * Renvoie un score supérieur au score total car le poids des tuiles est différent selon leur valeur
     *
     * @return
     */
    public int scoreTotalGrilleMajore() {
        return scoreTotalGrille_Core(1.5);
    }

    private int scoreTotalGrille_Core(double _multiplicator) {
        int score = 0;
        for (Grid g : this.grids) {
            for (Tile t : g.getGrid()) {
                if (t != null) {
                    score += t.getValue() * _multiplicator;
                }
            }
        }
        return score;
    }

    /**
     * Verifie si les 3 grilles sont bloquées
     * <p>
     * Vérifie au travers d'une grille réorganisée (temp) et des grilles courantes.
     *
     * @return boolean
     */
    public boolean stillPlayeable() {
        int nbGrids = this.grids.length;
        int nbBoolean = 0;

        Grid[] temp = reorganization(this.grids);

        for (int i = 0; i < nbGrids; i++) {
            if (temp[i].lose()) {
                nbBoolean++;
            }

            if (this.grids[i].lose()) {
                nbBoolean++;
            }
        }

        boolean res = nbBoolean != nbGrids * 2;

        if (!res && this.hasListeners()) {// !b
            this.fireGeneralistEvent(ModelEventSubtype.LOSE_EVENT);
        }

        return res;
    }

    /**
     * Verifie si la valeur de la tuile la plus élevé est égale ou supérieur à la valeur but
     *
     * @return
     */
    public boolean victory() {
        if (best() >= Parameters.GOAL) {
            if (this.hasListeners()) { // !b
                this.fireGeneralistEvent(ModelEventSubtype.WIN_EVENT);
            }

            return true;
        }
        return false;
    }

    /**
     * Gere les depacements des grilles
     *
     * @param simulation
     * @param _d
     */
    public void move(boolean simulation, int _d) {
        boolean[] verif = { false, false, false };

        if (_d == Parameters.FRONT) {
            moveBackOrForth(verif, Parameters.DOWN);

        } else if (_d == Parameters.BACK) {
            moveBackOrForth(verif, Parameters.UP);

        } else {
            for (int index = 0; index < Parameters.SIDE; index++) {
                verif[index] = grids[index].move(_d);
            }
        }

        for (boolean b : verif) {
            if (b) {
                for (int i = 0; i < 100; i++) {
                    int random = (int) (Math.random() * this.grids.length);
                    Grid randomGrid = this.grids[random];

                    for (int j = 0; j < randomGrid.getGrid().length; j++) {
                        if (randomGrid.getGrid()[j] == null && !simulation) {
                            randomGrid.newTile();
                            return;
                        }
                    }
                }
            }
        }
    }

    private void moveBackOrForth(boolean[] _verif, int _direction) {
        Grids tamp = new Grids(reorganization(this.grids));
        for (int index = 0; index < Parameters.SIDE; index++) {
            _verif[index] = tamp.getGrids()[index].move(_direction);
        }

        tamp.setGrids(this.reorganizationInverse(tamp.getGrids())); //on remet la matrice dans le bon sens
        if (this.equals(tamp.grids)) {
            _verif[0] = false;
        } else {
            this.grids = tamp.grids;
            _verif[0] = true;
        }
    }


    /**
     * Déplacement des tuiles de la grille vers la gauche
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la gauche
     */
    public boolean left(Grid _g) {
        return _g.left();
    }

    /**
     * Déplacement des tuiles de la grille vers la droite
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la droite
     */
    public boolean right(Grid _g) {
        return _g.right();
    }

    /**
     * Déplacement des tuiles de la grille vers le haut
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le haut
     */
    public boolean up(Grid _g) {
        return _g.up();
    }

    /**
     * Déplacement des tuiles de la grille vers le bas
     *
     * @param _g
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le bas
     */
    public boolean down(Grid _g) {
        return _g.down();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Grid g : grids) {
            s.append(g.toString());
        }
        return s.toString();
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
        for (int index1 = 0; index1 < Parameters.SIZE; index1++) {
            if (_gs[index2].getGrid()[index3] != null) {
                result1[index1] = _gs[index2].getGrid()[index3];
            }
            if (_gs[index2].getGrid()[index3 + Parameters.SIDE] != null) {
                result2[index1] = _gs[index2].getGrid()[index3 + Parameters.SIDE];
            }
            if (_gs[index2].getGrid()[index3 + (Parameters.SIDE * 2)] != null) {
                result3[index1] = _gs[index2].getGrid()[index3 + (Parameters.SIDE * 2)];
            }

            index3++;
            if (index1 == 2 || index1 == 5) {
                index2--;
                index3 = 0;
            }
        }

        return new Grid[] { new Grid(result1), new Grid(result2), new Grid(result3) };
    }

    /**
     * Réorganise la grille dans le sens inverse à reorganization(Grids[] _gs)
     *
     * @param _gs
     *
     * @return
     */
    private Grid[] reorganizationInverse(Grid[] _gs) {
        Tile[] result1 = new Tile[9];
        Tile[] result2 = new Tile[9];
        Tile[] result3 = new Tile[9];

        for (int grille = 1; grille < 4; grille++) {
            for (int ligne = 0; ligne < Parameters.SIDE; ligne++) {
                for (int colonne = 0; colonne < Parameters.SIDE; colonne++) {
                    if (grille == 1) {
                        if (ligne == 0) {
                            result3[colonne] = _gs[grille - 1].getGrid()[colonne];
                        } else if (ligne == 1) {
                            result2[colonne] = _gs[grille - 1].getGrid()[colonne + Parameters.SIDE];
                        } else {
                            result1[colonne] = _gs[grille - 1].getGrid()[colonne + Parameters.SIDE * ligne];
                        }
                    } else if (grille == 2) {
                        if (ligne == 0) {
                            result3[colonne + Parameters.SIDE] = _gs[grille - 1].getGrid()[colonne];
                        } else if (ligne == 1) {
                            result2[colonne + Parameters.SIDE] = _gs[grille - 1].getGrid()[Parameters.SIDE + colonne];
                        } else {
                            result1[Parameters.SIDE + colonne] = _gs[grille - 1].getGrid()[colonne + Parameters.SIDE * ligne];
                        }
                    } else {
                        if (ligne == 0) {
                            result3[colonne + Parameters.SIDE * 2] = _gs[grille - 1].getGrid()[colonne];
                        } else if (ligne == 1) {
                            result2[colonne + Parameters.SIDE * 2] = _gs[grille - 1].getGrid()[colonne + Parameters.SIDE];
                        } else {
                            result1[ligne * Parameters.SIDE + colonne] = _gs[grille - 1].getGrid()[colonne + Parameters.SIDE * ligne];
                        }
                    }

                }
            }
        }
        return new Grid[] { new Grid(result1), new Grid(result2), new Grid(result3) };
    }

    /**
     * Permet l'affichage des grilles en console
     */
    public void affichage() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < Parameters.SIDE; i++) {
            if (i == 0) {
                s.append("|--------------------|");
            } else {
                s.append("  |--------------------|");
            }
        }

        for (int x = 0; x < Parameters.SIDE; x++) {
            s.append("\n|");
            for (int index = 0; index < Parameters.SIDE; index++) {
                for (int y = 0; y < Parameters.SIDE; y++) {
                    if (grids[index].getGrid()[x * Parameters.SIDE + y] == null) {
                        s.append("      ");
                    } else {
                        int givenGridValue = grids[index].getGrid()[x * Parameters.SIDE + y].getValue();
                        if (givenGridValue < 9) {
                            s.append("  ").append(givenGridValue).append("   ");
                        } else if (givenGridValue < 99) {
                            s.append("  ").append(givenGridValue).append("  ");
                        } else if (givenGridValue < 999) {
                            s.append("  ").append(givenGridValue).append(" ");
                        } else if (givenGridValue < 9999) {
                            s.append(" ").append(givenGridValue).append(" ");
                        }
                    }
                    s.append("|");
                }
                if (index + 1 < Parameters.SIDE) {
                    s.append("  |");
                }
            }
        }
        s.append("\n");
        for (int i = 0; i < Parameters.SIDE; i++) {
            if (i == 0) {
                s.append("|--------------------|");
            } else {
                s.append("  |--------------------|");
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
    public boolean equals(Grid[] g) {
        for (int grille = 0; grille < Parameters.SIDE; grille++) {
            for (int t = 0; t < Parameters.SIZE; t++) {
                Tile tile1 = this.grids[grille].getGrid()[t];
                Tile tile2 = g[grille].getGrid()[t];

                if (tile1 != null && tile2 != null) {
                    if (!(tile1.compareValeur(tile2))) {
                        return false;
                    }
                } else if (tile1 == null && tile2 != null) {
                    return false;
                } else if (tile1 != null) {
                    return false;
                }

            }
        }
        return true;
    }

    public void save() {
        this.save((_strDate) -> {
            System.out.println("Sauvegarder : " + System.getProperty("user.dir") + "2048_" + _strDate + ".xt");
            return null;
        });
    }

    public void save(Callback<String, Void> _action) {
        String strDate = Calendar.getInstance().getTime().toString();
        strDate = strDate.replaceAll(" ", "\\ ").replaceAll(":", ".");

        String baseDir = System.getProperty("user.dir").replaceAll(" ", "\\ ");
        String filePath = baseDir + "/save/2048_" + strDate + ".xt";

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.grids);
            oos.close();
            _action.call(filePath);
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

    /// --- ACCESSEURS & MODIFICATEURS --- ///

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

    // --- Gestion du flux des événements --- //

    private void addListeners(ArrayList<EventHandler<ModelEvent>> _listeners) {
        for (EventHandler<ModelEvent> listener : _listeners) {
            this.addListener(listener);
        }
    }

    public void fireStartEvent() {
        if (!this.didFireStartEvent) {
            this.fireGeneralistEvent(ModelEventSubtype.START_EVENT);
        } else {
            System.err.println("Trying to fire a second start event.");
        }
    }

    /**
     * Envoit un événement de {@link ModelEventCategory type "général"} selon un sous-type donné.
     *
     * @param _eventSubtype
     *         Sous-type d'événement à envoyer.
     */
    private void fireGeneralistEvent(ModelEventSubtype _eventSubtype) {
        ModelEvent event = ModelEvent.createInstance(_eventSubtype);
        this.fireEvent(event);
    }

    /**
     * Envoit un événement de {@link ModelEventCategory type "score"} selon des paramètres.
     *
     * @param _eventSubtype
     *         Sous-type d'événement à envoyer.
     * @param _newScore
     *         Nouveau score.
     */
    private void fireScoreEvent(ModelEventSubtype _eventSubtype, int _newScore) {
        ModelEvent event = ModelScoreEvent.createInstance(_eventSubtype, _newScore);
        if (event == null) {
            return;
        }
        this.fireEvent(event);
    }
}
