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
package recommender.strategies;

import model.Grids;

/**
 *
 * @author utilisateur
 */
public interface IaStrategies {
    
    String ia(Grids grids, int profondeurMax);
}
