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

import java.util.ArrayList;

/**
 *
 * @author utilisateur
 */
public class Noeud {

    private Etat e;
    private final ArrayList<Action> sol;


    /**
     * Constructeur
     */
    public Noeud() {
        this.e = new Etat();
        this.sol = null;
    }

    /**
     * Fait une copie d'un noeud deja existant 
     * @param ee
     * @param aa
     */
    public Noeud(Etat ee, ArrayList<Action> aa) {
        this.e = new Etat(ee);
        this.sol = new ArrayList<>();
        if (aa != null) {
            this.sol.addAll(aa);
        }
    }


    /**
     * Getter
     * @return
     */

    public Etat getetat() {
        return (this.e);
    }

    /**
     * Getter
     * @return
     */
    public ArrayList<Action> getlisteaction() {
        return (this.sol);
    }

    /**
     * Setter
     * @param ee
     */
    public void setetat(Etat ee) {
        this.e = ee;
    }


    /**
     * Ajoute une action dans les solutions
     * @param aa
     */
    public void ajoutaction(Action aa) {
        this.sol.add(aa);
    }


    @Override
    public String toString() {
        return "Noeud{" + "e=" + e.getDeplacement() + ", sol=" + sol + '}';
    }
    
    
}

