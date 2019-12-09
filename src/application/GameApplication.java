/*
 * Copyright (c) 09/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package application;

import controller.ViewLoader;
import controller.menus.mainMenu.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Application du 2048 3D.
 * <p>
 * Hérite de la classe abstraite {@link Application} afin de pouvoir obtenir des protocoles particuliers au démarrage
 * et à la fin de l'exécution de l'application.
 * <p>
 * Comprend la langue utilisée par l'application ({@link #LANG}) en tant qu'attribut de classe privé avec un
 * accesseur public et modificateur <i>package-private</i>.
 *
 * @see Locale
 */
public class GameApplication extends Application {
    /** Langue utilisée par l'application. */
    private static Locale LANG;

    static void launchApplication(String... _args) {
        GameApplication.launch(_args);
    }

    /**
     * Démarre l'application une fois initialisée et prête à être lancée par le sytème.
     * <p>
     * Utilise une instance de la classe {@link ViewLoader} afin de charger le menu principal.
     * <p>
     * Ferme l'application en cas de problème au chargement de la fenêtre.
     *
     * @param primaryStage
     *         {@link Stage} fournit par JavaFX pour la première fenêtre qui va s'afficher (le menu principal).
     *
     * @see ViewLoader
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

    /// --- ACCESSEURS & MODIFICATEURS --- ///

    /**
     * Accesseur de l'attribut <i>LANG</i>.
     * <p>
     * Permet de connaître la langue de l'application peu importe la classe qui la demande.
     *
     * @return la valeur de l'attribut.
     */
    public static Locale getLANG() {
        return LANG;
    }

    /**
     * Modificateur de l'attribut <i>LANG</i>.
     * <p>
     * Permet aux classes du package de modifier la langue de l'application.
     *
     * @param _LANG
     *         La nouvelle valeur de l'attribut.
     *
     * @throws IllegalArgumentException
     *         Lancée si une langue non-implémentée est suggérée. <br>
     *         N'est pas censée être rapprapée, étant une erreur de programmation.
     */
    static void setLANG(Locale _LANG) {
        if (_LANG != Locale.FRENCH && _LANG != Locale.getDefault()) {
            throw new IllegalArgumentException("The wished language isn't implemented.");
        }
        GameApplication.LANG = _LANG;
    }
}
