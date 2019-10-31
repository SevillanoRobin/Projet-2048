/*
 * Copyright (c) 31/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenu {

    @FXML private Button newGameButton;
    @FXML private Button continueGameButton;
    @FXML private Button settingsButton;
    @FXML private Button quitButton;

    @FXML
    void initialize() {
        this.continueGameButton.setDisable(true);
        this.settingsButton.setDisable(true);
        this.quitButton.setDisable(true);
    }

    @FXML
    private void onNewGame(ActionEvent _actionEvent) {
        // TODO: Auto-generated stub.
    }

    @FXML
    private void onContinue(ActionEvent _actionEvent) {
        // TODO: Save feature not yet implemented.
    }

    @FXML
    private void onSettings(ActionEvent _actionEvent) {
        // TODO: Auto-generated stub.
    }

    @FXML
    private void onQuit(ActionEvent _actionEvent) {
        // TODO: Auto-generated stub.
    }
}
