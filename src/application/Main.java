/*
 * Copyright (c) 09/11/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package application;

import controller.menus.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
     * Méthode appellée au lancement du Thread JavaFX.
     * <p>
     * Ferme l'application en cas de problème au chargement de la fenêtre.
     *
     * @param primaryStage {@link Stage} fournit par JavaFX pour la première fenêtre qui va s'afficher (le menu principal).
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialisation de l'interface (scène), ainsi que sa langue et ses feuilles de styles.
            ResourceBundle bundle = ResourceBundle.getBundle("controller/menus/menus", Main.lang);
            FXMLLoader loader = new FXMLLoader((MainMenu.class.getResource("/controller/menus/MainMenu.fxml")));
            loader.setResources(bundle);
            VBox root = loader.load();
            Scene scene = new Scene(root);

            // Vous pouvez utiliser la méthode addCSSFiles s'il y a plusieurs fichiers CSS à ajouter.
            scene.getStylesheets().add("controller/menus/MainMenu.css");

            // Association de la scène et du stage, et affichage.
            primaryStage.setScene(scene);
            primaryStage.show();

            // Association du contrôleur avec le stage et le pack de langue.
            MainMenu controller = loader.getController();
            controller.setBundle(bundle);
            controller.setStage(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
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
     * Utilisée par mesure de simplicité pour ajouter plusieurs fichiers CSS à une {@link Scene scène} donnée.
     *
     * @param _scene             {@link Scene Scène} à laquelle on veut ajouter des fichiers CSS.
     * @param _fileRelativePaths Ensemble des liens relatifs en {@link String} menant aux fichiers CSS concernés.
     *
     * @see Scene#getStylesheets() pour ajouter un fichier CSS à une scène.
     */
    private static void addCSSFiles(Scene _scene, String... _fileRelativePaths) {
        for (String _str : _fileRelativePaths) {
            _scene.getStylesheets().add(_str);
        }
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
