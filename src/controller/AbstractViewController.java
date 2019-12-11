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

import controller.menus.MainMenuController;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Classe abstraite AbstractViewController.
 * <p>
 * Utilisée pour éviter de la redondance de code entre les différentes implémentations de {@link ViewController}.
 *
 * <p>
 * Comporte un attribut pour le {@link Stage} afin de lancer soi-même des requêtes de fermetures,
 * ainsi qu'une {@link DialogBoxFactory fabrique à boîte de dialogue}, pour simplifier la lisibilité de la classe, et
 * séparer les responsabilités selon un <i>design pattern</i> de fabrique.
 * <p>
 * Ces attributs exigent des éléments créés par les classes chargeant le FXML, qui doivent donc être transférés,
 * au moyen des méthodes {@link #initStage(Stage)} et {@link #initBundle(ResourceBundle)} respectivement.
 *
 * @see ViewController
 */
public abstract class AbstractViewController implements ViewController {

    /** Fabrique à boîte de dialogue associée à ce contrôleur. */
    protected DialogBoxFactory dialogFactory;
    /** Stage associé à ce contrôleur. */
    protected Stage stage;

    /**
     * Transfère l'attribut {@link Stage} depuis l'instance {@link ViewLoader} accèdant à la vue.
     *
     * <p>
     * Modifie la propriété <i>onCloseRequest</i> du {@link Stage} afin d'appeler {@link
     * #isCloseable()} et de devoir obtenir une validation avant de fermer la fenêtre.
     * <p>
     * Peut être surchargée avec un appel sur cette méthode si la classe modifie d'autres propriétés du {@link Stage}.
     *
     * @param _stage
     *         Le {@link Stage stage} associé à ce contrôleur.
     *
     * @throws IllegalStateException
     *         Lancée si le {@link Stage} existe déjà à l'appel de cette méthode. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    @Override
    public void initStage(Stage _stage) {
        if (this.stage != null) {
            throw new IllegalStateException("The stage already exists");
        }
        this.stage = _stage;

        this.stage.setOnCloseRequest(event -> {
            if (this.isCloseable()) {
                this.stage.close();
            } else {
                event.consume();
            }
        });
    }

    /**
     * Permet l'accès au chemin du fichier CSS à travers une instance.
     * <p>
     * Cela permet à {@link ViewLoader} de ne pas avoir à utiliser un paramètre supplémentaire dans le constructeur,
     * ainsi de ne pas forcer l'attribut <i>CSSPath</i> en tant qu'attribut de classe ou d'instance.
     * <p>
     * À surcharger ({@link Override}) si la classe-fille n'utilise pas le fichier CSS du menu principal.
     *
     * @return Retourne le chemin vers le fichier CSS associé au contrôleur.
     */
    @Override
    public String getCSSPath() {
        return MainMenuController.CSSPath;
    }

    /**
     * Transfère une instance {@link ResourceBundle} vers l'instance {@link DialogBoxFactory} afin d'obtenir des
     * boîtes de dialogues changeantes selon la langue de l'application.
     * <p>
     * Devrait être appelée par l'instance {@link ViewLoader} qui a instancié le pack.
     * <p>
     * Peut être surchargée avec un appel sur cette méthode si la classe utilise également l'instance du {@link
     * ResourceBundle pack de ressources}.
     *
     * @param _bundle
     *         Le {@link ResourceBundle pack de ressources} associé à ce contrôleur.
     *
     * @throws IllegalArgumentException
     *         Lancée si le {@link ResourceBundle pack} existe déjà à l'appel de cette méthode et ne change pas de
     *         langue. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     * @see DialogBoxFactory
     */
    @Override
    public void initBundle(ResourceBundle _bundle) {
        if (dialogFactory == null) {
            this.dialogFactory = new DialogBoxFactory(_bundle);
        } else {
            this.dialogFactory.setBundle(_bundle);
        }
    }

    /**
     * Affiche une {@link javafx.scene.control.Alert boîte de dialogue} pour confirmer la fermeture de la fenêtre,
     * et interprête ses résultats.
     * <p>
     * Invoquée lors d'une demande de fermeture de la fenêtre, par la propriété <i>onCloseRequest</i> du {@link Stage}.
     * <p>
     * Permet de pouvoir bloquer la fermeture de l'application si non-voulue.
     *
     * @return {@code true} si la boîte de dialogue est fermée avec le bouton "OK", {@code false} autrement.
     *
     * @see DialogBoxFactory
     * @see javafx.scene.control.Alert
     */
    protected abstract boolean isCloseable();
}
