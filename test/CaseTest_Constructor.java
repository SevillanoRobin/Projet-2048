/*
 * Copyright (c) 24/10/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe CaseTest_Constructor.
 * <p>
 * Utilisée pour faire des tests unitaires sur la classe {@link Case} et son constructeur.
 */
class CaseTest_Constructor {

    /**
     * Cas dans lequel le contructeur devrait exécuté correctement (sans exception).
     */
    @Test
    void constructor_properExecution() {
        assertFalse(doesConstructorReturnErrorsWithValue(2), "");
    }

    // ---- Valeur Anormale ---- //

    /*
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur la valeur.
     * <p>
     * Ici, avec un nombre pair plus petit que 2.
     */
    @Test
    void constructor_valueLessThan2_evenNumber() {
        assertTrue(doesConstructorReturnErrorsWithValue(0), "");
    }

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur la valeur.
     * <p>
     * Ici, avec un nombre impair plus petit que 2.
     */
    @Test
    void constructor_valueLessThan2_oddNumber() {
        assertTrue(doesConstructorReturnErrorsWithValue(1), "");
    }

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur la valeur.
     * <p>
     * Ici, avec un nombre négatif.
     */
    @Test
    void constructor_valueLessThan2_negativeNumber() {
        assertTrue(doesConstructorReturnErrorsWithValue(-1), "");
    }

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur la valeur.
     * <p>
     * Ici, avec un nombre pair plus grand que 4.
     */
    @Test
    void constructor_valueMoreThan4_evenNumber() {
        assertTrue(doesConstructorReturnErrorsWithValue(6), "");
    }

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur la valeur.
     * <p>
     * Ici, avec un nombre impair plus grand que 4.
     */
    @Test
    void constructor_valueMoreThan4_oddNumber() {
        assertTrue(doesConstructorReturnErrorsWithValue(5), "");
    }

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur la valeur.
     * <p>
     * Ici, avec un nombre égal à 3.
     */
    @Test
    void constructor_valueEqualTo3() {
        assertTrue(doesConstructorReturnErrorsWithValue(3), "");
    }

    /**
     * Vérifie que l'accesseur du constructeur retourne une exception pour une valeur donnée.
     *
     * @param _givenValue la valeur donnée censée provoquer une exception.
     *
     * @return {@code true} si le constructeur renvoit des exceptions.
     */
    private boolean doesConstructorReturnErrorsWithValue(int _givenValue) {
        boolean test = false;

        try {
            Case.verifyThenCreateCase(2, 2, _givenValue);
        } catch (IllegalArgumentException _e) {
            test = true;
        }

        return test;
    }


    // ---- Coordonnée Anormale ---- //

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur les coordonnées.
     * <p>
     * Ici, avec un nombre négatif.
     */
    @Test
    void constructor_coordLessThanPossible() {
        assertTrue(doesConstructorReturnErrorsWithCoord(-1), "");
    }

    /**
     * Cas dans lequel le contructeur devrait lancer une exception dûe aux contraites sur les coordonnées.
     * <p>
     * Ici, avec un nombre plus grand que le maximum.
     */
    @Test
    void constructor_coordMoreThanPossible() {
        assertTrue(doesConstructorReturnErrorsWithCoord(Parametres.TAILLE), "");
    }

    /**
     * Vérifie que l'accesseur du constructeur retourne une exception pour une coordonnée donnée.
     *
     * @param _givenCoord la coordonnée donnée censée provoquer une exception.
     *
     * @return {@code true} si le constructeur renvoit des exceptions.
     */
    boolean doesConstructorReturnErrorsWithCoord(int _givenCoord) {
        boolean test = false;

        try {
            Case.verifyThenCreateCase(_givenCoord, 2, 2);
        } catch (IllegalArgumentException _e1) {
            try {
                Case.verifyThenCreateCase(2, _givenCoord, 2);
                test = false;
            } catch (IllegalArgumentException _e2) {
                test = true;
            }
        }

        return test;
    }
}