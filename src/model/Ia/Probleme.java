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

import java.util.ArrayList;

/**
 * Permet d'initialiser un problème.
 *
 * @author Robin
 */
public class Probleme {

    private Grids grids;
    private ArrayList<Action> listeactions;


    /**
     * Constructeur
     *
     * @param grids
     */
    public Probleme(Grids grids) {
        this.listeactions = setactions();
        this.grids = grids;
    }

    /**
     * Getter
     *
     * @return
     */
    public Grids getGrids() {
        return grids;
    }

    /**
     * Getter
     *
     * @return
     */
    public ArrayList<Action> getlisteactions() {
        return (listeactions);
    }


    /**
     * Ajoute toutes les actions possibles d'effectuer
     *
     * @return
     */
    public ArrayList<Action> setactions() {
        ArrayList<Action> la = new ArrayList<>();
        Action aa;
        aa = new Action();
        aa.setAction("Déplacement droite");
        la.add(aa);
        aa = new Action();
        aa.setAction("Déplacement gauche");
        la.add(aa);
        aa = new Action();
        aa.setAction("Déplacement haut");
        la.add(aa);
        aa = new Action();
        aa.setAction("Déplacement bas");
        la.add(aa);
        aa = new Action();
        aa.setAction("Déplacement étages supérieurs");
        la.add(aa);
        aa = new Action();
        aa.setAction("Déplacement étages inférieurs");
        la.add(aa);
        return (la);
    }
}