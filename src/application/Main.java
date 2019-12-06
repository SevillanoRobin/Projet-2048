/*
 * Copyright (c) 07/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package application;

import controller.menus.ViewLoader;
import controller.menus.mainMenu.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Classe principale.
 * <p>
 * Hérite de la classe abstraite {@link Application} afin de pouvoir obtenir des protocoles particuliers au démarrage et
 * à la fin de l'éxecution de l'application.
 */
public class Main extends Application {
    /** Langue utilisée par l'interface de l'application. */
    private static Locale lang;

    /**
     * Accesseur de l'attribut <i>lang</i>.
     * <p>
     * Permet de connaître la langue de l'application peu importe la classe qui le demande.
     *
     * @return la valeur de l'attribut.
     */
    public static Locale getLang() {
        return Main.lang;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage
     *         the primary stage for this application, onto which
     *         the application scene can be set. The primary stage will be embedded in
     *         the browser if the application was launched as an applet.
     *         Applications may create other stages, if needed, but they will not be
     *         primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) {
        ViewLoader<MainMenuController> viewLoader = ViewLoader.createMainMenuLoader(primaryStage);
        viewLoader.loadView();
    }

    /**
     * Invoquée quand l'application est arrêtée ou fermée.
     * <p>
     * Permet d'arrêter correctement le programme.
     */
    @Override
    public void stop() {
        System.exit(0);
    }

    /**
     * Vérifie pour la présence de paramètres de langue, et ajuste la langue en fonction du résultat.
     *
     * @param _args Paramètres donnés par la méthode {@link Main#main(String[])}
     */
    private static void checkForLangArgs(List<String> _args) {
        int langInd = _args.indexOf("-lang");
        if (langInd != -1 && "en".equals(_args.get(langInd + 1))) {
            Main.lang = Locale.getDefault();
        }

        Main.lang = Locale.FRENCH;
    }

    /**
     * Méthode principale.
     * <p>
     * Peut lancer l'application textuelle ou changer la langue de l'interface selon les paramètres données.
     *
     * @param args Paramètres d'exécution.
     */
    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("text")) {
            Main_Terminal.main(args);
        } else {
            checkForLangArgs(arguments);
            launch(args);
        }
    }
}
