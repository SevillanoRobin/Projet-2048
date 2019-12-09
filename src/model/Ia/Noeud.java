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

import java.util.ArrayList;

/**
 * Noeud constituant d'un graphe
 *
 * @author Robin
 */
public class Noeud {

    private Etat e;
    private ArrayList<Action> sol;


    /**
     * Fait une copie d'un noeud deja existant
     *
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
     *
     * @return
     */
    public Etat getetat() {
        return (this.e);
    }

    /**
     * Getter
     *
     * @return
     */
    public ArrayList<Action> getlisteaction() {
        return (this.sol);
    }


    /**
     * Ajoute une action dans les solutions
     *
     * @param aa
     */
    public void ajoutaction(Action aa) {
        this.sol.add(aa);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Noeud{" + "e=" + e.getDeplacement() + ", sol=" + sol + '}';
    }


}

