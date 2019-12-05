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

/**
 * Enumération Theme.
 * <p>
 * Permet d'avoir plusieurs thèmes CSS sans devoir ajouter d'éléments superflus, ainsi que connaître les éléments
 * disponibles : pour l'instant, il y un thème clair et un thème sombre.
 * <p>
 * Utilisée par les interfaces, telles que le {@link controller.menus.mainMenu.MainMenuController menu principal},
 * et les vues de jeux.
 */
public enum Theme {

    /** Thème clair. */
    LIGHT("light"),
    /** Thème sombre. */
    DARK("dark");

    /** Suffixe à ajouter aux noms de fichier CSS. */
    private final String keySuffix;

    /**
     * Constructeur privé.
     * <p>
     * Initialise l'attribut avec l'extension ".css" afin d'améliorer la lisibilité d'utilisation, ainsi que l'ajout
     * d'un underscore pour lier le nom principal du fichier CSS avec le thème.
     *
     * Le masque correspond donc à {@code _suffix.css}.
     *
     * @param _keySuffix
     *         Suffixe correspondant au thème, sans superflu.
     */
    Theme(String _keySuffix) {
        this.keySuffix = "_" + _keySuffix + ".css";
    }

    /**
     * Accesseur de l'attribut <i>keySuffix</i>.
     * <p>
     * Permet de changer le thème CSS sans devoir rajouter de suffixe selon le thème, ni devoir rajouter la extension.
     *
     * @return la valeur de l'attribut.
     */
    public String getKeySuffix() {
        return this.keySuffix;
    }
}
