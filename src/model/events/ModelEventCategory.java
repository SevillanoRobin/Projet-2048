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
 * Énumération ModelEventCategory.
 * <p>
 * Permet de trier les {@link ModelEventSubtype sous-types d'évenements} dans des catégories ;
 * pour ensuite les séparer dans des classes spécialisées dans les catégories : les nombres d'attributs varient selon
 * les différentes catégories.
 * <p>
 * Par exemple, un événement de fusion aura deux attributs de position, alors qu'un événement de changement de score
 * n'aura qu'un seul attribut.
 * <p>
 * Il existe donc quatre catégories :
 * - Événements généraux, liés à l'état de la partie.
 * - Événements de changement de score.
 * - Événements de fusion de tuiles.
 * - Événements liés à une tuile donnée (créations de nouvelles tuiles et déplacements).
 * <p>
 * À ne pas confondre avec le {@link javafx.event.EventType type principal} ou le {@link ModelEventSubtype sous-type}.
 *
 * @see javafx.event.EventType Le type d'événément fournit et demandé par JavaFX.
 * @see ModelEventSubtype      Les sous-types d'événements ; permettant une réaction appropriée
 * des {@link javafx.event.EventHandler "listeners"} et des détails fournit par les classes associées.
 */
public enum ModelEventCategory {
    /** Événement lié à une fusion de tuiles. */
    FUSION_RELATED,
    /** Événement lié à l'ensemble des grilles et à la partie. */
    GRIDSET_RELATED,
    /** Événement lié à un changement du score. */
    SCORE_RELATED,
    /** Événement lié à une tuile en particulier. */
    TILE_RELATED
}
