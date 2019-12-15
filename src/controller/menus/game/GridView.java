/*
 * Copyright (c) 15/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus.game;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Grid;
import model.Tile;
import model.events.ModelEvent;
import model.events.ModelFusionEvent;
import model.events.ModelMovedTileEvent;
import model.events.ModelNewTileEvent;

import java.util.HashMap;

public class GridView extends GridPane implements EventHandler<ModelEvent> {

    private final Grid model;

    /** Tableau contenant les vues des cellules. */
    private final HashMap<Integer, Node> subViewList;

    GridView(Grid _model) {
        this.subViewList = new HashMap<>();

        this.model = _model;
        this.model.addListener(this);

        this.setGridLinesVisible(true);
        this.implementSpans();
    }

    private void implementSpans() {
        int spanValue = 50; // Egale à la valeur du CSS.

        for (int i = 0; i < 3; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(spanValue));
            this.getRowConstraints().add(new RowConstraints(spanValue));
        }
    }

    // --- GESTION DES SOUS-VUES --- //

    private void addChild(Tile _tile, int _ind) {
        TileView tileView = new TileView(_tile);

        this.subViewList.put(_ind, tileView);
        this.add(tileView, tileView.getColumn(), tileView.getRow());
    }

    private void moveChild(int _oldInd, int _newInd) {
        TileView tileView = (TileView) this.subViewList.get(_oldInd);
        tileView.setPosition(_newInd);

        // Déplacement dans le tableau en attribut.
        this.subViewList.remove(_oldInd);
        this.subViewList.put(_newInd, tileView);

        // Déplacement dans la vue.
        GridPane.setConstraints(tileView, tileView.getColumn(), tileView.getRow());
    }

    private void deleteChild(int _ind) {
        Node node = this.subViewList.remove(_ind);
        this.getChildren().remove(node);
    }

    private void fuseChildren(int _oldInd, int _newInd) {
        this.deleteChild(_oldInd);

        TileView tileView = (TileView) this.subViewList.get(_newInd);
        tileView.doubleValue();
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
            case NEW_TILE_EVENT:
                this.handleNewTileEvent((ModelNewTileEvent) _event);
                break;
            case MOVED_TILE_EVENT:
                this.handleMovedTileEvent((ModelMovedTileEvent) _event);
                break;
            case FUSED_TILES_EVENT:
                this.handleFusedTileEvent((ModelFusionEvent) _event);
                break;
            default:
                System.err.println("Unexpected value in GridView: " + _event.getSubType());
        }
    }

    private void handleNewTileEvent(ModelNewTileEvent _event) {
        int ind = _event.getInd();
        Tile newTile = this.model.getTile(ind);

        this.addChild(newTile, ind);
    }

    private void handleMovedTileEvent(ModelMovedTileEvent _event) {
        this.moveChild(_event.getInitialInd(), _event.getNewInd());
    }

    private void handleFusedTileEvent(ModelFusionEvent _event) {
        this.fuseChildren(_event.getInitialInd(), _event.getNewInd());
    }
}
