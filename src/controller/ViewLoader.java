/*
 * Copyright (c) 10/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller;

import controller.menus.mainMenu.MainMenuController;
import javafx.stage.Stage;

/**
 * Classe ViewLoader.
 * <p>
 * Permet le chargement des vues et de leurs contrôleurs, tel que {@link MainMenuController le menu principal},
 * et ainsi d'améliorer la lisibilité des classes chargeant des vues,
 * ainsi qu'éviter de la redondance de code pour le chargement de vue.
 *
 * <p>
 * Les vues ne devant se charger sans contrôleur, il n'a donc pas été jugé nécéssaire de créer une classe pour
 * cela et l'ambiguité du nom de la classe comme acceptable.
 *
 * <p>
 * S'il s'agit d'une sous-vue, il vaut mieux utiliser {@link SubViewLoader}.
 *
 * @see AbstractViewLoader
 * @see SubViewLoader
 */
public final class ViewLoader extends AbstractViewLoader<ViewController> {

    /**
     * Constructeur privé.
     * <p>
     * L'instanciation hors de la classe se fait avec les méthodes de fabrique (cf. section <i>See Also</i>).
     * <p>
     * Le {@link java.util.ResourceBundle pack de ressources} est automatiquement considéré comme étant selon
     * des menus, excluant donc la vue de jeu.
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
        super(_stage, _FXMLPath);
    }

    /**
     * Méthode de fabrique.
     * <p>
     * Fournit une instance pouvant charger la vue principale.
     * <p>
     * La méthode utilise une instance de {@link Stage} passée en paramètre ; il est donc mieux d'utiliser cette version
     * de la méthode uniquement si cette instance est déjà créée ou est particulière, et, dans d'autres cas,
     * de créer un autre constructeur sans paramètre de {@link Stage} et une surcharge de cette méthode : <br>
     * {@code public static ViewLoader createMainMenuLoader()}
     *
     * @param _stage
     *         Instance particulière de {@link Stage}, créée ou modifiée par la classe appellante (par ex.,
     *         le paramètre <i>primaryStage</i> de la méthode <i>Application.start()</i>).
     *
     * @return Retourne une instance {@link ViewLoader} chargée de s'occuper du menu principal.
     *
     * @see javafx.application.Application#start(Stage)
     */
    public static ViewLoader createMainMenuLoader(Stage _stage) {
        return new ViewLoader(_stage, MainMenuController.FXMLPath);
    }
}
