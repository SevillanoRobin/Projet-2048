/*
 * Copyright (c) 07/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Ia;

import model.Grids;

import static model.Parametres.*;

/**
 *
 * @author utilisateur
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
     * @param ensemble
     * @param deplacement
     */
    public Etat(Grids ensemble, String deplacement) {
        this.ensemble = new Grids(ensemble);
        this.deplacement = deplacement;
        this.scoreMax = ensemble.best();
        this.scoreGrid = ensemble.scoreTotalGrille();
    }

    /**
     * Fait une copie d'un etat existant
     * @param ee
     */
    public Etat(Etat ee) {
        this.ensemble = new Grids(ee.getGrids());
        this.deplacement = ee.getDeplacement();
        this.scoreMax = ee.getScoreMax();
        this.scoreGrid = ee.getScoreGrid();
    }

    /**
     * Getter
     * @return
     */
    public Grids getGrids() {
        return this.ensemble;
    }

    /**
     * Getter
     * @return
     */
    public String getDeplacement() {
        return this.deplacement;
    }

    /**
     * Getter
     * @return
     */
    public int getScoreMax() {
        return this.scoreMax;
    }

    /**
     * Getter
     * @return
     */
    public int getScoreGrid() {
        return this.scoreGrid;
    }

    /**
     * Setter
     * @param ensemble
     */
    public void setEnsembleGrile(Grids ensemble) {
        this.ensemble = ensemble;
    }

    /**
     * Setter
     * @param dep
     */
    public void setDeplacement(String dep) {
        this.deplacement = dep;
    }

    /**
     * Setter
     * @param scoreMax
     */
    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    //méthodes

    /**
     * Retoutne vrai si la valeur but est atteinte 
     * @param pb
     * @return
     */
    public boolean estbut(Probleme pb) { //teste si l'état est égal à l'état but du problème
        return (pb.getGrids().best() == GOAL);
    }

    /**
     * Retourne vrai si l'etat passe en parametre est egal a celui qui appelle la methode
     * @param e
     * @return
     */
    public boolean estegal(Etat e) {
        return this.ensemble == e.getGrids() && this.deplacement.equals(e.getDeplacement());
    }
    /**
     * Applique l'action passe en parametre
     * @param a
     * @param grids
     * @return
     */
    public Etat AppliqueAction(Action a, Grids grids) {
        Etat e;
        switch (a.getAction()) {
            case "Deplacement droite":
                e = new Etat(grids, "d");
                if (e.valide("d")) {
                    e.getGrids().move(RIGHT);
                    e = new Etat(this.ensemble, "d");
                } else {
                    e = null;
                }
                break;
            case "Deplacement gauche":
                e = new Etat(grids, "q");
                if (e.valide("q")) {
                    e.getGrids().move(LEFT);
                    e = new Etat(this.ensemble, "q");
                } else {
                    e = null;
                }
                break;
            case "Deplacement haut":
                e = new Etat(grids, "z");
                if (e.valide("z")) {
                    e.getGrids().move(UP);
                    e = new Etat(this.ensemble, "z");
                } else {
                    e = null;
                }
                break;
            case "Deplacement bas":
                e = new Etat(grids, "s");
                if (e.valide("s")) {
                    e.getGrids().move(DOWN);
                    e = new Etat(this.ensemble, "s");
                } else {
                    e = null;
                }
                break;
            case "Deplacement etages superieurs":
                e = new Etat(grids, "r");
                if (e.valide("r")) {
                    e.getGrids().move(FRONT);
                    e = new Etat(this.ensemble, "r");
                } else {
                    e = null;
                }
                break;
            default:
                e = new Etat(grids, "f");
                if (e.valide("f")) {
                    e.getGrids().move(BACK);
                    e = new Etat(this.ensemble, "f");
                } else {
                    e = null;
                }
                break;
        }
        return (e);
    }

    /**
     * Retourne vrai si la direction passe en parametre est possible a executer
     * @param direction
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
                this.ensemble.reorganization(this.ensemble.getGrids());
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.down(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
            default:
                this.ensemble.reorganization(this.ensemble.getGrids());
                while (!valide && indice < this.ensemble.getGrids().length) {
                    valide = this.ensemble.up(this.ensemble.getGrids()[indice]);
                    indice++;
                }
                break;
        }
        return valide;
    }

} 
