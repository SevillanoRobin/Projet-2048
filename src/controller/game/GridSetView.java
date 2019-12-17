/*
 * Copyright (c) 16/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.game;

import application.GameApplication;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import model.Grid;
import model.Grids;
import model.events.ModelEvent;

import java.util.ResourceBundle;

public class GridSetView extends StackPane implements EventHandler<ModelEvent> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle(
            "controller/game/GridSetView",
            GameApplication.getLANG());

    private Grids gridSet;

    private final HBox gridsView;
    private final AnchorPane winView;
    private final AnchorPane loseView;

    public GridSetView() {
        gridsView = prepareGridsView();

        this.winView = this.prepareAnchorPane("victory");
        this.loseView = this.prepareAnchorPane("lose");

        this.getStylesheets().add("controller/game/GridSetViewStyle.css");

        this.getChildren().add(gridsView);
        this.getChildren().add(winView);
        this.getChildren().add(loseView);
    }

    private HBox prepareGridsView() {
        HBox gridsView = new HBox(20);
        gridsView.setAlignment(Pos.CENTER);

        return gridsView;
    }

    private AnchorPane prepareAnchorPane(String _key) {
        AnchorPane pane = new AnchorPane();

        { // Ajout du label.
            Label label = new Label(GridSetView.bundle.getString("label." + _key));
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            pane.getChildren().add(label);
        }

        // Styles CSS.
        this.getStylesheets().add("controller/game/GridSetViewStyle.css");
        pane.getStyleClass().add(_key + "Pane");

        pane.setVisible(false);

        return pane;
    }

    // --- GESTION DES EVENEMENTS --- //

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param _event
     *         the event which occurred
     */
    @Override
    public void handle(ModelEvent _event) {
        switch (_event.getSubType()) {
            case LOADED_GAME_EVENT:
                this.handleLoadEvent();
                break;
            case START_EVENT:
                this.handleStartGameEvent();
                break;
            case WIN_EVENT:
                this.handleWinEvent();
                break;
            case LOSE_EVENT:
                this.handleLoseEvent();
                break;

            case BEST_VALUE_CHANGE_EVENT:
            case SCORE_CHANGE_EVENT:
            case BEST_SCORE_CHANGE_EVENT:
                break;

            default:
                System.err.println("Unexpected value in GridSetView: " + _event.getSubType());
        }
    }

    private void handleLoadEvent() {
        // TODO: To implement.
    }

    private void handleStartGameEvent() {
        // Cache les éléments de victoire / défaite.
        this.loseView.setVisible(false);
        this.winView.setVisible(false);

        // Ajoute les enfants de la vue des grilles.
        this.gridsView.getChildren().clear();

        for (Grid _grid : this.gridSet.getGrids()) {
            GridView gridView = new GridView(_grid);
            this.gridsView.getChildren().add(gridView);
        }
    }

    private void handleWinEvent() {
        this.winView.setVisible(true);
    }

    private void handleLoseEvent() {
        this.loseView.setVisible(true);
    }


    // --- ACCESSEURS & MODIFICATEURS --- //

    void setGridSet(Grids _gridSet) {
        this.gridSet = _gridSet;
    }
}
