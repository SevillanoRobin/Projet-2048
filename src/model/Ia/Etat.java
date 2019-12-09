/*
 * Copyright (c) 09/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model.Ia;

import model.Grid;
import model.Grids;
import model.Parameters;

/**
 * Constituant d'un noeud
 *
 * @author Robin
 */
public class Etat {

    private Grids ensemble;
    private String deplacement; // lettre entree en ligne de commande
    private int scoreMax;

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
    }

    /**
     * Fait une copie d'un etat existant
     *
     * @param ee
     */
    Etat(Etat ee) {
        this.ensemble = new Grids(ee.getGrids().getGrids());
        this.deplacement = ee.getDeplacement();
        this.scoreMax = this.ensemble.best();
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
    String getDeplacement() {
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

    /// --- METHODES --- ///

    /**
     * Retoutne vrai si la valeur but est atteinte
     *
     * @param pb
     *
     * @return
     */
    public boolean estbut(Probleme pb) { //teste si l'état est égal à l'état but du problème
        return (pb.getGrids().best() == Parameters.GOAL);
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
                    e.getGrids().move(true, Parameters.RIGHT);
                    e = new Etat(e.getGrids(), "d");
                } else {
                    e = null;
                }
                break;
            case "Déplacement gauche":
                e = new Etat(grids, "q");
                if (e.valide("q")) {
                    e.getGrids().move(true, Parameters.LEFT);
                    e = new Etat(e.getGrids(), "q");
                } else {
                    e = null;
                }
                break;
            case "Déplacement haut":
                e = new Etat(grids, "z");
                if (e.valide("z")) {
                    e.getGrids().move(true, Parameters.UP);
                    e = new Etat(e.getGrids(), "z");
                } else {
                    e = null;
                }
                break;
            case "Déplacement bas":
                e = new Etat(grids, "s");
                if (e.valide("s")) {
                    e.getGrids().move(true, Parameters.DOWN);
                    e = new Etat(e.getGrids(), "s");
                } else {
                    e = null;
                }
                break;
            case "Déplacement etages superieurs":
                e = new Etat(grids, "r");
                if (e.valide("r")) {
                    e.getGrids().move(true, Parameters.FRONT);
                    e = new Etat(e.getGrids(), "r");
                } else {
                    e = null;
                }
                break;
            default:
                e = new Etat(grids, "f");
                if (e.valide("f")) {
                    e.getGrids().move(true, Parameters.BACK);
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
    private boolean valide(String direction) {
        boolean valide = false;
        int indice = 0;
        Grid[] grids = this.ensemble.getGrids();
        switch (direction) {
            case "d":
                while (!valide && indice < grids.length) {
                    valide = this.ensemble.right(grids[indice]);
                    indice++;
                }
                break;
            case "q":
                while (!valide && indice < grids.length) {
                    valide = this.ensemble.left(grids[indice]);
                    indice++;
                }
                break;
            case "z":
                while (!valide && indice < grids.length) {
                    valide = this.ensemble.up(grids[indice]);
                    indice++;
                }
                break;
            case "s":
                while (!valide && indice < grids.length) {
                    valide = this.ensemble.down(grids[indice]);
                    indice++;
                }
                break;
            case "r":
                this.ensemble = new Grids(this.ensemble.reorganization(grids));
                while (!valide && indice < grids.length) {
                    valide = this.ensemble.down(grids[indice]);
                    indice++;
                }
                break;
            default:
                this.ensemble = new Grids(this.ensemble.reorganization(grids));
                while (!valide && indice < grids.length) {
                    valide = this.ensemble.up(grids[indice]);
                    indice++;
                }
                break;
        }
        return valide;
    }

} 
