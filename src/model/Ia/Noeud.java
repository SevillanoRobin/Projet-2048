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

public class Noeud {

    private Etat e;
    private final ArrayList<Action> sol;

    //constructeurs
    private Noeud() {
        this.e = new Etat();
        this.sol = null;
    }

    public Noeud(Etat ee, ArrayList<Action> aa) {
        this.e = new Etat(ee);
        this.sol = new ArrayList<Action>();
        if (aa != null) {
            this.sol.addAll(aa);
        }
    }

    //getteurs et setteurs

    public Etat getetat() {
        return (this.e);
    }

    public ArrayList<Action> getlisteaction() {
        return (this.sol);
    }

    private void setetat(Etat ee) {
        this.e = ee;
    }

    //méthodes
    public void ajoutaction(Action aa) {
        this.sol.add(aa);
    }

    public Noeud copy() { // renvoie une copie du noeud
        Noeud n = new Noeud();
        n.setetat(this.getetat());
        n.sol.addAll(sol);
        return (n);
    }

    @Override
    public String toString() {
        return "Noeud{" + "e=" + e.getDeplacement() + ", sol=" + sol + '}';
    }
    
    
}

