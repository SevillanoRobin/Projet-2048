/*
 * Copyright (c) 08/12/2019
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

/**
 * Application du 2048 3D.
 * <p>
 * Hérite de la classe abstraite {@link Application} afin de pouvoir obtenir des protocoles particuliers au démarrage et
 * à la fin de l'exécution de l'application.
 */
public class GameApplication extends Application {

    /**
     * Démarre l'application une fois initialisée et prête à être lancée par le sytème.
     *
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
}
