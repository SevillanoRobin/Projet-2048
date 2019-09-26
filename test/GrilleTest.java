import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrilleTest {
    private Grille grille;

    @BeforeEach
    void setUp() {
        this.grille = new Grille();
    }

    private void furnishValues() {
        this.grille.getGrille().add( new Case( 0, 0, 4 ) );
        this.grille.getGrille().add( new Case( 1, 2, 4 ) );
    }

    @Test
    void testToString() {
        if ( Parametres.TAILLE == 4 ) {
            this.furnishValues();
            String expected = "[4, 0, 0, 0]\n" +
                              "[0, 0, 0, 0]\n" +
                              "[0, 4, 0, 0]\n" +
                              "[0, 0, 0, 0]";

            assertEquals( expected, "" + this.grille, "The results are not corresponding." );
        }
    }
}