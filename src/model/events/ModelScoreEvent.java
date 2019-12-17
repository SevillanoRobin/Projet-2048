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

/**
 * Classe ModelScoreEvent.
 * <p>
 * Cette classe est utilisée pour les scores, contenant leurs nouvelles valeurs.
 * <p>
 * Cette classe concerne donc les événements de score :
 *       -  Changement du meilleur score.
 *       -  Changement de la meilleure valeur.
 *       -  Changement du score.
 *
 * @see ModelEventCategory        Les catégories d'événements ; doit être {@link ModelEventCategory#SCORE_RELATED} ici.
 * @see javafx.event.EventHandler Les "listeners", càd les récepteurs des événements.
 * @see ModelEventEmitter   Les émetteurs d'événements.
 */
public class ModelScoreEvent extends ModelEvent {
    /** Nouvelle valeur. */
    private final int newValue;

    /**
     * Constructeur privé.
     * <p>
     * Depuis une autre classe, il faut utiliser {@link ModelScoreEvent#createInstance(ModelEventSubtype, int)} afin de
     * vérifier la catégorie de l'événement, ainsi que d'éviter que le constructeur lance des exceptions.
     *
     * @param _eventSubtype
     *         Type d'événement.
     * @param _newValue
     *         Nouvelle valeur.
     *
     * @see ModelScoreEvent#createInstance(ModelEventSubtype, int) pour une initialisation hors-package.
     */
    private ModelScoreEvent(ModelEventSubtype _eventSubtype, int _newValue) {
        super(_eventSubtype);
        newValue = _newValue;
    }

    /**
     * Accesseur de l'attribut <i>newValue</i>.
     * <p>
     * Concerne la nouvelle valeur.
     *
     * @return la valeur de l'attribut.
     */
    public int getNewValue() {
        return this.newValue;
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Utilisé et utilisable pour créer de nouvelles instances de {@link ModelScoreEvent}.
     * La catégorie de l'événement est vérifiée, et lance une {@link IllegalArgumentException} si la catégorie est
     * incorrecte.
     *
     * @param _eventSubtype
     *         Type d'événement.
     * @param _newScore
     *         Nouvelle valeur.
     *
     * @return l'instance créée, si lieu.
     *
     * @throws IllegalArgumentException
     *         si la catégorie du type d'événement est incorrecte (non liée aux scores).
     */
    public static ModelScoreEvent createInstance(ModelEventSubtype _eventSubtype, int _newScore) {
        if (_eventSubtype.getCategory() == ModelEventCategory.SCORE_RELATED) {
            if (checkNewScore(_newScore)) {
                return new ModelScoreEvent(_eventSubtype, _newScore);
            } else {
                return null;
            }
        }

        throw new IllegalArgumentException("The given event is not related to the score or the best value.");
    }

    /**
     * Vérifie la valeur du nouveau score.
     * <p>
     * Si négative, une exception est lancée (sans intention de l'attraper).
     * Si nulle, un message d'erreur est envoyée, mais l'exécution continue et l'événement n'est pas créé par la
     * méthode-appelante.
     *
     * @param _newValue
     *         Nouvelle valeur.
     *
     * @return {@code true} si le score est strictement positif.
     */
    private static boolean checkNewScore(int _newValue) {
        if (_newValue < 0) {
            throw new IllegalArgumentException("The given new score is negative.");
        } else if (_newValue == 0) {
            System.err.println("The given new score is null.");
            return false;
        }
        return true;
    }
}
