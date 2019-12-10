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
 * Classe SubViewLoader.
 * <p>
 * Permet le chargement des sous-vues et leurs contrôleurs, tel que {@link MainMenuController le menu principal},
 * et ainsi d'améliorer la lisibilité des classes chargeant des vues,
 * ainsi qu'éviter de la redondance de code pour le chargement de vue.
 *
 * <p>
 * Cette classe obtient les méthodes {@link #close()} et {@link #showAndWait()} afin de donner plus de pouvoir
 * à la vue-mère sur la sous-vue au travers de ces instances.
 * <p>
 * Pour les vues principales ({@link ViewController}), il faut utiliser la classe ({@link ViewLoader}).
 *
 * @see AbstractViewLoader
 * @see ViewController
 * @see ViewLoader
 */
public final class SubViewLoader extends AbstractViewLoader<ViewController> {

    /**
     * Constructeur privé.
     * <p>
     * L'instanciation hors de la classe se fait avec les méthodes de fabrique (cf. section <i>See Also</i>).
     * <p>
     * Le {@link java.util.ResourceBundle pack de ressources} est automatiquement considéré comme étant selon
     * des menus, excluant donc la vue de jeu.
     * <p>
     * Initialise la {@link Stage vue-mère}.
     *
     * @param _FXMLPath
     *         {@link String} menant au fichier FXML lié à la vue que l'on veut ouvrir. <br>
     *         Typiquement accessible par des attributs de classes dans les contrôleurs (<b>T</b>).
     *
     * @see AbstractViewLoader#AbstractViewLoader(Stage, String)
     */
    private SubViewLoader(String _FXMLPath) {
        super(_FXMLPath);
    }

    /**
     * Affiche la vue et attend qu'elle soit fermée.
     * <p>
     * Déléguée de {@link Stage#show()}.
     *
     * @throws IllegalStateException
     *         si la méthode n'est pas appelée sur le Thread JavaFX.
     * @throws IllegalStateException
     *         si la méthode est appelée pendant le traitement des animations et de la structure.
     * @throws IllegalStateException
     *         si la méthode est appelée sur le <i>primary stage</i> ({@link javafx.application.Application#start(Stage)}
     * @throws IllegalStateException
     *         si le stage est déjà affiché.
     */
    public void showAndWait() {
        this.stage.showAndWait();
    }

    /**
     * Ferme la vue.
     * <p>
     * Déléguée de {@link Stage#close}.
     *
     * @throws IllegalStateException
     *         si la méthode n'est pas appelée sur le Thread JavaFX.
     */
    public void close() {
        this.stage.close();
    }
}
