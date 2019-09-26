import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CaseTest {
    private Case aCase;

    private static int coorMax = Parametres.TAILLE - 1;

    @Test
    void toString_Test() {
        aCase = new Case( 0, 1, 32 );
        assertEquals( "Case(0,1,32)", "" + aCase );
    }

    private Grille furnishGrille_Core() {
        this.aCase = new Case( 2, 2, 4 );
        Grille grille = new Grille();
        this.aCase.setGrille( grille );

        grille.getGrille().add( this.aCase );
        return grille;
    }

    private void furnishGrilleWith( int _x, int _y ) {
        Grille grille = furnishGrille_Core();
        grille.getGrille().add( new Case( _x, _y, 4 ) );
    }

    private ArrayList<Case> furnishCases() {
        ArrayList<Case> list = new ArrayList<>();
        list.add( new Case( 2, 0, 4 ) );
        list.add( new Case( 2, CaseTest.coorMax, 4 ) );
        list.add( new Case( 0, 2, 4 ) );
        list.add( new Case( CaseTest.coorMax, 2, 4 ) );
        return list;
    }

    private void furnishGrilleMinus( int _x, int _y ) {
        Grille grille = furnishGrille_Core();

        ArrayList<Case> list = furnishCases();

        for ( Case c : list ) {
            c.setGrille( grille );
        }

        Case exclut = new Case( _x, _y, 4 );
        exclut.setGrille( grille );

        list.remove( exclut );
        assert ( list.size() == 3 );
        grille.getGrille().addAll( list );
    }

    @Test
    void getVoisinDirect_Haut_none() {
        this.furnishGrilleMinus( 2, 0 );

        assertNull( this.aCase.getVoisinDirect( Parametres.HAUT ), "Voisin Direct found while not supposed to." );
    }

    @Test
    void getVoisinDirect_Haut_one() {
        this.furnishGrilleWith( 2, 0 );

        assertNotNull( this.aCase.getVoisinDirect( Parametres.HAUT ), "Voisin Direct not found while supposed to." );
    }

    @Test
    void getVoisinDirect_Bas_none() {
        this.furnishGrilleMinus( 2, CaseTest.coorMax );

        assertNull( this.aCase.getVoisinDirect( Parametres.BAS ), "Voisin Direct found while not supposed to." );
    }

    @Test
    void getVoisinDirect_Bas_one() {
        this.furnishGrilleWith( 2, CaseTest.coorMax );

        assertNotNull( this.aCase.getVoisinDirect( Parametres.BAS ), "Voisin Direct not found while supposed to." );
    }

    @Test
    void getVoisinDirect_Gauche_none() {
        this.furnishGrilleMinus( 0, 2 );

        assertNull( this.aCase.getVoisinDirect( Parametres.GAUCHE ), "Voisin Direct found while not supposed to." );
    }

    @Test
    void getVoisinDirect_Gauche_one() {
        this.furnishGrilleWith( 0, 2 );

        assertNotNull( this.aCase.getVoisinDirect( Parametres.GAUCHE ), "Voisin Direct not found while supposed to." );
    }

    @Test
    void getVoisinDirect_Droite_none() {
        this.furnishGrilleMinus( CaseTest.coorMax, 2 );

        assertNull( this.aCase.getVoisinDirect( Parametres.DROITE ), "Voisin Direct found while not supposed to." );
    }

    @Test
    void getVoisinDirect_Droite_one() {
        this.furnishGrilleWith( CaseTest.coorMax, 2 );

        assertNotNull( this.aCase.getVoisinDirect( Parametres.DROITE ), "Voisin Direct not found while supposed to." );
    }
}