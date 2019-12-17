/*
 * Copyright (c) 05/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package css;

import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.Random;

/**
 * Classe utilitaire CSSGenerator.
 * <p>
 * Sert à générer des éléments de CSS dans des fichers avec des paramètres donnés :  
 *          - Début de la boucle de génération.  
 *          - Fin de la boucle de génération. 
 *          - Fichier vers lequel les styles devront être écrits.
 *          - {@link OpenOption Options de la gestion} du fichier.
 *          - Masque du bloc et de l'identifiant/la classe du style.
 * <p>
 * Est pour l'instant configurée pour la création de couleurs aléatoires pour les tuiles, mais peut être configurée
 * autrement.
 * <p>
 * Suppression des avertissements "FieldCanBeLocal" pour permettre de garder les paramètres d'exécution en tant
 * qu'attributs plutôt qu'en tant que variables de méthodes.
 */
@SuppressWarnings("FieldCanBeLocal")
class CSSGenerator {

    /** Début de la boucle de génération. */
    private static final int LOOP_BEGINNING = 32;
    /** Fin de la boucle de génération. */
    private static final int LOOP_END = 2048;

    /** Fichier vers lequel les styles devront être écrits. */
    private static final String FILE = "src/controller/menus/game/TileStyle.css";

    /**
     * {@link OpenOption Options de la gestion} du fichier.
     * <p>
     * Configué pour avoir un {@link StandardOpenOption#WRITE accès d'écriture} et
     * d'{@link StandardOpenOption#APPEND ajouter les éléments à la fin du fichier}.
     *
     * @see StandardOpenOption#CREATE_NEW s'il est question de créer un nouveau fichier.
     * @see StandardOpenOption#TRUNCATE_EXISTING s'il est question d'écrase du contenu existant.
     */
    private static final OpenOption[] OPTIONS = new OpenOption[] {
            StandardOpenOption.WRITE,
            StandardOpenOption.APPEND
    };

    /**
     * Masque du bloc et de l'identifiant/la classe du style.
     *
     * @param _number
     *         Numéro de la boucle ; utilisé pour les styles de tuiles afin d'obtenir une couleur différente pour chaque
     *         valeur jusqu'à {@link CSSGenerator#LOOP_END}.
     * @param _element
     *         Elément généré par la {@link CSSGenerator#generateNewElement() méthode de génération}.
     *
     * @return une {@link String chaîne de caractères} correspondant à l'élément dans le cadre du masque.
     */
    private static String MASK(int _number, String _element) {
        return ".tile" + _number + " {\n" +
               "    " + _element + "\n" +
               "}\n\n";
    }

    /**
     * Génére aléatoirement une nouvelle {@link Color couleur}.
     * <p>
     * Ne concerne qu'une configuration de couleur, et peut donc être supprimée si la configuration change.
     * <p>
     * Étant donné qu'il s'agisse d'une génération aléatoire, il est possible que deux couleurs soient très similaires ;
     * il est donc conseillé de vérifier les couleurs générées.
     *
     * @return la couleur générée.
     */
    private Color generateNewColor() {
        Random rand = new Random();

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        return new Color(r, g, b);
    }

    /**
     * Génére un nouvel élément pouvant consister en plusieurs lignes selon la configuration.
     * <p>
     * Dans la configuration actuelle, une couleur est {@link CSSGenerator#generateNewColor()} générée et
     * fournit le code RGB pour écrire une ligne CSS {@code -fx-background-color: rgb()}.
     *
     * @return l'élément créé.
     */
    private String generateNewElement() {
        Color randomColor = generateNewColor();

        int red = randomColor.getRed();
        int green = randomColor.getGreen();
        int blue = randomColor.getBlue();

        return "-fx-background-color: rgb(" + red + "," + green + "," + blue + ")";
    }

    /**
     * Génére une série d'élément dans une {@link String chaîne de caractères} dépendant des paramètres de la classe.
     * <p>
     * Commenence une boucle avec LOOP_BEGINNING et la finit LOOP_END, où des éléments sont
     * {@link CSSGenerator#generateNewElement() générés} et ajoutés au sein du bloque fournit par MASK.
     *
     * @return la {@link String chaîne de caractères} contenant les blocs générés.
     */
    private String generateNewElements() {
        StringBuilder res = new StringBuilder();

        for (int i = LOOP_BEGINNING; i <= LOOP_END; i *= 2) {
            res.append(MASK(i, this.generateNewElement()));
        }
        return res.toString();
    }

    /**
     * Ecrit le contenu passé en paramètre dans un fichier donné par la classe ({@link CSSGenerator#FILE}).
     *
     * @param _content
     *         Contenu à écrire dans le fichier.
     *
     * @throws IOException
     *         s'il arrive une erreur <i>Input/Output</i> lors de l'ouverture ou de l'écriture avec le fichier.
     */
    private void writeIntoFile(String _content) throws IOException {
        Path path = Paths.get(FILE);
        Files.write(path, _content.getBytes(), OPTIONS);
    }

    /**
     * Méthode principale.
     * <p>
     * Utilisée pour exécuter les méthodes de la classe et créer des styles CSS.
     *
     * @param args
     *         Paramètres d'exécution. [non-utilisés]
     */
    public static void main(String[] args) throws IOException {
        CSSGenerator generator = new CSSGenerator();
        generator.writeIntoFile(generator.generateNewElements());
    }
}
