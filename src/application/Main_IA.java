/*
 * Copyright (c) 09/12/2019
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
 * @author Robin
 */
public class Main_IA {

    public static void main(String[] args) {
        IaContext ia = new IaContext();
        Grids grids = new Grids();
        grids.affichage();

        while (grids.stillPlayeable() || !grids.victory()) {
            System.out.println(ia.setStrategyIa("ReductionNombreTuile", grids, 1));
            grids.affichage();
        }
    }
}
