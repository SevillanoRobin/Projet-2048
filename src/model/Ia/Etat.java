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

    public Etat() {}
    public Etat(Grids ensemble, String deplacement) {
        this.ensemble = new Grids(ensemble);
        this.deplacement = deplacement;
        this.scoreMax = ensemble.best();
        this.scoreGrid = ensemble.scoreTotalGrille();
    }

    
    public Etat(Etat ee) {
        this.ensemble = new Grids(ee.getGrids());
        this.deplacement = ee.getDeplacement();
        this.scoreMax = ee.getScoreMax();
        this.scoreGrid = ee.getScoreGrid();
    }

    
    /*public String toString() {
        return "Ensemble grille : " + this.ensemble + " deplacmeent : " + this.deplacement + " score Max : " + this.scoreMax + " score Grid : " + this.scoreGrid;
    }*/

    
    //getteurs et setteurs
    public Grids getGrids() {
        return this.ensemble;
    }

    
    public String getDeplacement() {
        return this.deplacement;
    }

    
    public int getScoreMax() {
        return this.scoreMax;
    }

    
    private int getScoreGrid() {
        return this.scoreGrid;
    }
    

    public void setEnsembleGrile(Grids ensemble) {
        this.ensemble = ensemble;
    }

    
    public void setDeplacement(String dep) {
        this.deplacement = dep;
    }
    

    public void setScoreMax(int _scoreMax) {
        this.scoreMax = _scoreMax;
    }

    
    //méthodes
    public boolean estbut(Probleme pb) { //teste si l'état est égal à l'état but du problème
        return (pb.getGrids().best() == GOAL);
    }
    

    public boolean estegal(Etat e) {
        return this.ensemble == e.getGrids() && this.deplacement.equals(e.getDeplacement());
    }

    
    /*public void affiche() {
        System.out.println(this.ensemble + " " + this.deplacement + " " + this.scoreMax);
    }*/

    
    // applique une action a à l'état courant. Le probleme est passé en paramètre pour connaitre la contenance maximale de chaque cruche
    public Etat AppliqueAction(Action a, Grids grids) {
        Etat e;
        if (a.getAction().equals("Deplacement droite")) {
            e = new Etat(grids, "d");
            if (e.valide("d")) {
                e.getGrids().move(RIGHT);
                e = new Etat(this.ensemble, "d");
            } else {
                e = null;
            }
        } else if (a.getAction().equals("Deplacement gauche")) {
            e = new Etat(grids, "q");
            if (e.valide("q")) {
                e.getGrids().move(LEFT);
                e = new Etat(this.ensemble, "q");
            } else {
                e = null;
            }
        } else if (a.getAction().equals("Deplacement haut")) {
            e = new Etat(grids, "z");
            if (e.valide("z")) {
                e.getGrids().move(UP);
                e = new Etat(this.ensemble, "z");
            } else {
                e = null;
            }
        } else if (a.getAction().equals("Deplacement bas")) {
            e = new Etat(grids, "s");
            if (e.valide("s")) {
                e.getGrids().move(DOWN);
                e = new Etat(this.ensemble, "s");
            } else {
                e = null;
            }
        } else if (a.getAction().equals("Deplacement etages superieurs")) {
            e = new Etat(grids, "r");
            if (e.valide("r")) {
                e.getGrids().move(FRONT);
                e = new Etat(this.ensemble, "r");
            } else {
                e = null;
            }
        } else {
            e = new Etat(grids, "f");
            if (e.valide("f")) {
                e.getGrids().move(BACK);
                e = new Etat(this.ensemble, "f");
            } else {
                e = null;
            }
        }
        return (e);
    }

    
    private boolean valide(String direction) {
        boolean valide = false;
        int indice = 0;
        if (direction.equals("d")) {
            while (!valide && indice < this.ensemble.getGrids().length) {
                valide = this.ensemble.right(this.ensemble.getGrids()[indice]);
                indice++;
            }
        } else if (direction.equals("q")) {
            while (!valide && indice < this.ensemble.getGrids().length) {
                valide = this.ensemble.left(this.ensemble.getGrids()[indice]);
                indice++;
            }
        } else if (direction.equals("z")) {
            while (!valide && indice < this.ensemble.getGrids().length) {
                valide = this.ensemble.up(this.ensemble.getGrids()[indice]);
                indice++;
            }
        } else if (direction.equals("s")) {
            while (!valide && indice < this.ensemble.getGrids().length) {
                valide = this.ensemble.down(this.ensemble.getGrids()[indice]);
                indice++;
            }
        } else if (direction.equals("r")) {
            this.ensemble.reorganization(this.ensemble.getGrids());
            while (!valide && indice < this.ensemble.getGrids().length) {
                valide = this.ensemble.down(this.ensemble.getGrids()[indice]);
                indice++;
            }
        } else {
            this.ensemble.reorganization(this.ensemble.getGrids());
            while (!valide && indice < this.ensemble.getGrids().length) {
                valide = this.ensemble.up(this.ensemble.getGrids()[indice]);
                indice++;
            }
        }
        return valide;
    }

}