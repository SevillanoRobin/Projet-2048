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

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Classe ModelEvent.
 * <p>
 * Concerne les événements liés aux données du jeu (autrement dit, le <i>modèle</i> [design MVC]).
 * <p>
 * Cette classe est utilisée de deux façons :
 *      - Une classe pour les événements généraux, concernant l'ensemble de la partie (par ex. victoire ou défaite).
 *      - En tant que classe-mère pour les autres classes d'événements, afin d'éviter une classe essentiellement vide
 * pour les événements généraux.
 * <p>
 * <p>
 * Cette classe concerne donc les événements suivants :
 *       -  Démarrage de partie (nouvelle ou chargée).
 *       -  Chargement d'une partie.
 *       -  Résolution de l'objectif ({@link model.Parametres#GOAL}, généralement 2048).
 *       -  Perte de la partie.
 *
 * @see javafx.event.EventType    Le type d'événément fournit et demandé par JavaFX.
 * @see ModelEventCategory        Les catégories d'événements ; doit être {@link ModelEventCategory#GRIDSET_RELATED} ici.
 * @see javafx.event.EventHandler Les "listeners", càd les récepteurs des événements.
 * @see ModelEventEmitter   Les émetteurs d'événements.
 */
public class ModelEvent extends Event {

    /** Sous-type d'événement. */
    private final ModelEventSubtype subType;

    /** {@link EventType Type d'événement} que JavaFX requière. */
    private final static EventType<Event> MODEL_EVENT_TYPE = new EventType<>("ModelEvent");

    /**
     * Constructeur <i>package-private</i>.
     * <p>
     * Utilisé et utilisable par les classes-filles du même package.
     * <p>
     * Depuis un autre package, il faut utiliser {@link ModelEvent#createInstance(ModelEventSubtype)} afin de
     * vérifier la catégorie de l'événement, ainsi que d'éviter que le constructeur lance des exceptions.
     *
     * @param _eventSubType
     *         Type d'événement.
     *
     * @see ModelEvent#createInstance(ModelEventSubtype) pour une initialisation hors-package.
     */
    ModelEvent(ModelEventSubtype _eventSubType) {
        super(MODEL_EVENT_TYPE);

        this.subType = _eventSubType;
    }

    /**
     * Accesseur de l'attribut <i>category</i>.
     * <p>
     * Concerne le {@link ModelEventSubtype sous-type} de l'événement.
     *
     * @return la valeur de l'attribut.
     */
    public ModelEventSubtype getSubType() {
        return this.subType;
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Utilisé et utilisable pour créer de nouvelles instances de {@link ModelEvent}.
     * La catégorie de l'événement est vérifiée, et lance une {@link IllegalArgumentException} si la catégorie est
     * incorrecte.
     *
     * @param _eventSubType
     *         Type d'événement; doit être un événement général.
     *
     * @return l'instance créée, si lieu.
     *
     * @throws IllegalArgumentException
     *         si la catégorie du type d'événement est incorrecte (non-générale).
     */
    public static ModelEvent createInstance(ModelEventSubtype _eventSubType) throws IllegalArgumentException {
        if (_eventSubType.getCategory() == ModelEventCategory.GRIDSET_RELATED) {
            return new ModelEvent(_eventSubType);

        }
        throw new IllegalArgumentException("The given event is not related to the gridset.");
    }
}
