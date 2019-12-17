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

import javafx.event.EventHandler;

import java.util.ArrayList;

/**
 * Interface ModelEventEmitter.
 * <p>
 * Utilisée pour permettre une généralisation des éléments de gestion de flux d'événements :
 *          - Ajout de <i>listeners</i>.
 *          - Accès aux <i>listeners</i>.
 *          - Suppression de <i>listeners</i>.
 *
 * <p>
 * Exclut l'émission d'événement pour éviter qu'une classe puisse lancer des événements depuis une autre classe (sauf
 * le délégué).
 * L'{@link AbstractModelEventEmitter implémentation} offre une alternative, à condition de ne pas être
 * depuis l'extérieur ({@code package-private} ou {@code public}).
 *
 * <p>
 * Permet également de créer un élément valide pour le paramètre de l'interface {@link EventHandler} sans devoir
 * utiliser d'héritage (limité à une classe-mère).
 *
 * @see EventHandler
 * @see AbstractModelEventEmitter
 * @see model.events.ModelEvent
 */
public interface ModelEventEmitter {

    /**
     * Lie un {@link EventHandler} à l'objet courant.
     *
     * @param _listener
     *         Listener à lier.
     */
    void addListener(EventHandler<ModelEvent> _listener);

    /**
     * Dé-lie un {@link EventHandler} à l'objet courant.
     *
     * @param _listener
     *         Listener à lier.
     */
    void removeListener(EventHandler<ModelEvent> _listener);

    /**
     * Accesseur des {@link EventHandler} associés à cette instance.
     *
     * @return la valeur de l'attribut.
     */
    ArrayList<EventHandler<ModelEvent>> getListeners();
}
