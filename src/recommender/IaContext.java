/*
 * Copyright (c) 17/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package recommender;

import model.Grids;
import recommender.strategies.IaRandom;
import recommender.strategies.IaReductionNombreTuile;
import recommender.strategies.IaScoreMax;
import recommender.strategies.IaStrategies;

import java.util.ArrayList;

/**
 * Contexte de l'IA
 *
 * @author Robin
 */
public class IaContext {

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
        this.addListInit(new IaReductionNombreTuile(), "ReductionNombreTuile");
    }

    /**
     * Ajoute toutes les IA possibles dans deux listes.
     * Une pour le nom de classe et l'autre pour le nom de l'IA
     *
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
     * Change le nom de la stratégie et appelle la méthode ia de la bonne classe
     *
     * @param iaStrategy
     * @param grids
     * @param profondeurMax
     *
     * @return Result of getOneRecommendation.
     */
    public String setStrategyIa(String iaStrategy, Grids grids, int profondeurMax) {
        this.strategy = listStrategy.get(listNameStrategy.indexOf(iaStrategy));
        return this.ia(grids, profondeurMax);

    }
}
