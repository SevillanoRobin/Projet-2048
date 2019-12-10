/*
 * Copyright (c) 10/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus.mainMenu;

import controller.AbstractViewController;
import controller.DialogBoxFactory;
import controller.SubViewLoader;
import controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

/**
 * Contrôleur associé au menu principal.
 * <p>
 * Le menu est principalement composé de quatre boutons : <br>
 *      - Un bouton "Nouvelle Partie", lançant une partie <i>from scratch</i>. [non-implémenté] <br>
 *      - Un bouton "Continuer une Partie", lançant une partie depuis une sauvegarde. [non-implémenté] <br>
 *      - Un bouton "Options", pour changer quelques paramètres. <br>
 *      - Un bouton "Quitter", lançant une requête pour fermer l'application.
 *
 * <p>
 * Utilise les instances de la {@link AbstractViewController classe-mère} :
 *      - Le {@link Stage} afin de lancer soi-même des requêtes de fermetures. <br>
 *      - La {@link DialogBoxFactory fabrique à boîte de dialogue}, pour simplifier la lisibilité de la classe, et
 * séparer les responsabilités selon un <i>design pattern</i> de fabrique.
 *
 * <p>
 * Les attributs de classe <i>FXMLPath</i> et <i>MainMenuCSS</i> ne changent pas d'un lancement à un autre,
 * et sont utilisés par plusieurs classes ({@link application.Main}, les sous-menus et le future <i>GameController</i>.
 *
 * @see AbstractViewController
 * @see ViewController
 */
public class MainMenuController extends AbstractViewController implements ViewController {
    /** Chemin menant au fichier FXML associé au menu principal. */
    public static final String FXMLPath = "/controller/menus/mainMenu/MainMenu.fxml";
    /** Chemin menant au fichier CSS associé au menu, <b>sans</b> le masque de thème et l'extension. */
    public static final String CSSPath = "controller/menus/mainMenu/MainMenu";

    /** Bouton "Nouvelle Partie". */
    @FXML private Button newGameButton;
    /** Bouton "Charger une Partie". */
    @FXML private Button continueGameButton;

    /**
     * Actions effectuées durant le {@link javafx.fxml.FXMLLoader chargement de la vue FXML}.
     * <p>
     * <b>Ne doit pas avoir de paramètres afin de pouvoir être lancée automatiquement.</b>
     * <p>
     * Permet pour l'instant de désactiver les boutons n'ayant pas encore d'implémentation.
     *
     * @see javafx.fxml.FXMLLoader
     */
    @FXML
    void initialize() {
        this.newGameButton.setDisable(true);
        this.continueGameButton.setDisable(true);
    }

    /**
     * [Non-implémentée] Action correspondante au bouton "Nouvelle Partie".
     */
    @FXML
    private void onNewGame() {
        // TODO: Implement a game interface.
    }

    /**
     * [Non-implémentée] Action correspondante au bouton "Continuer une partie".
     */
    @FXML
    private void onContinue() {
        // TODO: Implement save & load features.
    }

    /**
     * Action correspondante au bouton "Options".
     * <p>
     * Ouvre une menu des options, grâce au {@link SubViewLoader}.
     * <p>
     * Si le menu principal est fermé alors que le menu des options est ouvert, alors on le ferme également.
     *
     * @see SubViewLoader
     * @see controller.menus.mainMenu.settings.SettingsController
     */
    @FXML
    private void onSettings() {
        SubViewLoader viewLoader = SubViewLoader.createSettingsMenuLoader();
        viewLoader.loadView();

        // Si cette vue est fermée, alors la sous-vue le sera également.
        this.stage.setOnHiding(event -> viewLoader.close());
        viewLoader.showAndWait();
        this.stage.setOnHiding(null);
    }

    /**
     * Action correspondante au bouton "Quitter".
     * <p>
     * Envoit une requête de fermture au {@link Stage}, qui peut ensuite être validée ou annulée, au travers de
     * la boîte de dialogue créée par la méthode {@link #isCloseable()}.
     *
     * @see javafx.scene.control.Alert la classe de la boîte de dialogue qui sera créée
     */
    @FXML
    private void onQuit() {
        this.stage.fireEvent(new WindowEvent(this.stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Affiche une {@link javafx.scene.control.Alert boîte de dialogue} pour confirmer la fermeture de la fenêtre,
     * et interprête ses résultats.
     * <p>
     * Invoquée lors d'une demande de fermeture de la fenêtre, par la propriété <i>onCloseRequest</i> du {@link Stage}.
     * <p>
     * Permet de pouvoir bloquer la fermeture de l'application si non-voulue.
     *
     * @return {@code true} si la boîte de dialogue est fermée avec le bouton "OK", {@code false} autrement.
     *
     * @see DialogBoxFactory
     * @see javafx.scene.control.Alert
     */
    @Override
    protected boolean isCloseable() {
        Optional<ButtonType> result = this.dialogFactory.CloseRequestDialog().showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }
}
