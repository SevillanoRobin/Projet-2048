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
package recommender;

import model.Grids;
import recommender.strategies.IaRandom;
import recommender.strategies.IaScoreMax;
import recommender.strategies.IaStrategies;

import java.util.ArrayList;

/**
 *
 * @author utilisateur
 */
public class IaContext {

    private static String strategyName;
    private IaStrategies strategy;
    private final ArrayList<IaStrategies> listStrategy;
    private final ArrayList<String> listNameStrategy;

    public IaContext() {
        this.listStrategy = new ArrayList<>();
        this.listNameStrategy = new ArrayList<>();

        this.addListInit(new IaScoreMax(), "ScoreMax");
        this.addListInit(new IaRandom(), "Random");
    }

    /**
     * Adds a RecommenderStrategy and a String in the good list.
     */
    private void addListInit(IaStrategies classNameStrategy, String nameStrategy) {
        this.listStrategy.add(classNameStrategy);
        this.listNameStrategy.add(nameStrategy);
    }

    private String ia(Grids grids, int profondeurMax) {
         return this.strategy.ia(grids, profondeurMax);
    }
    
    /**
     * Changes the name of the strategy and calls getOneRecommendation of the right strategy.
     *
     * @param iaStrategy
     * @param grids
     * @param profondeurMax
     *
     * @return Result of getOneRecommendation.
     */
    public String setStrategyIa(String iaStrategy, Grids grids, int profondeurMax) {
        try {
            this.strategy = listStrategy.get(listNameStrategy.indexOf(iaStrategy)); 
        } catch (Exception ex) { // if the stratefy is not in the list of strategy
            this.strategy = listStrategy.get(listNameStrategy.indexOf("Random")); // find the default strategy
        }
        strategyName = iaStrategy;
        return this.ia(grids, profondeurMax);

    }
}
