/*
 * Copyright (c) 11/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller;

import application.GameApplication;
import controller.menus.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe abstraite AbstractViewLoader.
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
 * <p>
 * Le paramètre permet aux classes-filles d'appeler des interfaces particuliers, et ainsi appeler des méthodes
 * d'interface-fille à {@link ViewController} sans devoir utiliser de <i>cast</i>. <br>
 * <p>
 * Pour les vues principales, il vaut mieux utiliser la sous-classe {@link ViewLoader}. <br>
 * Pour les sous-vues, il vaut mieux utiliser la sous-classe ({@link SubViewLoader}).
 *
 * @param <T>
 *         Une classe implémentant {@link ViewController}, cela <b>doit</b> être un contrôleur, et non une vue.
 *
 * @see ViewLoader
 * @see SubViewLoader
 * @see ViewController
 */
public abstract class AbstractViewLoader<T extends ViewController> {

    /** Contrôleur qui a été chargé. */
    private T controller;
    /** Pack de ressources associé au contrôleur à charger. */
    private final ResourceBundle bundle;
    /** Chargeur de fichier FXML. */
    private final FXMLLoader loader;
    /** Stage créé pour la vue à charger. */
    final Stage stage;

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
     *         Autrement, il serait mieux de créer l'autre constructeur.
     * @param _FXMLPath
     *         {@link String} menant au fichier FXML lié à la vue que l'on veut ouvrir. <br>
     *         Typiquement accessible par des attributs de classes dans les contrôleurs (<b>T</b>).
     *
     * @see ViewLoader#createMainMenuLoader(Stage)
     */
    AbstractViewLoader(Stage _stage, String _FXMLPath) {
        this.stage = _stage;

        this.bundle = ResourceBundle.getBundle("controller/menus/menus", GameApplication.getLANG());

        URL FXMLfile = ViewLoader.class.getResource(_FXMLPath);
        this.loader = new FXMLLoader(FXMLfile);
        this.loader.setResources(bundle);
    }

    /**
     * Constructeur privé.
     * <p>
     * L'instanciation hors de la classe se fait avec les méthodes de fabrique (cf. section <i>See Also</i>).
     * <p>
     * Le {@link ResourceBundle pack de ressources} est automatiquement considéré comme étant selon des vues
     * "classiques", excluant donc la vue de jeu.
     * <p>
     * Le {@link Stage} est instancié automatiquement, donc il ne faut pas l'appeler depuis une classe modifiant
     * le {@link Stage} (telle que {@link javafx.application.Application}).
     *
     * @param _FXMLPath
     *         {@link String} menant au fichier FXML lié à la vue que l'on veut ouvrir. <br>
     *         Typiquement accessible par des attributs de classes dans les contrôleurs (<b>T</b>).
     *
     * @see #AbstractViewLoader(Stage, String)
     */
    AbstractViewLoader(String _FXMLPath) {
        this.stage = new Stage();

        this.bundle = ResourceBundle.getBundle("controller/menus/menus", GameApplication.getLANG());

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
        scene.getStylesheets().add(this.controller.getCSSPath() + GameApplication.getThemeSuffix());

        // Association de la scène et du stage, et affichage.
        this.stage.setScene(scene);
    }

    /**
     * Affiche la vue.
     * <p>
     * Déléguée de {@link Stage#show()}.
     *
     * @throws IllegalStateException
     *         si la méthode n'est pas appelée sur le Thread JavaFX.
     */
    public void show() {
        this.stage.show();
    }
}
