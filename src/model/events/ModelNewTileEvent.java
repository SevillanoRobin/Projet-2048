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
 * Classe ModelNewTileEvent.
 * <p>
 * Cette classe est utilisée pour les tuiles, afin de donner des détails sur leurs positions à la création.
 * <p>
 * Cette classe concerne donc les événements de création de tuiles.
 *
 * @see ModelEventCategory        Les catégories d'événements ; doit être {@link ModelEventCategory#TILE_RELATED} ici.
 * @see javafx.event.EventHandler Les "listeners", càd les récepteurs des événements.
 * @see ModelEventEmitter   Les émetteurs d'événements.
 */
public class ModelNewTileEvent extends ModelEvent {
    /** Indice de la tuile concernée. */
    private final int ind;

    /**
     * Constructeur privé.
     * <p>
     * Depuis une autre classe, il faut utiliser {@link ModelNewTileEvent#createInstance(ModelEventSubtype, int)} afin
     * de
     * vérifier la catégorie de l'événement, ainsi que d'éviter que le constructeur lance des exceptions.
     *
     * @param _eventSubtype
     *         Type d'événement.
     * @param _ind
     *         Indice de la tuile.
     *
     * @see ModelNewTileEvent#createInstance(ModelEventSubtype, int) pour une initialisation hors-package.
     */
    private ModelNewTileEvent(ModelEventSubtype _eventSubtype, int _ind) {
        super(_eventSubtype);

        this.ind = _ind;
    }

    /**
     * Accesseur de l'attribut <i>newInd</i>.
     * <p>
     * Concerne l'indice de la tuile concernée.
     *
     * @return la valeur de l'attribut.
     */
    public int getInd() {
        return this.ind;
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Utilisé et utilisable pour créer de nouvelles instances de {@link ModelNewTileEvent}.
     * La catégorie de l'événement est vérifiée, et lance une {@link IllegalArgumentException} si la catégorie est
     * incorrecte.
     *
     * @param _eventSubtype
     *         Type d'événement; doit être un événement lié aux tuiles.
     * @param _pos
     *         Indice de la tuile.
     *
     * @return l'instance créée, si lieu.
     *
     * @throws IllegalArgumentException
     *         si la catégorie du type d'événement est incorrecte (non liée aux tuiles).
     */
    public static ModelNewTileEvent createInstance(ModelEventSubtype _eventSubtype, int _pos) {
        if (_eventSubtype == ModelEventSubtype.NEW_TILE_EVENT) {
            return new ModelNewTileEvent(_eventSubtype, _pos);
        }

        throw new IllegalArgumentException("The given event is not related to the creation of a tile.");
    }
}
