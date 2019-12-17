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
 * Énumération ModelEventSubtype.
 * <p>
 * Permet aux {@link javafx.event.EventHandler "listeners"} de fournir des réponses appropriées aux différents types
 * d'événements,
 * ainsi que de les trier avant d'analyser l'instance de l'événement (attributs).
 * Par exemple, qu'un événement de victoire (<i>WIN_EVENT</i>) soit différencié d'un événement de défaite
 * (<i>LOSE_EVENT</i>).
 * <p>
 * Les éléments sont associés à une {@link ModelEventCategory catégorie} afin de vérifier que les événements sont
 * envoyés aux bons éléments graphiques (vues et sous-vues) et pour les associer à une classe d'événement différente
 * pour fournir des détails (attributs).
 * <p>
 * <p>
 * Les types sont :
 *   <b>Général :</b>
 *       -  Démarrage de partie (nouvelle ou chargée).
 *       -  Chargement d'une partie.
 *       -  Résolution de l'objectif ({@link model.Parameters#GOAL}, généralement 2048).
 *       -  Perte de la partie.
 *   <b>Score :</b>
 *       -  Changement du meilleur score.
 *       -  Changement de la meilleure valeur.
 *       -  Changement du score.
 *   <b>Tuile spécifique :</b>
 *       -  Création d'une nouvelle tuile.
 *       -  Déplacement d'une tuile.
 *   <b>Fusions :</b>
 *       -  Fusion de deux tuiles.
 *
 * @see javafx.event.EventType  Le type d'événément fournit et demandé par JavaFX.
 * @see ModelEventCategory      Les catégories d'événements ; permettant une association aux classes d'événements et
 * aux {@link javafx.event.EventHandler "listeners"}.
 */
public enum ModelEventSubtype {
    /** Changement du meilleur score. */
    BEST_SCORE_CHANGE_EVENT(ModelEventCategory.SCORE_RELATED),
    /** Changement de la meilleure valeur. */
    BEST_VALUE_CHANGE_EVENT(ModelEventCategory.SCORE_RELATED),
    /** Fusion de deux tuiles. */
    FUSED_TILES_EVENT(ModelEventCategory.FUSION_RELATED),
    /** Chargement d'une partie. */
    LOADED_GAME_EVENT(ModelEventCategory.GRIDSET_RELATED),
    /** Perte de la partie. */
    LOSE_EVENT(ModelEventCategory.GRIDSET_RELATED),
    /** Déplacement d'une tuile. */
    MOVED_TILE_EVENT(ModelEventCategory.TILE_RELATED),
    /** Création d'une nouvelle tuile. */
    NEW_TILE_EVENT(ModelEventCategory.TILE_RELATED),
    /** Changement du score. */
    SCORE_CHANGE_EVENT(ModelEventCategory.SCORE_RELATED),
    /** Démarrage de partie (nouvelle ou chargée). */
    START_EVENT(ModelEventCategory.GRIDSET_RELATED),
    /** Résolution de l'objectif. */
    WIN_EVENT(ModelEventCategory.GRIDSET_RELATED);

    /** Catégorie associée au type d'événement. */
    private final ModelEventCategory category;

    /**
     * Constructeur privé.
     *
     * @param _ctg
     *         Catégorie associée au type d'événement.
     */
    ModelEventSubtype(ModelEventCategory _ctg) {
        this.category = _ctg;
    }

    /**
     * Accesseur de l'attribut <i>category</i>.
     * <p>
     * Concerne la {@link ModelEventCategory catégorie} de l'événement.
     *
     * @return la valeur de l'attribut.
     */
    public ModelEventCategory getCategory() {
        return this.category;
    }
}
