/*
 * Copyright (c) 17/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller.menus.settings;

import application.GameApplication;
import controller.AbstractViewController;
import controller.SubViewLoader;
import controller.Theme;
import controller.ViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Contrôleur associé au menu des options.
 * <p>
 * Les options ne permettent (pour l'instant) que de changer le thème de l'application.
 *
 * <p>
 * Le menu est composé de deux ensembles de boutons : <br>
 *      - L'ensemble des boutons radios pour les <b>thèmes</b> : <br>
 *              - Thème clair - {@link #lightTheme} <br>
 *              - Thème sombre - {@link #darkTheme} <br>
 * <p>
 *      - L'ensemble des boutons d'actions sur la <b>fenêtre</b> : <br>
 *              - Bouton {@link #onValidate() "OK"}, changeant le thème de l'{@link GameApplication application}. <br>
 *              - Bouton {@link #onCancel() "Annuler"}, annulant les modifications et fermant la fenêtre. <br>
 *              - Bouton {@link #onPreview() "Aperçu"}, appliquant les changements <b>que</b> sur la fenêtre. <br>
 *
 * <p>
 * Utilise les instances de la {@link AbstractViewController classe-mère} :
 *      - Le {@link Stage} afin de lancer soi-même des requêtes de fermetures. <br>
 *      - La {@link controller.DialogBoxFactory fabrique à boîte de dialogue}, pour simplifier la lisibilité de
 * la classe, et séparer les responsabilités selon un <i>design pattern</i> de fabrique.
 *
 * <p>
 * Les attributs de classe {@link #FXMLPath} et {@link #CSSPath} ne changent pas d'un lancement à un autre,
 * et sont utilisés par le {@link SubViewLoader chargeur de vue}.
 *
 * @see AbstractViewController
 * @see ViewController
 * @see SubViewLoader
 */
public class SettingsController extends AbstractViewController implements ViewController {

    /** Chemin menant au fichier CSS associé au menu, <b>sans</b> le masque de thème et l'extension. */
    private static final String CSSPath = "/controller/menus/settings/SettingsMenu";
    /** Chemin menant au fichier FXML associé au menu principal. */
    public static final String FXMLPath = "/controller/menus/settings/SettingsMenu.fxml";

    /** boolean indiquant s'il y a eu un changement appliqué ({@link #previewThemeChange()}. */
    private boolean hasChanged;
    /** Pack de ressources associé à ce contrôleur. */
    private ResourceBundle bundle;
    /** Suivi du thème actuel de la fenêtre. */
    private Toggle currentSceneTheme;

    /** Groupe de boutons radio associés aux thèmes. */
    @FXML private ToggleGroup themeToggle;
    /** Bouton radio associé au thème clair. */
    @FXML private Toggle lightTheme;
    /** Bouton radio associé au thème sombre. */
    @FXML private Toggle darkTheme;

    /**
     * Actions effectuées durant le {@link javafx.fxml.FXMLLoader chargement de la vue FXML}.
     * <p>
     * <b>Ne doit pas avoir de paramètres afin de pouvoir être lancée automatiquement.</b>
     * <p>
     * Permet de servir de pseudo-constructeur et d'initialiser les attributs {@link #hasChanged} et
     * {@link #currentSceneTheme} ; ainsi que les éléments graphiques associés aux paramètres (thèmes).
     *
     * @see javafx.fxml.FXMLLoader
     * @see ToggleGroup#selectToggle(Toggle)
     */
    @FXML
    private void initialize() {
        // Initialisation des attributs.
        this.hasChanged = false;
        this.currentSceneTheme = parseApplicationTheme();

        // Ajuste la sélection du thème.
        this.themeToggle.selectToggle(currentSceneTheme);
    }

    /**
     * Détermine la valeur de bouton radio associé au thème de l'application.
     * <p>
     * Utilise une structure ternaire où on associe la valeur de l'attribut {@link #lightTheme} ou {@link #darkTheme}.
     * <p>
     * Si un troisième thème est introduit, il faudra changer de structure vers une structure <i>switch</i> ou
     * une structure avec des conditions (<i>if</i>).
     *
     * @return Retourne la valeur déterminée : <br>
     * - {@link #lightTheme} si on utilise le thème clair.
     * - {@link #darkTheme} sinon.
     *
     * @see GameApplication#getTHEME()
     * @see Theme
     */
    private Toggle parseApplicationTheme() {
        return GameApplication.getTHEME() == Theme.LIGHT ? this.lightTheme : this.darkTheme;
    }

    /**
     * Transfère l'attribut {@link ResourceBundle} depuis l'instance {@link SubViewLoader} accédant à la vue.
     * <p>
     * Devrait être appelée par l'instance {@link SubViewLoader} qui a instancié le pack.
     *
     * @param _bundle
     *         Le {@link ResourceBundle pack de ressources} associé à ce contrôleur.
     *
     * @throws IllegalArgumentException
     *         Devrait être Lancée si le {@link ResourceBundle pack} existe déjà à l'appel de cette méthode et
     *         ne change pas de langue. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    @Override
    public void initBundle(ResourceBundle _bundle) {
        super.initBundle(_bundle);
        this.bundle = _bundle;
    }

    /**
     * Permet l'accès au chemin du fichier CSS à travers une instance.
     * <p>
     * Cela permet à {@link SubViewLoader} de ne pas avoir à utiliser un paramètre supplémentaire dans le constructeur,
     * ainsi de ne pas forcer l'attribut <i>CSSPath</i> en tant qu'attribut de classe ou d'instance.
     * <p>
     * À surcharger ({@link Override}) si la classe-fille n'utilise pas le fichier CSS du menu principal.
     *
     * @return Retourne le chemin vers le fichier CSS associé au contrôleur.
     */
    @Override
    public String getCSSPath() {
        return SettingsController.CSSPath;
    }

    /**
     * Transfère l'attribut {@link Stage} depuis l'instance {@link SubViewLoader} accédant à la vue.
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
        super.initStage(_stage);
        _stage.setTitle(this.bundle.getString("settings"));
    }

    /**
     * Affiche une {@link Alert boîte de dialogue} pour confirmer la fermeture de la fenêtre,
     * et interprète ses résultats.
     * <p>
     * Invoquée lors d'une demande de fermeture de la fenêtre, par la propriété <i>onCloseRequest</i> du {@link Stage}.
     * <p>
     * Est habilitée à annuler le choix de thème de la fenêtre.
     *
     * @return   - {@code true} s'il n'y a pas de changements <br>
     *           - {@code true} si l'utilisateur clique sur les boutons <i>OUI</i> ou <i>NON</i>. <br>
     *           - {@code false} sinon.
     *
     * @see Alert
     * @see controller.DialogBoxFactory
     */
    @Override
    protected boolean isCloseable() {
        if (this.hasChanged || this.themeToggle.getSelectedToggle() != this.currentSceneTheme) {
            Optional<ButtonType> dialogResult = this.dialogFactory.validateThemeChangeDialog().showAndWait();

            if (dialogResult.isPresent()) {
                ButtonType buttonType = dialogResult.get();

                if (buttonType == ButtonType.YES) {
                    this.validateThemeChange();
                    return true;
                } else return buttonType == ButtonType.NO;
            }
            return false;
        }
        return true;
    }

    /**
     * Change le thème de l'application.
     * <p>
     * Si le thème choisi est le même que le thème actuel de l'application, le changement n'est pas pris en compte.
     */
    private void validateThemeChange() {
        Toggle selectedToggle = this.themeToggle.getSelectedToggle();

        if (selectedToggle != this.parseApplicationTheme()) {
            if (selectedToggle == this.lightTheme) {
                GameApplication.setTHEME(Theme.LIGHT);
            } else {
                GameApplication.setTHEME(Theme.DARK);
            }
        }
    }

    /**
     * Applique le thème sélectionné à la vue.
     * <p>
     * Vérifie si le thème actuel de la vue et le thème sélectionné ne soient pas les même.
     * Applique ensuite le thème, au travers de {@link #previewSelectedTheme(Toggle)} et
     * change les attributs :
     *      - Change {@link #hasChanged} à {@code true}.
     *      - Met à jour {@link #currentSceneTheme}.
     *
     * @see #previewSelectedTheme(Toggle)
     * @see #onPreview()
     */
    private void previewThemeChange() {
        Toggle selectedToggle = this.themeToggle.getSelectedToggle();

        if (selectedToggle != this.currentSceneTheme) {
            this.previewSelectedTheme(selectedToggle);
            this.hasChanged = true;
            this.currentSceneTheme = selectedToggle;
        }
    }

    /**
     * Applique un thème à la vue actuelle.
     * <p>
     * Change le fichier CSS associé à cette vue au travers d'un {@link ObservableList#clear() clear()} et d'un
     * {@link ObservableList#add(Object) add(String)}.
     *
     * @param _theme
     *         Thème sous la forme du bouton radio associé.
     *
     * @see #previewThemeChange()
     * @see #validateThemeChange()
     */
    private void previewSelectedTheme(Toggle _theme) {
        ObservableList<String> stylesheets = this.stage.getScene().getStylesheets();
        stylesheets.clear();

        if (_theme == this.lightTheme) {
            stylesheets.add(CSSPath + Theme.LIGHT.getKeySuffix());
        } else {
            stylesheets.add(CSSPath + Theme.DARK.getKeySuffix());
        }
    }

    /// --- FXML Methods --- ///

    /**
     * Action correspondante au bouton "OK".
     * <p>
     * Applique les changements à l'application, au travers de {@link #validateThemeChange()}, et ferme la fenêtre.
     *
     * @see #validateThemeChange()
     * @see #onValidate()
     * @see #onPreview()
     */
    @FXML
    void onValidate() {
        this.validateThemeChange();
        this.stage.close();
    }

    /**
     * Action correspondante au bouton "Annuler".
     * <p>
     * Ferme la fenêtre sans appliquer les changements et sans demander une confirmation de l'utilisateur ou
     * s'il veut garder les modifications.
     * <p>
     * Associée à la touche "Échap", au travers la propriété {@code cancelButton} du FXML.
     *
     * @see Stage#close()
     */
    @FXML
    void onCancel() {
        this.stage.close();
    }

    /**
     * Action correspondante au bouton "Aperçu".
     * <p>
     * Applique les changements à la fenêtre seulement, et la laisse ouverte.
     *
     * @see #onValidate()
     * @see #previewThemeChange()
     */
    @FXML
    void onPreview() {
        this.previewThemeChange();
    }
}
