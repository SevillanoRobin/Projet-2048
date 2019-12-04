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

    public static String strategyName;
    private IaStrategies strategy;
    private ArrayList<IaStrategies> listStrategy;
    private ArrayList<String> listNameStrategy;

    /**
     * Constructeur
     */
    public IaContext() {
        this.listStrategy = new ArrayList<>();
        this.listNameStrategy = new ArrayList<>();

        this.addListInit(new IaScoreMax(), "ScoreMax");
        this.addListInit(new IaRandom(), "Random");
    }

    /**
     * Ajoute toutes les IA possibles dans deux listes. Une pour le nom de classe et l'autre pour le nom de l'IA
     * @param classNameStrategy
     * @param nameStrategy
     */
    private void addListInit(IaStrategies classNameStrategy, String nameStrategy) {
        this.listStrategy.add(classNameStrategy);
        this.listNameStrategy.add(nameStrategy);
    }

    private String ia(Grids grids, int profondeurMax) {
         return this.strategy.ia(grids, profondeurMax);
    }
    
        /**
     * Change le nom de la strategie et appelle la methode ia de la bonne classe
     * @param iaStrategy
     * @param grids
     * @param profondeurMax
     * @return Result of getOneRecommendation.
     */
    public String setStrategyIa(String iaStrategy, Grids grids, int profondeurMax) {
        try {
            this.strategy = listStrategy.get(listNameStrategy.indexOf(iaStrategy)); 
        } catch (Exception ex) { // Si la strategie entree en parametre n'a pas ete trouve
            this.strategy = listStrategy.get(listNameStrategy.indexOf("Random")); // Strategie par default
        }
        strategyName = iaStrategy;
        return this.ia(grids, profondeurMax);

    }
}
