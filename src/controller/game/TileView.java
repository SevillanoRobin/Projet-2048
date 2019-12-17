/*
 * Copyright (c) 17/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.game;

import javafx.scene.control.Label;
import model.Parameters;
import model.Tile;

/**
 * Classe TileView.
 * Vue associée à une tuile en particulier.
 * <p>
 * Possède des éléments de positions :
 *     - Une position, fournie par le modèle (MVC).
 *     - Une coordonnée en abscisse, la ligne.
 *     - Une coordonnée en ordonnée, la colonne.
 * <p>
 * Possède également un suivi de la valeur de la tuile au travers d'un attribut et d'un {@link TileView#doubleValue()
 * modificateur}, ce qui permet un changement de la valeur affichée par la vue au travers de la classe {@link Label}.
 *
 * @see javafx.scene.control.Label
 */
class TileView extends Label {
    private int position;
    private int row;
    private int column;
    private int value;

    /**
     * Constructeur <i>package-private</i>.
     * <p>
     * Initialise la vue avec la valeur et la position initiales de la tuile, ainsi que le style CSS associé à toutes
     * les tuiles (<i>.tileCoreStyle</i>).
     * La vue est ensuite associée à un style dépendant de sa valeur (<i>.tile#</i>), ainsi que de coordonnées calculées
     * depuis la position donnée.
     *
     * @param _model
     *         Tuile associée à cette vue ; l'instance n'est pas gardée, on ne prend que sa valeur et sa position.
     */
    TileView(Tile _model) {
        super();

        this.value = _model.getValue();
        this.position = _model.getPos();

        // Styles.
        this.getStylesheets().add("controller/game/TileStyle.css");
        this.getStyleClass().add("tileCoreStyle");

        this.calculateCoors();
        this.updateFrontEnd();
    }

    /**
     * Calcule les attributs de ligne et de colonne.
     * <p>
     * La ligne utilise un calcul tel que {@code position / SIDE}.
     * La colonne utilise un calcul tel que {@code position - SIDE * ligne}.
     */
    private void calculateCoors() {
        this.row = Math.floorDiv(this.position, Parameters.SIDE);
        this.column = (int) Math.ceil(this.position - Parameters.SIDE * row);
    }

    /**
     * Actualise {@link Label#setText(String) le texte de la tuile}, ainsi que les styles CSS qui lui sont associés :
     * <p>
     * Les styles CSS associés consistent en :
     *      - <i>.tileCoreStyle</i> pour tous les tuiles.
     *      - <i>.tile#</i> pour les tuiles dont la valeur est <i>#</i> (valeur numérique).
     */
    private void updateFrontEnd() {
        // Texte de la tuile (valeur affichée).
        this.setText("" + this.value);

        // Styles FXML.
        this.getStyleClass().removeIf(_s -> _s.matches("tile[0-9]+"));
        this.getStyleClass().add("tile" + this.value);
    }

    // --- ACCESSEURS & MODIFICATEURS --- //

    /**
     * Accesseur de l'attribut <i>row</i>.
     *
     * @return la valeur de l'attribut.
     */
    int getRow() {
        return this.row;
    }

    /**
     * Accesseur de l'attribut <i>column</i>.
     *
     * @return la valeur de l'attribut.
     */
    int getColumn() {
        return this.column;
    }

    /**
     * Modificateur de l'attribut <i>position</i>.
     * <p>
     * Permet de mettre à jour la position de la vue en fonction de la position transmisse par les
     * {@link model.events.ModelMovedTileEvent événéments de mouvement}.
     *
     * @param _position
     *         Nouvelle position, doit être positive et inférieure à {@link Parameters#SIZE la taille de la grille}.
     *
     * @throws IllegalArgumentException
     *         si le paramètre est négatif ou supérieur à la taille de la grille.
     * @see model.events.ModelMovedTileEvent
     * @see Parameters#SIZE
     */
    void setPosition(int _position) throws IllegalArgumentException {
        if (_position < 0 || _position >= Parameters.SIZE) {
            throw new IllegalArgumentException("Anormal index value: " + _position);
        }
        this.position = _position;

        this.calculateCoors();
    }

    /**
     * Modificateur de l'attribut <i>value</i>.
     * <p>
     * Double la valeur de l'attribut et accommode l'apparence de la vue en fonction de la nouvelle valeur.
     */
    void doubleValue() {
        this.value *= 2;

        this.updateFrontEnd();
    }
}
