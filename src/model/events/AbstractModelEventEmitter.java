/*
 * Copyright (c) 17/12/2019
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
 * Classe abstraite AbstractModelEventEmitter.
 *
 * <p>
 * Fournit des implémentations de l'interface {@link ModelEventEmitter} qui devraient
 * convenir à la majorité des classes qui émettent des événements.
 *
 * <p>
 * Utilisée pour permettre une généralisation sans redondance des éléments de gestion de flux d'événements :
 *          - Ajout de <i>listeners</i>.
 *          - Accès aux <i>listeners</i>.
 *          - Suppression de <i>listeners</i>.
 *          - Émission d'événement.
 *
 * @see EventHandler
 * @see ModelEventEmitter
 * @see model.events.ModelEvent
 */
public abstract class AbstractModelEventEmitter implements ModelEventEmitter {
    /** Indique si l'objet courant est associé à au moins un listener. */
    private boolean hasListeners;

    /** Indique si l'objet courant est associé à plusieurs listeners. */
    private boolean ownsSeveralListeners;

    /** Liste des {@link EventHandler processus "à l'écoute"}. */
    private ArrayList<EventHandler<ModelEvent>> listeners;

    protected AbstractModelEventEmitter() {
        this.hasListeners = false;
        this.ownsSeveralListeners = false;
        this.listeners = new ArrayList<>();
    }

    /**
     * Lie un {@link EventHandler} à l'objet courant.
     *
     * @param _listener
     *         Listener à lier.
     */
    public void addListener(EventHandler<ModelEvent> _listener) {
        this.listeners.add(_listener);
        this.hasListeners = true;

        if (!ownsSeveralListeners && this.listeners.size() >= 2) {
            this.ownsSeveralListeners = true;
        }
    }

    /**
     * Dé-lie un {@link EventHandler} à l'objet courant.
     *
     * @param _listener
     *         Listener à lier.
     */
    public void removeListener(EventHandler<ModelEvent> _listener) {
        this.listeners.remove(_listener);

        if (this.listeners.size() < 1) {
            this.hasListeners = false;
            this.ownsSeveralListeners = false;

        } else if (ownsSeveralListeners && this.listeners.size() <= 1) {
            this.ownsSeveralListeners = false;
        }
    }

    /**
     * Envoie un événement aux listeners.
     * <p>
     * Par mesure de sécurité et de respect des responsabilités, cette méthode ne doit pas offrir une visibilité
     * supérieure à {@code protected}.
     *
     * @param _event
     *         Événement à envoyer.
     */
    protected void fireEvent(ModelEvent _event) {
        if (!ownsSeveralListeners) {
            this.listeners.get(0).handle(_event);
        } else {
            for (EventHandler<ModelEvent> _listener : this.listeners) {
                _listener.handle(_event);
            }
        }
    }

    /// --- ACCESSEURS & MODIFICATEURS --- ///

    /**
     * Accesseur de l'attribut d'instance {@code hasListeners}.
     * <p>
     * Indique si l'objet courant est associé à au moins un listener.
     *
     * @return la valeur de l'attribut.
     */
    public boolean hasListeners() {
        return this.hasListeners;
    }

    /**
     * Accesseur des {@link EventHandler} associés à cette instance.
     *
     * @return la valeur de l'attribut.
     */
    public ArrayList<EventHandler<ModelEvent>> getListeners() {
        return this.listeners;
    }
}
