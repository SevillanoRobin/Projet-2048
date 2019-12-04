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
 * Classe ModelMovedTileEvent.
 * <p>
 * Cette classe est utilisée pour les éléments de mouvement, afin de donner des détails sur les positions de la tuile
 * concernée.
 * <p>
 * Cette classe concerne donc les événements de fusion.
 *
 * @see ModelEventCategory        Les catégories d'événements ; doit être {@link ModelEventCategory#TILE_RELATED} ici.
 * @see javafx.event.EventHandler Les "listeners", càd les récepteurs des événements.
 * @see ModelEventEmitter   Les émetteurs d'événements.
 */
public class ModelMovedTileEvent extends ModelEvent {
    /** Indice initiale de la tuile concernée. */
    private final int initialInd;
    /** Nouvelle indice de la tuile concernée. */
    private final int newInd;

    /**
     * Constructeur privé.
     * <p>
     * Depuis une autre classe, il faut utiliser {@link ModelMovedTileEvent#createInstance(ModelEventSubtype, int, int)}
     * afin de
     * vérifier la catégorie de l'événement, ainsi que d'éviter que le constructeur lance des exceptions.
     *
     * @param _eventSubtype
     *         Type d'événement.
     * @param _initialInd
     *         Indice initiale de la tuile.
     * @param _newInd
     *         Nouvelle indice.
     *
     * @see ModelMovedTileEvent#createInstance(ModelEventSubtype, int, int) pour une initialisation hors-package.
     */
    private ModelMovedTileEvent(ModelEventSubtype _eventSubtype, int _initialInd, int _newInd) {
        super(_eventSubtype);

        this.initialInd = _initialInd;
        this.newInd = _newInd;
    }

    /**
     * Accesseur de l'attribut <i>initialInd</i>.
     * <p>
     * Concerne l'indice initiale de la tuile concernée
     *
     * @return la valeur de l'attribut.
     */
    public int getInitialInd() {
        return this.initialInd;
    }

    /**
     * Accesseur de l'attribut <i>newInd</i>.
     * <p>
     * Concerne l'indice finale de la tuile concernée
     *
     * @return la valeur de l'attribut.
     */
    public int getNewInd() {
        return this.newInd;
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Utilisé et utilisable pour créer de nouvelles instances de {@link ModelMovedTileEvent}.
     * La catégorie de l'événement est vérifiée, et lance une {@link IllegalArgumentException} si la catégorie est
     * incorrecte.
     *
     * @param _eventSubType
     *         Type d'événement; doit être un événement lié aux tuiles.
     * @param _initialInd
     *         Indice initiale de la tuile.
     * @param _newInd
     *         Nouvelle indice.
     *
     * @return l'instance créée, si lieu.
     *
     * @throws IllegalArgumentException
     *         si la catégorie du type d'événement est incorrecte (non liée aux déplacements).
     */
    public static ModelMovedTileEvent createInstance(ModelEventSubtype _eventSubType, int _initialInd, int _newInd) {
        if (_eventSubType == ModelEventSubtype.MOVED_TILE_EVENT) {
            return new ModelMovedTileEvent(_eventSubType, _initialInd, _newInd);
        }

        throw new IllegalArgumentException("The given event is not related to a tile movement.");
    }
}
