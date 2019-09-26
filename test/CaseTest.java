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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe CaseTest.
 * <p>
 * Utilisée pour faire des tests unitaires sur la classe {@link Case} et ses fonctions.
 */
class CaseTest {
    /** Instance utilisée pour les tests. */
    private Case aCase;

    /**
     * Vérification de la fonctionalité de la méthode {@link Case#toString()}.
     */
    @Test
    void toString_Test() {
        aCase = new Case(0, 1, 32);
        assertEquals("Case(0,1,32)", "" + aCase);
    }
}