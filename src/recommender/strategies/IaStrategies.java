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
package recommender.strategies;

import model.Grids;

/**
 *
 * @author utilisateur
 */
public interface IaStrategies {
    
    /**
     * Retourne le deplacement effectue de l'ia choisi
     * @param grids
     * @param profondeurMax
     * @return
     */
    String ia(Grids grids, int profondeurMax);
}
