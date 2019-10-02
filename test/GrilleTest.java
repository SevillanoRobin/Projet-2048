/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe GrilleTest.
 * <p>
 * Utilisée pour faire des tests unitaires sur la classe {@link Grille} et ses fonctions.
 */
class GrilleTest {
    /** Instance utilisée pour les tests. */
    private Grille grille;

    /**
     * Méthode appelée avant chaque test.
     * <p>
     * Sert à initialiser l'attribut `grille` avant chaque test.
     */
    @BeforeEach
    void setUp() {
        this.grille = new Grille();
    }

    /**
     * Remplit la grille de quelques cellules afin de faire les tests sur toString().
     */
    private void furnishValues_toString() {
        Case[] _cases = {
                Case.verifyThenCreateCase(0, 0, 4),
                Case.verifyThenCreateCase(1, 2, 4)
        };
        for (Case _case : _cases) {
            _case.setGrille(this.grille);
        }
        this.grille.getGrille().addAll(Arrays.asList(_cases));
    }

    /**
     * Vérification de la fonctionalité de la méthode {@link Grille#toString()}.
     */
    @Test
    void testToString() {
        if (Parametres.TAILLE == 4) {
            this.furnishValues_toString();
            String expected = "[4, 0, 0, 0]\n" +
                              "[0, 0, 0, 0]\n" +
                              "[0, 4, 0, 0]\n" +
                              "[0, 0, 0, 0]";

            assertEquals(expected, "" + this.grille, "The results are not corresponding.");
        }
    }

    /**
     * Vérification de la fonctionalité de {@link Grille#nouvelleCase()}.
     */
    @Test
    void testNouvelleCase() {
        assertTrue(this.grille.nouvelleCase());
    }

    /**
     * Fournit les cases nécéssaires pour bloquer tout mouvement dans la grille.
     * <p>
     * Permet de provoquer un retour positif de {@link Grille#partieFinie()}.
     */
    private void furnishValues_finish() {
        for (int i = 0; i < Parametres.TAILLE; i++) {
            for (int j = 0; j < Parametres.TAILLE; j++) {
                int value;

                if (i == j) {
                    value = 4;
                } else if (i % 2 == 0) {
                    value = j % 2 == 0 ? 4 : 2;
                } else {
                    value = j % 2 == 0 ? 2 : 4;
                }

                Case _case = new Case(i, j, value);
                _case.setGrille(this.grille);

                this.grille.getGrille().add(_case);
            }
        }
    }

    /**
     * Teste la méthode {@link Grille#partieFinie()}.
     * <p>
     * Cas où la grille ne devrait permettre aucun mouvement.
     */
    @Test
    void partieFinie_finished() {
        this.furnishValues_finish();

        assertTrue(this.grille.partieFinie(),
                "The grid is anormally not considered as finished." + this.grille.toString());
    }

    /**
     * Teste la méthode {@link Grille#partieFinie()}.
     * <p>
     * Cas où la grille n'est pas remplie totalement.
     */
    @Test
    void partieFinie_notFinished_notFilled() {
        this.furnishValues_finish();
        Case _case = new Case(0, 0, 2);
        _case.setGrille(this.grille);
        this.grille.getGrille().remove(_case);

        assertFalse(this.grille.partieFinie(),
                "The grid is anormally considered as finished. \n" + this.grille.toString());
    }

    /**
     * Teste la méthode {@link Grille#partieFinie()}.
     * <p>
     * Cas où la grille est composée de voisins de même valeur.
     */
    @Test
    void partieFinie_notFinished_hasSameValue() {
        this.furnishValues_finish();

        Case _case = new Case(0, 0, 2);
        _case.setGrille(this.grille);
        this.grille.getGrille().remove(_case);
        this.grille.getGrille().add(_case);

        assertFalse(this.grille.partieFinie(),
                "The grid is anormally considered as finished.  \n" + this.grille.toString());
    }

    /**
     * Pseudo-copie de la méthode privée `fusion()` de Grille.
     * <p>
     * Multiplie la valeur de la case passée en paramètre par 2.
     *
     * @param _case Case fusionnée.
     */
    private void fusionStub(Case _case) {
        int newValue = _case.getValue() * 2;
        _case.setValue(newValue);
    }

    /**
     * Test de la méthode `fusion()` de Grille.
     * <p>
     * Utilise la pseudo-copie de cette classe.
     */
    @Test
    void fusion() {
        Case _case = Case.verifyThenCreateCase(0, 0, 2);

        this.fusionStub(_case);
        assertEquals(4, _case.getValue());

        this.fusionStub(_case);
        assertEquals(8, _case.getValue());
    }
}