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

import controller.AbstractViewController;
import controller.DialogBoxFactory;
import controller.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Grids;
import model.Parameters;
import model.events.ModelEvent;
import model.events.ModelEventSubtype;
import model.events.ModelScoreEvent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Contrôleur pour la fenêtre de jeu.
 * <p>
 * La vue est créée à partir du fichier FXML, et utilise la {@link GridSetView} pour le jeu en lui-même.
 */
public class GameController extends AbstractViewController implements EventHandler<ModelEvent>, ViewController {

    private boolean isAGameOnGoing;
    private boolean hasBeenSaved;

    private ResourceBundle bundle;

    private Grids gridSet;

    @FXML private GridSetView gridSetView;

    @FXML private Label bestValueLabel;

    // TODO: Implement.
    @FXML private Label currentScoreLabel;
    @FXML private Label bestScoreLabel;

    @FXML private Button saveGameBtn;
    @FXML private Button loadGameBtn;
    @FXML private Button helpBtn;
    @FXML private Button continuousHelpBtn;

    /** Chemin menant au fichier CSS associé au menu, <b>sans</b> le masque de thème et l'extension. */
    private static final String CSSPath = "controller/game/GameViewStyle";
    /** Chemin menant au fichier FXML associé à la vue de jeu. */
    public static final String FXMLPath = "/controller/game/GameViewFXML.fxml";
    /** Chemin menant aux fichiers <i>.properties</i> associés à la vue de jeu. */
    public static final String BundlePath = "controller/game/GameView";

    public GameController() {
        this.isAGameOnGoing = false;
        this.hasBeenSaved = false;
    }

    /**
     * Le {@link Stage} n'est pas initialisé à ce moment ; cf. {@link #initStage(Stage)}.
     */
    @FXML
    private void initialize() {
        this.saveGameBtn.setDisable(true);

        // Non-implémentés
        this.loadGameBtn.setDisable(true);
        this.helpBtn.setDisable(true);
        this.continuousHelpBtn.setDisable(true);
    }

    /**
     * Transfère l'attribut {@link ResourceBundle} depuis l'instance {@link controller.SubViewLoader} accédant à la vue.
     * <p>
     * Devrait être appelée par l'instance {@link controller.SubViewLoader} qui a instancié le pack.
     *
     * @param _bundle
     *         Le {@link ResourceBundle pack de ressources} associé à ce contrôleur.
     *
     * @throws IllegalArgumentException
     *         Devrait être Lancée si le {@link ResourceBundle pack} existe déjà à l'appel de cette méthode et
     *         ne change pas de langue. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    @Override
    public void initBundle(ResourceBundle _bundle) {
        super.initBundle(_bundle);
        this.bundle = _bundle;
    }

    /**
     * Transfère l'attribut {@link Stage} depuis l'instance {@link controller.SubViewLoader} accédant à la vue.
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
        _stage.setTitle(this.bundle.getString("window_title"));
    }

    /**
     * Permet l'accès au chemin du fichier CSS à travers une instance.
     * <p>
     * Cela permet à {@link controller.ViewLoader} de ne pas avoir à utiliser un paramètre supplémentaire dans le
     * constructeur, ainsi de ne pas forcer l'attribut <i>CSSPath</i> en tant qu'attribut de classe ou d'instance.
     *
     * @return Retourne le chemin vers le fichier CSS associé au contrôleur.
     */
    @Override
    public String getCSSPath() {
        return GameController.CSSPath;
    }

    /**
     * Affiche une {@link Alert boîte de dialogue} pour confirmer la fermeture de la fenêtre,
     * et interprète ses résultats.
     * <p>
     * Invoquée lors d'une demande de fermeture de la fenêtre, par la propriété <i>onCloseRequest</i> du {@link Stage}.
     *
     * <p>
     * Dans ce contexte, la fermeture de la fenêtre correspond au retour vers le menu principal.
     * <p>
     * Permet d'éviter de quitter une partie si cela n'est pas voulu ; ainsi que de donner la possibilité de
     * sauvegarder.
     *
     * @return {@code true} si la boîte de dialogue est fermée avec les boutons "Oui" ou "Non".
     *
     * @see DialogBoxFactory
     * @see Alert
     */
    @Override
    protected boolean isCloseable() {
        if (!this.isAGameOnGoing || this.hasBeenSaved) {
            return true;
        }

        Alert alert = this.dialogFactory.furnishLeaveConfirmationDB();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get().equals(ButtonType.YES)) {
                this.saveGame();
                return true;
            }
            return result.get().equals(ButtonType.NO);
        }

        return false;
    }

    private void saveGame() {
        this.gridSet.save(name -> {
            this.dialogFactory.furnishSaveConfirmationDB(name).show();
            return null;
        });

        this.hasBeenSaved = true;
    }

    /// --- FXML Methods --- ///

    @FXML
    private void onNewGame() {
        ArrayList<EventHandler<ModelEvent>> listeners = new ArrayList<>();
        listeners.add(this);
        listeners.add(this.gridSetView);

        this.gridSet = new Grids(listeners);
        this.gridSetView.setGridSet(this.gridSet);
        this.gridSet.fireStartEvent();
    }

    @FXML
    private void onSaveGame() {
        this.saveGame();
    }

    @FXML
    private void onLoadGame() {
        // TODO: To implement.

        this.fireLoadedGameEvent();
        this.hasBeenSaved = false;
    }

    @FXML
    private void onReturnToMainMenu() {
        if (this.isCloseable()) {
            this.stage.hide();
        }
    }

    @FXML
    private void onHelp() {
        // TODO: To implement.
    }

    @FXML
    private void onContinuousHelp() {
        // TODO: To implement.
    }


    // --- GESTION DES ÉVÉNEMENTS --- //

    private void fireLoadedGameEvent() {
        ModelEvent event = ModelEvent.createInstance(ModelEventSubtype.LOADED_GAME_EVENT);

        this.gridSetView.handle(event);
    }

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

            case START_EVENT:
                this.handleGameStart();
                break;
            case WIN_EVENT:
            case LOSE_EVENT:
                this.handleGameEnd();
                break;
            case BEST_VALUE_CHANGE_EVENT:
                String text = this.bundle.getString("label.bestValue") + " ";
                this.bestValueLabel.setText(text + ((ModelScoreEvent) _event).getNewValue());
                break;
            case SCORE_CHANGE_EVENT:
                text = this.bundle.getString("label.currentScore") + " ";
                this.currentScoreLabel.setText(text + ((ModelScoreEvent) _event).getNewValue());
                break;
            case BEST_SCORE_CHANGE_EVENT:
                text = this.bundle.getString("label.bestScore") + " ";
                this.bestScoreLabel.setText(text + ((ModelScoreEvent) _event).getNewValue());
                break;

            case LOADED_GAME_EVENT:
                break;

            default:
                System.err.println("Unexpected value in GameController: " + _event.getSubType());
        }
    }

    private void handleGameStart() {
        // TODO: To implement.

        this.isAGameOnGoing = true;
        this.hasBeenSaved = false;
        this.saveGameBtn.setDisable(false);

        this.stage.getScene().setOnKeyReleased(this::onKeyReleased);
    }

    private void handleGameEnd() {
        // TODO: To implement.

        this.isAGameOnGoing = false;
        this.saveGameBtn.setDisable(true);

        this.stage.getScene().setOnKeyReleased(null);
    }

    private void onKeyReleased(KeyEvent _event) {
        int direction = getDirection(_event);

        // GOAL est utilisée, car c'est une valeur qui ne devrait pas être associée à une direction.
        if (direction != Parameters.GOAL) {
            this.gridSet.move(false, direction);
            this.hasBeenSaved = false;
        }
    }

    private int getDirection(KeyEvent _event) {
        int direction;

        switch (_event.getCode()) {
            case LEFT:
            case KP_LEFT:
            case NUMPAD4:
            case Q:
                direction = Parameters.LEFT;
                break;
            case UP:
            case KP_UP:
            case NUMPAD8:
            case Z:
                direction = Parameters.UP;
                break;
            case RIGHT:
            case KP_RIGHT:
            case NUMPAD6:
            case D:
                direction = Parameters.RIGHT;
                break;
            case DOWN:
            case KP_DOWN:
            case NUMPAD2:
            case S:
                direction = Parameters.DOWN;
                break;
            case HOME:
            case NUMPAD7:
            case R:
                direction = Parameters.FRONT;
                break;
            case END:
            case NUMPAD1:
            case F:
                direction = Parameters.BACK;
                break;

            default:
                direction = Parameters.GOAL;
                break;
        }

        return direction;
    }
}
