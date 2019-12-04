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

import java.util.ArrayList;

/**
 *
 * @author utilisateur
 */
public class Probleme {

    private final Grids grids;
    private final ArrayList<Action> listeactions;

    //constructeur
    public Probleme(Grids grids) {
        this.listeactions = setactions();
        this.grids = grids;

    }

    public Grids getGrids() {
        return grids;
    }

    public ArrayList<Action> getlisteactions() {
        return (listeactions);
    }

    //initialisation 
    private ArrayList<Action> setactions() {
        ArrayList<Action> la = new ArrayList<Action>();
        Action aa;
        aa = new Action();
        aa.setAction("Deplacement droite");
        la.add(aa);
        aa = new Action();
        aa.setAction("Deplacement gauche");
        la.add(aa);
        aa = new Action();
        aa.setAction("Deplacement haut");
        la.add(aa);
        aa = new Action();
        aa.setAction("Deplacement bas");
        la.add(aa);
        aa = new Action();
        aa.setAction("Deplacement etages superieurs");
        la.add(aa);
        aa = new Action();
        aa.setAction("Deplacement etages inferieurs");
        la.add(aa);
        return (la);
    }
}