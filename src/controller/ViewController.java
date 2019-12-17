/*
 * Copyright (c) 17/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller;

import controller.menus.MainMenuController;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Interface ViewController.
 * <p>
 * Fournit un archétype de méthodes pour les contrôleurs de vue (y compris la vue de jeu), afin de pouvoir
 * créer un chargeur d'interface ({@link ViewLoader}) général plutôt que
 * d'implémentations redondantes pour chaque contrôleur.
 *
 * <p>
 * L'interface concerne deux instances créées par {@link ViewLoader} au chargement :
 *      - Le {@link Stage stage}.
 *      - Le {@link ResourceBundle pack de ressources}.
 * Ainsi que le {@link String} contenant le chemin vers le fichier CSS associé au contrôleur.
 *
 * <p>
 * Les méthodes d'initialisationn'impliquent pas forcément la création d'attributs {@link Stage} ou
 * {@link ResourceBundle}, car ils peuvent être directement envoyés vers une autre instance, telle que les
 * {@link controller.DialogBoxFactory}, (cf. {@link MainMenuController#initBundle(ResourceBundle) MainMenuController}).
 *
 * @see ViewLoader
 * @see Stage
 * @see ResourceBundle
 */
public interface ViewController {

    /**
     * Transfère l'attribut {@link Stage} depuis l'instance {@link ViewLoader} accédant à la vue.
     * <p>
     * Devrait être appelée par l'instance {@link ViewLoader} qui a instancié le stage.
     *
     * @param _stage
     *         Le {@link Stage stage} associé à ce contrôleur.
     *
     * @throws IllegalStateException
     *         Devrait être lancée si le {@link Stage} existe déjà à l'appel de cette méthode. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    void initStage(Stage _stage);

    /**
     * Transfère l'attribut {@link ResourceBundle} depuis l'instance {@link ViewLoader} accédant à la vue.
     * <p>
     * Devrait être appelée par l'instance {@link ViewLoader} qui a instancié le pack.
     *
     * @param _bundle
     *         Le {@link ResourceBundle pack de ressources} associé à ce contrôleur.
     *
     * @throws IllegalArgumentException
     *         Devrait être Lancée si le {@link ResourceBundle pack} existe déjà à l'appel de cette méthode et
     *         ne change pas de langue. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    void initBundle(ResourceBundle _bundle);

    /**
     * Permet l'accès au chemin du fichier CSS à travers une instance.
     * <p>
     * Cela permet à {@link ViewLoader} de ne pas avoir à utiliser un paramètre supplémentaire dans le constructeur,
     * ainsi de ne pas forcer un attribut en tant qu'attribut de classe ou d'instance aux contrôleurs.
     *
     * @return Retourne le chemin vers le fichier CSS associé au contrôleur.
     */
    String getCSSPath();
}
