/*
 * Copyright (c) 07/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus;

import application.Main;
import controller.menus.mainMenu.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe ViewLoader.
 * <p>
 * Permet le chargement des vues et des contrôleurs de vue, tel que {@link MainMenuController le menu principal},
 * et ainsi d'améliorer la lisibilité des classes chargeant des vues, ainsi qu'éviter de la redondance de code
 * pour le chargement de vue.
 *
 * <p>
 * Cette classe prend une implémentation de {@link ViewController} en paramètre.
 * Cela implique que la classe <i>T</i> doit être un contrôleur, et non une vue elle-même.
 * Les vues ne devant se charger sans contrôleur, il n'a donc pas été jugé nécéssaire de créer une classe pour
 * cela et l'ambiguité du nom de la classe comme acceptable.
 *
 * @param <T>
 *         Une classe implémentant {@link ViewController}, cela <b>doit</b> être un contrôleur, et non une vue.
 */
public class ViewLoader<T extends ViewController> {

    /** Pack de ressources associé au contrôleur à charger. */
    private ResourceBundle bundle;
    /** Stage créé pour la vue à charger. */
    private Stage stage;
    /** Chargeur de fichier FXML. */
    private FXMLLoader loader;
    /** Contrôleur qui a été chargé. */
    private T controller;

    /**
     * Constructeur privé.
     * <p>
     * L'instanciation hors de la classe se fait avec les méthodes de fabrique (cf. section <i>See Also</i>).
     * <p>
     * Le {@link ResourceBundle pack de ressources} est automatiquement considéré comme étant selon des vues
     * "classiques", excluant donc la vue de jeu.
     *
     * @param _stage
     *         {@link Stage} qui est associé à l'interface à charger, créé ou modifié par la classe appellante
     *         (par ex., le paramètre <i>primaryStage</i> de la méthode <i>Application.start()</i>). <br>
     *         Autrement, il serait mieux de créer un autre constructeur.
     * @param _FXMLPath
     *         {@link String} menant au fichier FXML lié à la vue que l'on veut ouvrir. <br>
     *         Typiquement accessible par des attributs de classes dans les contrôleurs (<b>T</b>).
     *
     * @see #createMainMenuLoader(Stage)
     */
    private ViewLoader(Stage _stage, String _FXMLPath) {
        this.stage = _stage;

        this.bundle = ResourceBundle.getBundle("controller/menus/menus", Main.getLang());

        URL FXMLfile = ViewLoader.class.getResource(_FXMLPath);
        this.loader = new FXMLLoader(FXMLfile);
        this.loader.setResources(bundle);
    }

    /**
     * Méthode principale.
     * <p>
     * Lance le chargement de la vue à travers l'instance configurée de {@link FXMLLoader}.
     * <p>
     * Abilitée à arrêter l'application s'il y a une erreur de chargement.
     * <p>
     * Utilise les deux méthodes privées de cette classe :
     *      - {@link #initController()}, pour les attributs du contrôleur.
     *      - {@link #initSceneAndStage(VBox)}, pour la scène et le stage.
     */
    public void loadView() {
        try {
            VBox root = loader.load();
            this.initController();
            this.initSceneAndStage(root);

        } catch (Exception _e) {
            _e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Prépare le contrôleur en l'associant avec le stage et le pack de langue.
     * <p>
     * Dépend de l'implémentation donnée par le contrôleur des méthodes de {@link ViewController}.
     * <p>
     * Cette méthode nécéssite que le contrôleur soit donné par le fichier FXML.
     *
     * @see ViewController#initBundle(ResourceBundle)
     * @see ViewController#initStage(Stage)
     */
    private void initController() {
        this.controller = loader.getController();
        this.controller.initBundle(bundle);
        this.controller.initStage(stage);
    }

    /**
     * Initialise le {@link Stage stage} avec sa {@link Scene scène} et l'affiche.
     * <p>
     * La scène est configurée à partir de la {@link VBox}-racine créé par l'instance {@link FXMLLoader}
     * et utilise le fichier CSS associé au contrôleur qui est fournit à partir du constructeur et des attributs.
     *
     * @param _root
     *         Racine créée par l'instance {@link FXMLLoader} ; utilisée pour instancier la {@link Scene scène}.
     */
    private void initSceneAndStage(VBox _root) {
        Scene scene = new Scene(_root);
        scene.getStylesheets().add(this.controller.getCSSPath());

        // Association de la scène et du stage, et affichage.
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Fournit une instance pouvant charger la vue principal.
     * <p>
     * La méthode utilise une instance de {@link Stage} passée en paramètre ; il est donc mieux d'utiliser cette version
     * de la méthode uniquement si cette instance est déjà créée ou est particulière, et, dans d'autres cas,
     * de créer un autre constructeur sans paramètre de {@link Stage} et une surcharge de cette méthode : <br>
     * <code>public static ViewLoader<MainMenuController> createMainMenuLoader()</code>
     *
     * @param _stage
     *         Instance particulière de {@link Stage}, créée ou modifiée par la classe appellante (par ex.,
     *         le paramètre <i>primaryStage</i> de la méthode <i>Application.start()</i>).
     *
     * @return Retourne une instance {@link ViewLoader} chargée de s'occuper du menu principal.
     *
     * @see #ViewLoader(Stage, String)
     * @see javafx.application.Application#start(Stage)
     */
    public static ViewLoader<MainMenuController> createMainMenuLoader(Stage _stage) {
        return new ViewLoader<>(_stage, MainMenuController.FXMLPath);
    }
}
