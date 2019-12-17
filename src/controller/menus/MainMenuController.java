/*
 * Copyright (c) 16/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus;

import application.GameApplication;
import controller.AbstractViewController;
import controller.DialogBoxFactory;
import controller.SubViewLoader;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;
import java.util.ResourceBundle;

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
 * Les attributs de classe {@link #FXMLPath} et {@link #CSSPath} ne changent pas d'un lancement à un autre,
 * et sont utilisés par les {@link controller.ViewLoader chargeurs de vue}.
 *
 * @see AbstractViewController
 * @see ViewController
 * @see controller.ViewLoader
 */
public class MainMenuController extends AbstractViewController implements ViewController {
    /** Chemin menant au fichier FXML associé au menu principal. */
    public static final String FXMLPath = "/controller/menus/MainMenu.fxml";
    /** Chemin menant au fichier CSS associé au menu, <b>sans</b> le masque de thème et l'extension. */
    public static final String CSSPath = "controller/menus/MainMenu";

    /** Pack de ressource associé à cette vue. */
    private ResourceBundle bundle;
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
        this.continueGameButton.setDisable(true);
    }

    /**
     * Transfère une instance {@link ResourceBundle} vers l'instance {@link DialogBoxFactory} afin d'obtenir des
     * boîtes de dialogues changeantes selon la langue de l'application.
     * <p>
     * Devrait être appelée par l'instance {@link controller.ViewLoader} qui a instancié le pack.
     * <p>
     * Peut être surchargée avec un appel sur cette méthode si la classe utilise également l'instance du {@link
     * ResourceBundle pack de ressources}.
     *
     * @param _bundle
     *         Le {@link ResourceBundle pack de ressources} associé à ce contrôleur.
     *
     * @throws IllegalArgumentException
     *         Lancée si le {@link ResourceBundle pack} existe déjà à l'appel de cette méthode et ne change pas de
     *         langue. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     * @see DialogBoxFactory
     */
    @Override
    public void initBundle(ResourceBundle _bundle) {
        super.initBundle(_bundle);
        this.bundle = _bundle;
    }

    /**
     * Transfère l'attribut {@link Stage} depuis l'instance {@link controller.ViewLoader} accèdant à la vue.
     *
     * <p>
     * Modifie la propriété <i>onCloseRequest</i> du {@link Stage} afin d'appeler {@link
     * #isCloseable()} et de devoir obtenir une validation avant de fermer la fenêtre.
     * <p>
     * Peut être surchargée avec un appel sur cette méthode si la classe modifie d'autres propriétés du {@link Stage}.
     *
     * @param _stage
     *         Le {@link Stage stage} associé à ce contrôleur.
     *
     * @throws IllegalStateException
     *         Lancée si le {@link Stage} existe déjà à l'appel de cette méthode. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    @Override
    public void initStage(Stage _stage) {
        super.initStage(_stage);
        this.stage.setTitle(this.bundle.getString("mainMenu"));
    }

    /**
     * Action correspondante au bouton "Nouvelle Partie".
     * <p>
     * Ouvre la vue de jeu, grâce au {@link SubViewLoader}.
     * <p>
     * Le menu principal est fermé après l'ouverture de la vue de jeu, mais s'ouvre à nouveau
     * lors de la fermeture de la vue de jeu.
     *
     * @see SubViewLoader
     * @see controller.game.GameController
     * @see Stage#setOnShown(EventHandler)
     * @see Stage#setOnHiding(EventHandler)
     */
    @FXML
    private void onNewGame() {
        Optional<ButtonType> res = this.dialogFactory.furnishGameBugWarning().showAndWait();

        if (res.isPresent() && res.get() == ButtonType.OK) {

            SubViewLoader viewLoader = SubViewLoader.createGameViewLoader();
            viewLoader.loadView();

            // Si la vue de jeu est fermée, alors le menu principal s'affiche à nouveau.
            Stage loadedStage = viewLoader.getStage();
            loadedStage.setOnShown((event) -> this.stage.close());
            loadedStage.setOnHiding((event) -> this.stage.show());

            viewLoader.show();
        }
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
     * @see controller.menus.settings.SettingsController
     */
    @FXML
    private void onSettings() {
        SubViewLoader viewLoader = SubViewLoader.createSettingsMenuLoader();
        viewLoader.loadView();

        // Si cette vue est fermée, alors la sous-vue le sera également.
        this.stage.setOnHiding(event -> viewLoader.close());
        viewLoader.showAndWait();

        // On actualise le CSS à la fermeture des paramètres.
        this.refreshCSS();
        this.stage.setOnHiding(null);
    }

    /**
     * Action correspondante au bouton "Quitter".
     * <p>
     * Envoit une requête de fermeture au {@link Stage}, qui peut ensuite être validée ou annulée, au travers de
     * la boîte de dialogue créée par la méthode {@link #isCloseable()}.
     *
     * @see javafx.scene.control.Alert
     * @see Stage#fireEvent(javafx.event.Event)
     * @see WindowEvent#WINDOW_CLOSE_REQUEST
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

    /**
     * Rafraichit le fichier CSS associé.
     * <p>
     * Utilise {@link ObservableList#clear()}, donc il faudra modifier cette méthode si on y associé plus d'un
     * fichier CSS (thèmes ou autres).
     *
     * @see javafx.scene.Scene#getStylesheets()
     */
    private void refreshCSS() {
        ObservableList<String> CSSFiles = this.stage.getScene().getStylesheets();
        CSSFiles.clear();
        CSSFiles.add(MainMenuController.CSSPath + GameApplication.getThemeSuffix());
    }
}
