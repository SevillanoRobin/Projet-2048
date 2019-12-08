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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Classe principale.
 */
public class Main {
    /** Langue utilisée par l'interface de l'application. */
    private static Locale lang;

    /**
     * Accesseur de l'attribut <i>lang</i>.
     * <p>
     * Permet de connaître la langue de l'application peu importe la classe qui le demande.
     *
     * @return la valeur de l'attribut.
     */
    public static Locale getLang() {
        return Main.lang;
    }

    /**
     * Vérifie pour la présence de paramètres de langue, et ajuste la langue en fonction du résultat.
     *
     * @param _args
     *         Paramètres donnés par la méthode {@link Main#main(String[])}
     */
    private static void checkForLangArgs(List<String> _args) {
        int langInd = _args.indexOf("-lang");
        if (langInd != -1 && "en".equals(_args.get(langInd + 1))) {
            Main.lang = Locale.getDefault();
        }

        Main.lang = Locale.FRENCH;
    }

    /**
     * Méthode principale.
     * <p>
     * Peut lancer l'application textuelle ou changer la langue de l'interface selon les paramètres données.
     *
     * @param args
     *         Paramètres d'exécution.
     */
    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("text")) {
            Main_Terminal.main(args);
        } else {
            checkForLangArgs(arguments);
            GameApplication.launch(args);
        }
    }
}
