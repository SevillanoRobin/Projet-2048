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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Classe CaseTest_voisinDirect.
 * <p>
 * Utilisée pour faire des tests unitaires sur la classe {@link Case} et sa méthode {@link Case#getVoisinDirect(int)}.
 */
class CaseTest_voisinDirect {
    /** Instance utilisée pour les tests. */
    private Case aCase;

    /** Coordonnée maximale dans la grille. */
    private static int coorMax = Parametres.TAILLE - 1;

    /**
     * Coeur des méthodes `furnishGrille()`.
     * <p>
     * Crée une case, ainsi qu'un grille, et associe les deux instances.
     *
     * @return la grille créée.
     */
    private Grille furnishGrille_Core() {
        this.aCase = Case.verifyThenCreateCase(2, 2, 2);
        Grille grille = new Grille();
        this.aCase.setGrille(grille);

        grille.getGrille().add(this.aCase);
        return grille;
    }

    /**
     * Crée une grille avec une cellule aux coordonnées données.
     *
     * @param _x coordonnée en X pour la cellule.
     * @param _y coordonnée en Y pour la cellule.
     */
    private void furnishGrilleWith(int _x, int _y) {
        Grille grille = furnishGrille_Core();

        Case cell = Case.verifyThenCreateCase(_x, _y, 4);
        cell.setGrille(grille);

        grille.getGrille().add(cell);
    }

    /**
     * Fournit une liste de 4 cases situées aux extrêmités de la grille.
     * <p>
     * Utilisée pour faire des tests avec aucune cellule uniquement dans une direction donnée.
     *
     * @return la liste créée.
     */
    private ArrayList<Case> furnishCases() {
        ArrayList<Case> list = new ArrayList<>();
        list.add(Case.verifyThenCreateCase(2, 0, 4));
        list.add(Case.verifyThenCreateCase(2, CaseTest_voisinDirect.coorMax, 4));
        list.add(Case.verifyThenCreateCase(0, 2, 4));
        list.add(Case.verifyThenCreateCase(CaseTest_voisinDirect.coorMax, 2, 4));
        return list;
    }

    /**
     * Crée une grille remplie de 4 cellules aux extrémités, excepté pour une cellule aux coordonnées données.
     *
     * @param _x coordonnée en X pour la cellule.
     * @param _y coordonnée en Y pour la cellule.
     */
    private void furnishGrilleMinus(int _x, int _y) {
        Grille grille = furnishGrille_Core();

        ArrayList<Case> list = furnishCases();

        for (Case c : list) {
            c.setGrille(grille);
        }

        Case exclut = Case.verifyThenCreateCase(_x, _y, 4);
        exclut.setGrille(grille);

        list.remove(exclut);
        assert (list.size() == 3);
        grille.getGrille().addAll(list);
    }

    /**
     * Test de getVoisinDirect.
     * <p>
     * Orienté vers le haut, sans cellule dans cette direction.
     */
    @Test
    void getVoisinDirect_Haut_none() {
        this.furnishGrilleMinus(2, 0);

        assertNull(this.aCase.getVoisinDirect(Parametres.HAUT), "Voisin Direct found while not supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers le haut, avec une cellule uniquement dans cette direction.
     */
    @Test
    void getVoisinDirect_Haut_one() {
        this.furnishGrilleWith(2, 0);

        assertNotNull(this.aCase.getVoisinDirect(Parametres.HAUT), "Voisin Direct not found while supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers le bas, sans cellule dans cette direction.
     */
    @Test
    void getVoisinDirect_Bas_none() {
        this.furnishGrilleMinus(2, CaseTest_voisinDirect.coorMax);

        assertNull(this.aCase.getVoisinDirect(Parametres.BAS), "Voisin Direct found while not supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers le bas, avec une cellule uniquement dans cette direction.
     */
    @Test
    void getVoisinDirect_Bas_one() {
        this.furnishGrilleWith(2, CaseTest_voisinDirect.coorMax);

        assertNotNull(this.aCase.getVoisinDirect(Parametres.BAS), "Voisin Direct not found while supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers la gauche, sans cellule dans cette direction.
     */
    @Test
    void getVoisinDirect_Gauche_none() {
        this.furnishGrilleMinus(0, 2);

        assertNull(this.aCase.getVoisinDirect(Parametres.GAUCHE), "Voisin Direct found while not supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers la gauche, avec une cellule uniquement dans cette direction.
     */
    @Test
    void getVoisinDirect_Gauche_one() {
        this.furnishGrilleWith(0, 2);

        assertNotNull(this.aCase.getVoisinDirect(Parametres.GAUCHE), "Voisin Direct not found while supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers la droite, sans cellule dans cette direction.
     */
    @Test
    void getVoisinDirect_Droite_none() {
        this.furnishGrilleMinus(CaseTest_voisinDirect.coorMax, 2);

        assertNull(this.aCase.getVoisinDirect(Parametres.DROITE), "Voisin Direct found while not supposed to.");
    }

    /**
     * Test de getVoisinDirect.
     *
     * Orienté vers la droite, avec une cellule uniquement dans cette direction.
     */
    @Test
    void getVoisinDirect_Droite_one() {
        this.furnishGrilleWith(CaseTest_voisinDirect.coorMax, 2);

        assertNotNull(this.aCase.getVoisinDirect(Parametres.DROITE), "Voisin Direct not found while supposed to.");
    }
}
