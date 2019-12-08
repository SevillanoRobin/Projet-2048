/*
 * Copyright (c) 08/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package recommender.strategies;

import model.Grids;

/**
 * Interface IA
 *
 * @author Robin
 */
public interface IaStrategies {

    /**
     * Retourne le déplacement effectue de l'ia choisi
     *
     * @param grids
     * @param profondeurMax
     *
     * @return
     */
    String ia(Grids grids, int profondeurMax);
}
