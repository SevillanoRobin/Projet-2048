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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Classe principale.
 */
public class Main {

    /**
     * Vérifie pour la présence de paramètres de langue, et ajuste la langue en fonction du résultat.
     *
     * @param _args
     *         Paramètres donnés par la méthode {@link Main#main(String[])}
     */
    private static void checkForLangArgs(List<String> _args) {
        int langInd = _args.indexOf("-lang");
        if (langInd != -1 && "en".equals(_args.get(langInd + 1))) {
            GameApplication.setLANG(Locale.getDefault());
        }

        GameApplication.setLANG(Locale.FRENCH);
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
            JouerEnSolo.main(args);
        } else if (arguments.contains("IA")) {
            Main_IA.main(args);
        } else {
            checkForLangArgs(arguments);
            GameApplication.launchApplication(args);
        }
    }
}
