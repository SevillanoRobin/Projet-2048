/*
 * Copyright (c) 09/11/2019
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Contrôleur associé au menu principal.
 */
public class MainMenuController {

    @FXML private Button newGameButton;
    @FXML private Button continueGameButton;
    @FXML private Button settingsButton;
    @FXML private Button quitButton;

    /** Stage associé à ce contrôleur. */
    private Stage stage;
    /** Pack de ressources associé à ce contrôleur. */
    private ResourceBundle bundle;

    /**
     * Invoquée à la fin de l'initialisation de la {@link javafx.scene.Scene scène}.
     * <p>
     * Permet de désactiver les boutons n'ayant pas encore d'implémentation.
     */
    @FXML
    void initialize() {
        this.newGameButton.setDisable(true);
        this.continueGameButton.setDisable(true);
        this.settingsButton.setDisable(true);
    }

    /**
     * Permet d'associer le {@link Stage stage} initialisé dans {@link application.Main}.
     * <p>
     * Associe également un protocole pour s'occuper des requêtes de fermeture ({@link MainMenu#onQuit()}.
     *
     * @param _stage Le {@link Stage stage} associé à ce contrôleur.
     */
    public void setStage(Stage _stage) {
        this.stage = _stage;

        this.stage.setOnCloseRequest(event -> {
            if (this.isCloseable()) {
                this.stage.close();
            } else {
                event.consume();
            }
        });
    }

    /**
     * Permet d'associer le {@link ResourceBundle pack de ressources} initialisé dans {@link application.Main}.
     *
     * @param _bundle Le {@link ResourceBundle pack de ressources} associé à ce contrôleur.
     */
    public void setBundle(ResourceBundle _bundle) {
        this.bundle = _bundle;
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

    /**
     * Invoquée en cliquant sur le bouton de fermeture ("quitter").
     * <p>
     * Envoit une requête de fermeture qui peut ainsi être annulée ou confirmée.
     */
    @FXML
    private void onQuit() {
        this.stage.fireEvent(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Invoquée lors d'une demande de fermeture de la fenêtre.
     * <p>
     * Permet de bloquer la fermeture de l'application si non-voulue.
     * <p>
     * Ne fonctionne pas avec la croix de fermeture.
     *
     * @return {@code true} si la boîte de dialogue est fermée avec le bouton "OK".
     */
    @FXML
    private boolean isCloseable() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, this.bundle.getString("exit_confirmation.msg"));
        alert.setTitle(this.bundle.getString("exit_confirmation.window"));

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }
}
