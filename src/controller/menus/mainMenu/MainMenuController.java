/*
 * Copyright (c) 08/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus.mainMenu;

import controller.DialogBoxFactory;
import controller.ViewController;
import controller.ViewLoader;
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
 * Le menu est principalement composé de quatre boutons :
 *      - Un bouton "Nouvelle Partie", lançant une partie <i>from scratch</i>. [non-implémenté]
 *      - Un bouton "Continuer une Partie", lançant une partie depuis une sauvegarde. [non-implémenté]
 *      - Un bouton "Options", pour changer quelques paramètres. [non-implémentés]
 *      - Un bouton "Quitter", lançant une requête pour fermer l'application.
 * <p>
 * Comporte également un attribut pour le {@link Stage} afin de lancer soi-même des requêtes de fermetures,
 * ainsi qu'une {@link DialogBoxFactory fabrique à boîte de dialogue}, pour simplifier la lisibilité de la classe, et
 * séparer les responsabilités selon un <i>design pattern</i> de fabrique.
 * <p>
 * Ces attributs exigent des éléments créés par les classes chargeant le FXML, qui doivent donc être transférés,
 * au moyen des méthodes {@link #initStage(Stage)} et {@link #initBundle(ResourceBundle)} respectivement.
 * <p>
 * <p>
 * Les attributs de classe <i>FXMLPath</i> et <i>MainMenuCSS</i> ne changent pas d'un lancement à un autre,
 * et sont utilisés par plusieurs classes ({@link application.Main} et le future <i>GameController</i>.
 */
public class MainMenuController implements ViewController {
    /** Chemin menant au fichier FXML associé au menu principal. */
    public static final String FXMLPath = "/controller/menus/mainMenu/MainMenu.fxml";
    /** Chemin menant au fichier CSS associé au menu, <b>sans</b> le masque de thème et l'extension. */
    public static final String CSSPath = "controller/menus/mainMenu/MainMenu";

    /** Fabrique à boîte de dialogue associée à ce contrôleur. */
    private DialogBoxFactory dialogFactory;

    /** Bouton "Nouvelle Partie". */
    @FXML private Button newGameButton;
    /** Bouton "Charger une Partie". */
    @FXML private Button continueGameButton;
    /** Bouton "Options". */
    @FXML private Button settingsButton;

    /** Stage associé à ce contrôleur. */
    private Stage stage;

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
        this.settingsButton.setDisable(true);
    }

    /**
     * Transfère l'attribut {@link Stage} depuis l'instance {@link ViewLoader} accèdant au menu principal.
     *
     * <p>
     * Modifie la propriété <i>onCloseRequest</i> du {@link Stage} afin d'appeler {@link
     * #isCloseable()} et de devoir obtenir une validation avant de fermer l'application.
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
        if (this.stage != null) {
            throw new IllegalStateException("The stage already exists");
        }
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
     * Transfère une instance {@link ResourceBundle} vers l'instance {@link DialogBoxFactory} afin d'obtenir des
     * boîtes de dialogues changeantes selon la langue de l'application.
     * <p>
     * Devrait être appelée par l'instance {@link ViewLoader} qui a instancié le pack.
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
        if (dialogFactory == null) {
            this.dialogFactory = new DialogBoxFactory(_bundle);
        } else {
            this.dialogFactory.setBundle(_bundle);
        }
    }

    /**
     * Permet l'accès au chemin du fichier CSS à travers une instance.
     * <p>
     * Cela permet à {@link ViewLoader} de ne pas avoir à utiliser un paramètre supplémentaire dans le constructeur,
     * ainsi de ne pas forcer l'attribut <i>CSSPath</i> en tant qu'attribut de classe ou d'instance.
     *
     * @return Retourne le chemin vers le fichier CSS associé au contrôleur.
     */
    @Override
    public String getCSSPath() {
        return MainMenuController.CSSPath;
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
     * [Non-implémentée] Action correspondante au bouton "Options".
     */
    @FXML
    private void onSettings() {
        // TODO: Implement setting features.
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
    @FXML
    private boolean isCloseable() {
        Optional<ButtonType> result = this.dialogFactory.CloseRequestDialog().showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }
}
