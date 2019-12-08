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

import controller.Theme;
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
 * Comprend des paramètres utilisés par l'application en tant qu'attributs de classe privés :
 *      - La {@link #LANG langue} utilisée avec un accesseur public et un modificateur <i>package-private</i>.
 *      - Le {@link #THEME thème} utilisé avec un accesseur et un modificateur publics.
 *
 * @see Locale
 * @see Theme
 */
public class GameApplication extends Application {
    /** Langue utilisée par l'application. */
    private static Locale LANG;
    /** Thème utilisé par l'interface de l'application. */
    private static Theme THEME = Theme.LIGHT;

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

    /**
     * Accesseur de l'attribut <i>THEME</i>.
     * <p>
     * Permet de connaître le thème de l'application peu importe la classe qui le demande.
     *
     * @return la valeur de l'attribut.
     */
    public static Theme getTHEME() {
        return GameApplication.THEME;
    }

    /**
     * Accesseur de l'attribut <i>keySuffix</i> de l'attribut <i>THEME</i> pour les chemins de fichiers CSS.
     * <p>
     * Permet de connaître le chemin du fichier CSS associé peu importe la classe qui le demande.
     *
     * @return la valeur de l'attribut.
     */
    public static String getThemeSuffix() {
        return GameApplication.THEME.getKeySuffix();
    }

    /**
     * Modificateur de l'attribut <i>THEME</i>.
     * <p>
     * Permet de modifier le thème de l'application peu importe la classe qui le demande.
     * Typiquement utilisé par le menu d'options du {@link MainMenuController menu principal}.
     *
     * @param _THEME
     *         La nouvelle valeur de l'attribut.
     */
    public static void setTHEME(Theme _THEME) {
        GameApplication.THEME = _THEME;
    }
}
