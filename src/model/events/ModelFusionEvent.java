/*
 * Copyright (c) 15/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model.events;

/**
 * Classe ModelFusionEvent.
 * <p>
 * Cette classe est utilisée pour les éléments de fusion, afin de donner des détails sur les positions des tuiles
 * concernées.
 * <p>
 * Cette classe concerne donc les événements de fusion.
 *
 * @see ModelEventCategory        Les catégories d'événements ; doit être {@link ModelEventCategory#FUSION_RELATED} ici.
 * @see javafx.event.EventHandler Les "listeners", càd les récepteurs des événements.
 * @see ModelEventEmitter   Les émetteurs d'événements.
 */
public class ModelFusionEvent extends ModelEvent {

    /** Indice de la première tuile. */
    private final int initialInd;
    /** Indice de la tuile résultante / de la deuxième tuile. */
    private final int newInd;

    /**
     * Constructeur privé.
     * <p>
     * Depuis une autre classe, il faut utiliser {@link ModelFusionEvent#createInstance(ModelEventSubtype, int, int)}
     * afin de
     * vérifier la catégorie de l'événement, ainsi que d'éviter que le constructeur lance des exceptions.
     *
     * @param _eventSubtype
     *         Type d'événement.
     * @param _initialInd
     *         Indice de la première tuile.
     * @param _newInd
     *         Indice de la tuile résultante / de la deuxième tuile.
     *
     * @see ModelFusionEvent#createInstance(ModelEventSubtype, int, int) pour une initialisation hors-package.
     */
    private ModelFusionEvent(ModelEventSubtype _eventSubtype, int _initialInd, int _newInd) {
        super(_eventSubtype);

        this.initialInd = _initialInd;
        this.newInd = _newInd;
    }

    /**
     * Accesseur de l'attribut <i>initialInd</i>.
     * <p>
     * Concerne l'indice de la première tuile.
     *
     * @return la valeur de l'attribut.
     */
    public int getInitialInd() {
        return this.initialInd;
    }

    /**
     * Accesseur de l'attribut <i>newInd</i>.
     * <p>
     * Concerne l'indice de la tuile résultante / de la deuxième tuile.
     *
     * @return la valeur de l'attribut.
     */
    public int getNewInd() {
        return this.newInd;
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Utilisé et utilisable pour créer de nouvelles instances de {@link ModelFusionEvent}.
     * La catégorie de l'événement est vérifiée, et lance une {@link IllegalArgumentException} si la catégorie est
     * incorrecte.
     *
     * @param _eventSubtype
     *         Type d'événement; doit être un événement de fusion.
     * @param _initialInd
     *         Indice de la première tuile.
     * @param _newInd
     *         Indice de la tuile résultante / de la deuxième tuile.
     *
     * @return l'instance créée, si lieu.
     *
     * @throws IllegalArgumentException
     *         si la catégorie du type d'événement est incorrecte (non liée aux fusions).
     */
    public static ModelFusionEvent createInstance(ModelEventSubtype _eventSubtype, int _initialInd, int _newInd) {
        if (_eventSubtype.getCategory() == ModelEventCategory.FUSION_RELATED) {
            return new ModelFusionEvent(_eventSubtype, _initialInd, _newInd);
        }

        throw new IllegalArgumentException("The given event is not related to two fusing cells.");
    }
}
