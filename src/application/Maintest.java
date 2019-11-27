/*
 * Copyright (c) 27/11/2019
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
package application;

import model.Grids;
import recommender.IaContext;

/**
 *
 * @author utilisateur
 */
public class Maintest {

    public static void main(String[] args) {
        IaContext ia = new IaContext();
        Grids grids = new Grids();
        for (int i=0; i < 100; i++) {
            grids.affichage();
            System.out.println(ia.setStrategyIa("Random", grids, 1));
            grids.affichage();
        }

    }
}
