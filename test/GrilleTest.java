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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}