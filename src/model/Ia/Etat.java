/*
 * Copyright (c) 08/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model.Ia;

import model.Grids;

import static model.Parametres.*;

/**
 * Constituant d'un noeud
 *
 * @author Robin
 */
public class Etat {

    private Grids ensemble;
    private String deplacement; // lettre entree en ligne de commande
    private int scoreMax;
    private int scoreGrid;

    /**
     * Constructeur
     */
    public Etat() {
    }

    /**
     * Constructeur
     *
     * @param ensemble
     * @param deplacement
     */
    public Etat(Grids ensemble, String deplacement) {
        this.ensemble = new Grids(ensemble.getGrids());
        this.deplacement = deplacement;
        this.scoreMax = ensemble.best();
        this.scoreGrid = ensemble.scoreTotalGrille();
    }

    /**
     * Fait une copie d'un etat existant
     *
     * @param ee
     */
    public Etat(Etat ee) {
        this.ensemble = new Grids(ee.getGrids().getGrids());
        this.deplacement = ee.getDeplacement();
        this.scoreMax = this.ensemble.best();
        this.scoreGrid = this.ensemble.scoreTotalGrille();
    }

    /**
     * Getter
     *
     * @return
     */
    public Grids getGrids() {
        return this.ensemble;
    }

    /**
     * Getter
     *
     * @return
     */
    public String getDeplacement() {
        return this.deplacement;
    }

    /**
     * Getter
     *
     * @return
     */
    public int getScoreMax() {
        this.scoreMax = ensemble.best();
        return this.scoreMax;
    }

    /**
     * Getter
     *
     * @return
     */
    public int getScoreGrid() {
        this.scoreGrid = ensemble.scoreTotalGrille();
        return this.scoreGrid;
    }

    /**
     * Setter
     *
     * @param ensemble
     */
    public void setEnsembleGrile(Grids ensemble) {
        this.ensemble = ensemble;
    }

    /**
     * Setter
     *
     * @param dep
     */
    public void setDeplacement(String dep) {
        this.deplacement = dep;
    }

    /**
     * Setter
     *
     * @param _scoreMax
     */
    public void setScoreMax(int _scoreMax) {
        this.scoreMax = _scoreMax;
    }

    /// --- METHODES --- ///

    /**
     * Retoutne vrai si la valeur but est atteinte
     *
     * @param pb
     *
     * @return
     */
    public boolean estbut(Probleme pb) { //teste si l'état est égal à l'état but du problème
        return (pb.getGrids().best() == GOAL);
    }

    /**
     * Retourne vrai si l'etat passe en parametre est egal a celui qui appelle la methode
     *
     * @param e
     *
     * @return
     */
    public boolean estegal(Etat e) {
        return this.ensemble == e.getGrids() && this.deplacement.equals(e.getDeplacement());
    }

    /**
     * Applique l'action passe en parametre
     *
     * @param a
     * @param grids
     *
     * @return
     */
    public Etat AppliqueAction(Action a, Grids grids) {
        Etat e;
        switch (a.getAction()) {
            case "Déplacement droite":
                e = new Etat(grids, "d");
                if (e.valide("d")) {
                    e.getGrids().move(true, RIGHT);
                    e = new Etat(e.getGrids(), "d");
                } else {
                    e = null;
                }
                break;
            case "Déplacement gauche":
                e = new Etat(grids, "q");
                if (e.valide("q")) {
                    e.getGrids().move(true, LEFT);
                    e = new Etat(e.getGrids(), "q");
                } else {
                    e = null;
                }
                break;
            case "Déplacement haut":
                e = new Etat(grids, "z");
                if (e.valide("z")) {
                    e.getGrids().move(true, UP);
                    e = new Etat(e.getGrids(), "z");
                } else {
                    e = null;
                }
                break;
            case "Déplacement bas":
                e = new Etat(grids, "s");
                if (e.valide("s")) {
                    e.getGrids().move(true, DOWN);
                    e = new Etat(e.getGrids(), "s");
                } else {
                    e = null;
                }
                break;
            case "Déplacement etages superieurs":
                e = new Etat(grids, "r");
                if (e.valide("r")) {
                    e.getGrids().move(true, FRONT);
                    e = new Etat(e.getGrids(), "r");
                } else {
                    e = null;
                }
                break;
            default:
                e = new Etat(grids, "f");
                if (e.valide("f")) {
                    e.getGrids().move(true, BACK);
                    e = new Etat(e.getGrids(), "f");
                } else {
                    e = null;
                }
                break;
        }
        return (e);
    }

    /**
     * Retourne vrai si la direction passe en parametre est possible a executer
     *
     * @param direction
     *
     * @return
     */
    public boolean valide(String direction) {
        boolean valide = false;
        int indice = 0;
        switch (direction) {
            case "d":
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.right(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
            case "q":
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.left(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
            case "z":
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.up(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
            case "s":
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.down(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
            case "r":
                this.ensemble = new Grids(this.ensemble.reorganization(this.ensemble.getGrids()));
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.down(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
            default:
                this.ensemble = new Grids(this.ensemble.reorganization(this.ensemble.getGrids()));
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.up(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
        }
        return valide;
    }

} 
