/*
 * Copyright (c) 16/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType;

/**
 * Fabrique de boîtes de dialogue.
 * <p>
 * Permet de simplifier la création de nouvelles {@link Alert boîtes de dialogue}, tout en améliorant la lisibilité et
 * en évitant la redondance entre les méthodes de création de nouvelles boîtes de dialogue.
 * <p>
 * <p>
 * Nécéssite un {@link ResourceBundle pack de ressources} afin de pouvoir fournir des éléments textuels associés à
 * la langue de l'application (cf. {@link application.Main}).
 * Un modificateur est disponible dans le cas d'un changement de langue lors de l'exécution.
 * <p>
 * <p>
 * Les boîtes de dialogue créées ne devraient pas être utilisées ou affichées par cette classe,
 * par respect du principe de fabrique.
 * <p>
 * Une exception du type {@link MissingResourceException} risque d'être lancée si une classe appelle des méthodes
 * qu'elle n'est
 * pas censée accéder.
 * Cela correspond à une clé de ressource non existante dans le pack associé.
 *
 * @see Alert
 * @see AlertType
 */
public class DialogBoxFactory {
    /** Pack de ressources associé à cette instance. */
    private ResourceBundle bundle;

    /**
     * Constructeur.
     * <p>
     * Initialise le {@link ResourceBundle pack de ressources} selon le paramètre.
     *
     * @param _bundle
     *         Pack de ressources à associer à cette instance.
     */
    DialogBoxFactory(ResourceBundle _bundle) {
        this.bundle = _bundle;
    }

    /**
     * Modificateur du pack de ressources.
     * <p>
     * À n'utiliser qu'en cas de changements de langue lors de l'exécution de l'application ; au risque de lancer une
     * {@link IllegalArgumentException exception} autrement.
     *
     * @param _bundle
     *         Nouveau pack de ressources ; doit avoir une langue différente de l'ancien pack.
     *
     * @throws IllegalArgumentException
     *         Exception lancée si la langue est la même entre les deux packs. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    void setBundle(ResourceBundle _bundle) throws IllegalArgumentException {
        if (this.bundle.getLocale() == _bundle.getLocale()) {
            throw new IllegalArgumentException("The locale remains unchanged between the bundles.");
        }
        this.bundle = _bundle;
    }

    /**
     * Crée une {@link Alert boîte de dialogue} de type {@link AlertType#CONFIRMATION} pour confirmer, ou annuler,
     * une demande de fermeture de l'application.
     * <p>
     * Utilise les ressources du type <i>exit_confirmation</i>.
     *
     * @return la boîte de dialogue créée, si lieu.
     *
     * @throws MissingResourceException
     *         Exception lancée si le {@link ResourceBundle pack de ressources} ne contient pas les clés demandées. <br>
     *         Cela implique que l'instance tente d'utiliser une méthode non-affiliée à son domaine. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    public Alert CloseRequestDialog() throws MissingResourceException {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle(bundle.getString("exit_confirmation.window"));
        alert.setContentText(bundle.getString("exit_confirmation.msg"));

        return alert;
    }

    /**
     * Crée une {@link Alert boîte de dialogue} de type {@link AlertType#CONFIRMATION} pour confirmer, ou annuler,
     * une demande de fermeture de la fenêtre des options avec des modifications (sélection et aperçu).
     * <p>
     * Utilise les ressources du type <i>themes.confirm</i>.
     *
     * @return la boîte de dialogue créée, si lieu.
     *
     * @throws MissingResourceException
     *         Exception lancée si le {@link ResourceBundle pack de ressources} ne contient pas les clés demandées. <br>
     *         Cela implique que l'instance tente d'utiliser une méthode non-affiliée à son domaine. <br>
     *         N'est pas censée être rattrapée, étant un problème de programmation.
     */
    public Alert validateThemeChangeDialog() throws MissingResourceException {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle(bundle.getString("themes.confirm"));
        alert.setContentText(bundle.getString("themes.confirm.desc"));

        changeButtonTypes(alert);

        return alert;
    }

    public Alert furnishLeaveConfirmationDB() {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle(bundle.getString("exit_confirmation.window"));
        alert.setContentText(bundle.getString("exit_confirmation.msg"));

        changeButtonTypes(alert);

        return alert;
    }

    private void changeButtonTypes(Alert _alert) {
        _alert.getButtonTypes().remove(ButtonType.OK);
        _alert.getButtonTypes().add(ButtonType.YES);
        _alert.getButtonTypes().add(ButtonType.NO);
    }

    public Alert furnishSaveConfirmationDB(String _name) {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle(bundle.getString("save_confirmation.window"));
        alert.setContentText(bundle.getString("save_confirmation.msg") + " " + _name);

        return alert;
    }

    public Alert furnishGameBugWarning() {
        Alert alert = new Alert(AlertType.WARNING);

        alert.setTitle(bundle.getString("game_bug_warning.window"));
        alert.setContentText(bundle.getString("game_bug_warning.msg"));

        return alert;
    }
}
